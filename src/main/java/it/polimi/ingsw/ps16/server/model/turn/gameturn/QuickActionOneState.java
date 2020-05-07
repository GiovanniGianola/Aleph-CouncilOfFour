/*****************************************
 * 										 *
 * 		Council Of Four					 *
 * 										 *
 * 		Software Engineering Project	 *
 * 										 *
 * 		Politecnico di Milano 			 *
 * 										 *
 * 		Academic Year: 2015 - 2016		 *
 * 										 *
 * 		Authors: Gianola Giovanni		 *
 * 				 Leveni Filippo			 *
 * 				 Ionata Valentina		 *
 * 										 *
 ****************************************/
package it.polimi.ingsw.ps16.server.model.turn.gameturn;

import java.util.concurrent.locks.LockSupport;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.ActionType;
import it.polimi.ingsw.ps16.server.model.actions.quick.AdditionalMainAction;
import it.polimi.ingsw.ps16.server.model.actions.quick.ChangeBusinessPermitCardAction;
import it.polimi.ingsw.ps16.server.model.actions.quick.ElectCouncillorUsingAssistantAction;
import it.polimi.ingsw.ps16.server.model.actions.quick.EngageAssistantAction;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateBalcony;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdatePlayerMessage;
import it.polimi.ingsw.ps16.server.model.turn.TurnState;
import it.polimi.ingsw.ps16.server.view.ConnectionView;

/**
 * The Class QuickActionOneState.
 */
public class QuickActionOneState extends TurnState
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1366093243242695542L;
	
	/**
	 * Instantiates a new quick action one state.
	 *
	 * @param game
	 *            the game
	 * @param view
	 *            the view
	 */
	public QuickActionOneState(Game game, ConnectionView view)
	{
		super(game, view);
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.turn.TurnState#executeGameState()
	 */
	@Override
	public TurnState executeGameState() throws SuspendPlayerException
	{
		this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_STATE, this.getGame()));
		this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.QUICK_ACTIONS, this.getGame().getCurrentState()));
		
		this.notifyObservers(new UpdatePlayerMessage(this.getGame()));
		this.getGame().notifyObservers(new UpdateBalcony(this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_STATE, this.getGame()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		boolean isValidAction;
		if(ActionType.ADDMAIN.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			AdditionalMainAction actionRequested = new AdditionalMainAction(this.getGame());
			isValidAction = actionRequested.additionalMainAction();
		}
		else if(ActionType.CHANGE.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			ChangeBusinessPermitCardAction actionRequested = new ChangeBusinessPermitCardAction(this.getGame());
			isValidAction = actionRequested.changeBusinessPermitCard();
		}
		else if(ActionType.ELECTASSISTANT.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			
			ElectCouncillorUsingAssistantAction actionRequested = new ElectCouncillorUsingAssistantAction(this.getGame());
			isValidAction = actionRequested.electCouncillorUsingAssistant();
		}
		else if(ActionType.ENGAGEASSISTANT.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			
			EngageAssistantAction actionRequested = new EngageAssistantAction(this.getGame());
			isValidAction = actionRequested.engageAssitant();
		}
		else if(ActionType.BACK.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			return new ChooseOneState(this.getGame(), this.getView());
		}
		else
		{
			this.notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			return new QuickActionOneState(this.getGame(), this.getView());
		}
		if( !isValidAction )
		{
			return new QuickActionOneState(this.getGame(), this.getView());
		}
		return new MainActionTwoState(this.getGame(), this.getView());
	}
}
