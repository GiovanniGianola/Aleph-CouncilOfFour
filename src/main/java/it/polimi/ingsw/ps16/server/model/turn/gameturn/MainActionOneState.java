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
import it.polimi.ingsw.ps16.server.model.actions.main.AcquireBusinessPermitCardAction;
import it.polimi.ingsw.ps16.server.model.actions.main.BuildEmporiumUsingKingAction;
import it.polimi.ingsw.ps16.server.model.actions.main.BuildEmporiumUsingPermitCardAction;
import it.polimi.ingsw.ps16.server.model.actions.main.ElectCouncillorAction;
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
 * The Class MainActionOneState.
 */
public class MainActionOneState extends TurnState
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8484914057057279969L;
	
	/**
	 * Instantiates a new main action one state.
	 *
	 * @param game
	 *            the game
	 * @param view
	 *            the view
	 */
	public MainActionOneState(Game game, ConnectionView view)
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
		this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.MAIN_ACTIONS_ONE, this.getGame().getCurrentState()));
		
		this.notifyObservers(new UpdatePlayerMessage(this.getGame()));
		this.getGame().notifyObservers(new UpdateBalcony(this.getGame()));
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_STATE, this.getGame()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		boolean isValidAction;
		if(ActionType.ACQUIRE.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			AcquireBusinessPermitCardAction actionRequested = new AcquireBusinessPermitCardAction(this.getGame());
			isValidAction = actionRequested.acquireBusinessPermitCard();
		}
		else if(ActionType.BUILDKING.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			BuildEmporiumUsingKingAction actionRequested = new BuildEmporiumUsingKingAction(this.getGame());
			isValidAction = actionRequested.buildEmpuriumUsingKing();
		}
		else if(ActionType.BUILDPERMIT.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			BuildEmporiumUsingPermitCardAction actionRequested = new BuildEmporiumUsingPermitCardAction(this.getGame());
			isValidAction = actionRequested.buildEmporiumUsingPermitCard();
		}
		else if(ActionType.ELECT.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			ElectCouncillorAction actionRequested = new ElectCouncillorAction(this.getGame());
			isValidAction = actionRequested.electCouncillor();
		}
		else if(ActionType.BACK.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
		{
			return new ChooseOneState(this.getGame(), this.getView());
		}
		else
		{
			this.notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			return new MainActionOneState(this.getGame(), this.getView());
		}
		if( !isValidAction )
		{
			return new MainActionOneState(this.getGame(), this.getView());
		}
		return new ChooseTwoState(this.getGame(), this.getView());
	}
}
