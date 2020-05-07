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
package it.polimi.ingsw.ps16.server.model.actions.bonus;

import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;

/**
 * The Class ChooseARewardInAllBusinessPermitCardAction.
 */
public class ChooseARewardInAllBusinessPermitCardAction extends BonusAction
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ChooseARewardInAllBusinessPermitCardAction.class.getName() );
	
	/** The business permit card. */
	private BusinessPermitCard businessPermitCard;
	
	/**
	 * Instantiates a new choose a reward in all business permit card action.
	 *
	 * @param game
	 *            the game
	 */
	public ChooseARewardInAllBusinessPermitCardAction(Game game) {
		super(game);
	}

	/**
	 * Gets the business permit card of which take bonus.
	 *
	 * @return the business permit card
	 */
	public BusinessPermitCard getBusinessPermitCard() {
		return businessPermitCard;
	}

	/**
	 * Sets the business permit card of which take bonus.
	 *
	 * @param businessPermitCard
	 *            the new business permit card
	 */
	public void setBusinessPermitCard(BusinessPermitCard businessPermitCard) {
		this.businessPermitCard = businessPermitCard;
	}
	
	/**
	 * Check that player has a business permit cards.
	 *
	 * @return true, if successful
	 */
	//controlla che il giocatore abbia almeno un BusinessPermitCard
	public boolean checkBusinessPermitCards()
	{
		if( this.getGame().getCurrentPlayer().getBusinessPermitCards().isEmpty() && this.getGame().getCurrentPlayer().getBusinessPermitCardsFaceDown().isEmpty() )
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Choose a reward in all business permit card and got it.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean chooseARewardInAllBusinessPermitCardAction() throws SuspendPlayerException
	{
		if( !this.checkBusinessPermitCards() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("You don't have any Business Permit Card.", this.getGame().getCurrentState()));
			return false;
		}
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_PLAYER_BUSINESSPERMITCARDS, this.getGame()));

		int choice = 0;
		while(choice <= 0 || choice > ( this.getGame().getCurrentPlayer().getBusinessPermitCards().size() + this.getGame().getCurrentPlayer().getBusinessPermitCardsFaceDown().size() ))
		{
			this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_BUSINESSPERMITCARD, this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
			
			try		
			{
				choice = Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice());
				if( choice <= 0 || choice > ( this.getGame().getCurrentPlayer().getBusinessPermitCards().size() + this.getGame().getCurrentPlayer().getBusinessPermitCardsFaceDown().size() ) )
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input", this.getGame().getCurrentState()));
				}
			}
			catch(NumberFormatException e)
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input", this.getGame().getCurrentState()));
			}
		}
		
		BusinessPermitCard checkBusinessPermitCard;
		if( (Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice()) - 1) < this.getGame().getCurrentPlayer().getBusinessPermitCards().size() )
		{
			checkBusinessPermitCard = this.getGame().getCurrentPlayer().getBusinessPermitCards().get(Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice()) - 1);
		}
		else
		{
			checkBusinessPermitCard = this.getGame().getCurrentPlayer().getBusinessPermitCardsFaceDown().get((Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice()) - 1) - this.getGame().getCurrentPlayer().getBusinessPermitCards().size());
		}
		this.setBusinessPermitCard(checkBusinessPermitCard);
		this.execute();
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_MODIFIES_STRING, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_CURRENT_PLAYER, this.getGame()));
		return false;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() {
		
		for(int i=0; i < businessPermitCard.getBonus().size();i++)
		{
			try {
				
				businessPermitCard.getBonus().get(i).executeBonus(this.getGame());
			} 
			catch (Exception e) 
			{				
				log.log( Level.SEVERE, e.toString(), e);
			}
		}
	}
	
}
