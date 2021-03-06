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
 * The Class ChooseTwoState.
 */
public class ChooseTwoState extends TurnState
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 750493158574668596L;

	/**
	 * Instantiates a new choose two state.
	 *
	 * @param game
	 *            the game
	 * @param view
	 *            the view
	 */
	public ChooseTwoState(Game game, ConnectionView view)
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
		this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.CHOOSE_TWO_STATE, this.getGame().getCurrentState()));
		
		this.notifyObservers(new UpdatePlayerMessage(this.getGame()));
		this.getGame().notifyObservers(new UpdateBalcony(this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_STATE, this.getGame()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
				
		if( ActionType.QUICK.getActionType().equalsIgnoreCase(this.getMsg().getChoice()) )
			return new QuickActionTwoState(this.getGame(), this.getView());
		else if( ActionType.END.getActionType().equalsIgnoreCase(this.getMsg().getChoice()) )
			return new FinishState(this.getGame(), this.getView());
		else
		{
			this.notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			return new ChooseTwoState(this.getGame(), this.getView());
		}
	}
}
