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
package it.polimi.ingsw.ps16.server.model.actions.gamelogic;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;


/**
 * The Class DrawPoliticCardAction provide a new card from<br>
 *  the deck of politic card to a player at the beginning of the turn.
 */
public class DrawPoliticCardAction extends GameLogicAction 
{
	
	/** The card drawed. */
	private PoliticCard cardDrawed;
	
	/**
	 * Instantiates a new draw politic card action.
	 *
	 * @param game
	 *            the game
	 */
	public DrawPoliticCardAction(Game game) 
	{
		super(game);
	}
	
	
	/**
	 * Checks if the deck of politic cards is empty.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid() 
	{
		return !(this.getGame().getGameBoard().getPoliticCardsDeck().isEmpty());
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() 
	{
		if( isValid() )
		{
			cardDrawed = this.getGame().getGameBoard().drawPoliticCard();
			this.getGame().getCurrentPlayer().addPoliticCard(cardDrawed);
		}
		else
		{
			this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_FINISH_CARDS, this.getGame()));
		}
	}
}
