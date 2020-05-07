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
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.turn.TurnState;
import it.polimi.ingsw.ps16.server.view.ConnectionView;

/**
 * The Class DrawPoliticCardState.
 */
public class DrawPoliticCardState extends TurnState 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -429759160898097374L;

	/**
	 * Instantiates a new draw politic card state.
	 *
	 * @param game
	 *            the game
	 * @param view
	 *            the view
	 */
	public DrawPoliticCardState(Game game, ConnectionView view)
	{
		super(game, view);
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.turn.TurnState#executeGameState()
	 */
	@Override
	public TurnState executeGameState() throws SuspendPlayerException
	{			
		this.getGame().launchTimer(this.getView());
		
		PoliticCard cardDrawed;
		
		this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_STATE, this.getGame()));

		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_STATE, this.getGame()));
		
		if( this.getGame().getGameBoard().getPoliticCardsDeck().isEmpty() )
		{
			this.notifyObservers(new ErrorMessage("The deck had no cards", this.getGame().getCurrentState()));
		}
		else
		{
			cardDrawed = this.getGame().getGameBoard().drawPoliticCard();
			this.getGame().getCurrentPlayer().addPoliticCard(cardDrawed);
		}
		
		this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_CURRENT_PLAYER, this.getGame()));
		
		return new ChooseOneState(this.getGame(), this.getView());
	}
}
