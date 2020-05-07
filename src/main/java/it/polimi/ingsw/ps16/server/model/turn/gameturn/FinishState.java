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

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateBalcony;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdatePlayerMessage;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateTimer;
import it.polimi.ingsw.ps16.server.model.turn.TurnState;
import it.polimi.ingsw.ps16.server.view.ConnectionView;

/**
 * The Class FinishState.
 */
public class FinishState extends TurnState
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5165417016547295151L;

	/**
	 * Instantiates a new finish state.
	 *
	 * @param game
	 *            the game
	 * @param view
	 *            the view
	 */
	public FinishState(Game game, ConnectionView view)
	{
		super(game, view);
	}
	
	
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.turn.TurnState#executeGameState()
	 */
	@Override
	public TurnState executeGameState()
	{
		this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_STATE, this.getGame()));
		this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_EMPORIUMS, this.getGame()));
		
		this.notifyObservers(new UpdatePlayerMessage(this.getGame()));
		this.getGame().notifyObservers(new UpdateBalcony(this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_STATE, this.getGame()));
		
		this.getGame().getCurrentState().notifyObservers(new UpdateTimer(this.getGame(), Game.getTimer(), "stop"));
		
		if( this.getGame().getCurrentPlayer().getEmporiumStack().isEmpty() )
		{
			this.getGame().setFinishGame(true);
		}
		
		return null;
	}
}
