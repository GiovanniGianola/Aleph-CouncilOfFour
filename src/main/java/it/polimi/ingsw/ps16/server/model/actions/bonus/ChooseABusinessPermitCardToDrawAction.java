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

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;


/**
 * The Class ChooseABusinessPermitCardToDrawAction (take a business permit card without paying it).
 */
public class ChooseABusinessPermitCardToDrawAction extends BonusAction
{
	
	/** The business permit card. */
	private BusinessPermitCard businessPermitCard;
	
	
	
	/**
	 * Instantiates a new choose a business permit card to draw action.
	 *
	 * @param game
	 *            the game
	 */
	public ChooseABusinessPermitCardToDrawAction(Game game) {
		
		super(game);
	}
	
	
	
	/**
	 * Gets the business permit card to draw.
	 *
	 * @return the business permit card
	 */
	public BusinessPermitCard getBusinessPermitCard() {
		return businessPermitCard;
	}


	/**
	 * Sets the business permit card to draw.
	 *
	 * @param businessPermitCard
	 *            the new business permit card
	 */
	public void setBusinessPermitCard(BusinessPermitCard businessPermitCard) {
		this.businessPermitCard = businessPermitCard;
	}

	/**
	 * Check at least a region has a business permit card to draw .
	 *
	 * @return true, if successful
	 */
	//controlla che almeno una regione abbia un BusinessPermitCard
	public boolean checkBusinessPermitCardsDeckAll()
	{
		for( RegionBoard region : this.getGame().getGameBoard().getRegionBoard() )
		{
			if( region.getBusinessPermitTileOne() != null )
			{
				return true;
			}
			if( region.getBusinessPermitTileTwo() != null )
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Choose a business permit card to draw.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean chooseABusinessPermitCardToDraw() throws SuspendPlayerException
	{
		if( !this.checkBusinessPermitCardsDeckAll() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No more Business Permit Cards.", this.getGame().getCurrentState()));
			return false;
		}
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_REGIONS_BUSINESSPERMITCARDS, this.getGame()));
		
		
		int choice = 0;
		while(choice <= 0 || choice > this.getGame().getGameBoard().getRegionBoard().size()*2)
		{
			this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_BUSINESSPERMITCARD, this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
			
			try		
			{
				choice = Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice());
				if( choice <= 0 || choice > this.getGame().getGameBoard().getRegionBoard().size()*2 )
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input", this.getGame().getCurrentState()));
				}
				else
				{
					BusinessPermitCard businessPermitCard;
					for( int i = 0; i < this.getGame().getGameBoard().getRegionBoard().size() && choice!=0; i++ )
					{
						if( ((i*2)+1) == Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice()) )
						{	
							if(this.getGame().getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne() == null)
							{
								this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Choice", this.getGame().getCurrentState()));
								choice = 0;
							}
							else
							{
								businessPermitCard = this.getGame().getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne();
								this.setBusinessPermitCard(businessPermitCard);
							}
						}
						if( ((i*2)+2) == Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice()) )
						{
							if(this.getGame().getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo() == null)
							{
								this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Choice", this.getGame().getCurrentState()));
								choice = 0;
							}
							else
							{
								businessPermitCard = this.getGame().getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo();
								this.setBusinessPermitCard(businessPermitCard);
							}
						}
					}
				}
			}
			catch(NumberFormatException e)
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input", this.getGame().getCurrentState()));
			}
		}
		
		this.execute();
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_MODIFIES_STRING, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_REGIONS_BUSINESSPERMITCARDS, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_CURRENT_PLAYER, this.getGame()));
		return true;
	}


	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() {
		
		if(businessPermitCard.getCities().get(0).getRegionBoard().getBusinessPermitTileOne().equals(businessPermitCard))
			this.getGame().getCurrentPlayer().addBusinessPermitCard(businessPermitCard.getCities().get(0).getRegionBoard().drawBusinessPermitTileOne());
	
		if(businessPermitCard.getCities().get(0).getRegionBoard().getBusinessPermitTileTwo().equals(businessPermitCard))
			this.getGame().getCurrentPlayer().addBusinessPermitCard(businessPermitCard.getCities().get(0).getRegionBoard().drawBusinessPermitTileTwo());
	
	}

}
