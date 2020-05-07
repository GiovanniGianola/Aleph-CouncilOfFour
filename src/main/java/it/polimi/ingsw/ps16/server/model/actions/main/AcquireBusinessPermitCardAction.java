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
package it.polimi.ingsw.ps16.server.model.actions.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.ActionType;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.gameboard.Councillor;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;


/**
 * Player choose to acquire a business permit card using his politic cards
 */
public class AcquireBusinessPermitCardAction extends MainAction 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( AcquireBusinessPermitCardAction.class.getName() );
	
	/** The politic cards list. */
	private List<PoliticCard> politicCardsList;
	
	/** The business permit card. */
	private BusinessPermitCard businessPermitCard;
	
	
	
	/**
	 * Instantiates a new acquire business permit card action.
	 *
	 * @param game
	 *            the game
	 */
	public AcquireBusinessPermitCardAction(Game game) 
	{
		super(game);
	}
	
	
	
	/**
	 * Sets the politic cards list.
	 *
	 * @param politicCardsList
	 *            the new politic cards list
	 */
	public void setPoliticCardsList(List<PoliticCard> politicCardsList) 
	{
		this.politicCardsList = politicCardsList;
	}

	/**
	 * Sets the business permit card.
	 *
	 * @param businessPermitCard
	 *            the new business permit card
	 */
	public void setBusinessPermitCard(BusinessPermitCard businessPermitCard) 
	{
		this.businessPermitCard = businessPermitCard;
	}
	
	/**
	 * Check that player has at least a politic Card.
	 *
	 * @return true, if player has one or more cards
	 */
	//controlla che l'utente abbia almeno una carta politica
	public boolean checkPlayerCards()
	{
		if( !this.getGame().getCurrentPlayer().getPoliticCardsList().isEmpty() )
			return true;
		return false;
	}
	
	/**
	 * Check that one of all regions have at least a business permit card.
	 *
	 * @return true, if one of the regions has more than 0 politic cards.
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
	 * Check that user's cards can match with at least a balcony of a region.
	 *
	 * @return true, if a card match with a councillor.
	 */
	//controlla che l'utente possa soddisfare almeno un balcone di una regione
	public boolean checkPlayerCardsAndRegionsBalcony()
	{
		if( this.getGame().getCurrentPlayer().searchCardsExistence(Colour.JOLLY, 1))
		{
			return true;
		}
		for( int i = 0; i < this.getGame().getGameBoard().getRegionBoard().size(); i++ )
		{
				for( Councillor councillor : this.getGame().getGameBoard().getRegionBoard().get(i).getBalcony().getBalcony() )
				{
					for( int k = 0; k < this.getGame().getCurrentPlayer().getPoliticCardsList().size(); k++ ) 
					{
						if( this.getGame().getCurrentPlayer().getPoliticCardsList().get(k).getColor() == councillor.getColor() )
							return true;
					}
				}
		}
		return false;
	}
	
	/**
	 * Check that player can accommodate the balcony of the region chosen
	 *
	 * @return true, if a card match with a councillor
	 */
	//controlla che l'utente possa soddisfare il balcone della regione scelta
	public boolean checkPlayerCardsAndRegionChooseBalcony()
	{
		if( this.getGame().getCurrentPlayer().searchCardsExistence(Colour.JOLLY, 1))
		{
			return true;
		}
		for( Councillor councillor : this.businessPermitCard.getCities().get(0).getRegionBoard().getBalcony().getBalcony() )
		{
			for( int k = 0; k < this.getGame().getCurrentPlayer().getPoliticCardsList().size(); k++ ) 
			{
				if( this.getGame().getCurrentPlayer().getPoliticCardsList().get(k).getColor() == councillor.getColor() )
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Check that player has the politic cards he chosen
	 *
	 * @param politicCardsList
	 *            the politic cards list that player choose
	 * @return true, if he has the cards
	 */
	//controlla che l'utente abbia effettivamente le carte che sceglie
	public boolean checkCardOwnership(List<PoliticCard> politicCardsList)
	{
		List<PoliticCard> copyList = new ArrayList<>();
		
		for(int i=0; i < politicCardsList.size(); i++)
			copyList.add(new PoliticCard(politicCardsList.get(i).getColor()));
		boolean res1 = true;
		while(!copyList.isEmpty())
		{
			int countColor=0;
			for(int j=0;j<copyList.size();j++)
				if(copyList.get(j).getColor()==copyList.get(0).getColor())
					countColor ++;
			
			res1=this.getGame().getCurrentPlayer().searchCardsExistence(copyList.get(0).getColor(), countColor);
			if(!res1)
				return false;
			
			Colour removeColor = copyList.get(0).getColor();
			for (int k=0; k<copyList.size();k++)
			{
				if(copyList.get(k).getColor()== removeColor)
					copyList.remove(k);
			}
		}
		return res1;
	}
	
	/**
	 * Check that politic cards chosen match with all balcony's councillors.
	 *
	 * @param politicCardsList
	 *            the politic cards list that player choose
	 * @return true, if matches
	 */
	//controlla che le carte che sceglie corrispondono ai consiglieri del balcone
	public boolean checkBalconyColors(List<PoliticCard> politicCardsList)
	{
		List<PoliticCard> copyCardsList = new ArrayList<>();
		for(int i=0; i < politicCardsList.size(); i++)
			copyCardsList.add(new PoliticCard(politicCardsList.get(i).getColor()));
		
		boolean result= true;
		while(!copyCardsList.isEmpty())
		{
			int countNumOfCards=0;
			for(int j=0;j<copyCardsList.size();j++)
			{
				if(copyCardsList.get(j).getColor()==copyCardsList.get(0).getColor())
					countNumOfCards ++;
			}
			if(copyCardsList.get(0).getColor()!= Colour.JOLLY)
				result= businessPermitCard.getCities().get(0).getRegionBoard().getBalcony().searchCouncillorPresence(copyCardsList.get(0).getColor(), countNumOfCards );
			
			if(!result)
				return false;
			
			Colour removeColor = copyCardsList.get(0).getColor();
			for (int k=0; k<copyCardsList.size();k++)
			{
				if(copyCardsList.get(k).getColor()== removeColor)
					copyCardsList.remove(k);
			}
		}
		return result;
	}
	
	/**
	 * Check that player has enough money to pay missing cards
	 *
	 * @param politicCardsList
	 *            the politic cards list that he chose
	 * @return true, if have enough money
	 */
	//controlla che abbia abbastanza monete per pagare le carte mancanti
	public boolean checkMoney(List<PoliticCard> politicCardsList)
	{
		int money = 0;
		if(politicCardsList.size()==1)
			money = money + 10;
		else if(politicCardsList.size()==2)
			money = money + 7;
		else if(politicCardsList.size()==3)
			money = money + 4;
		
		int jollyMoney= 0;
		for(int i=0; i<politicCardsList.size(); i++)
		{
			if(politicCardsList.get(i).getColor().equals(Colour.JOLLY))
				jollyMoney++;
		}
		money= money + jollyMoney;
		
		if(this.getGame().getCurrentPlayer().getRichnessMarkerDisc().getPosition() >= money)
			return true;
		
		return false;
	}
	
	/**
	 *execute the acquire business permit card action
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean acquireBusinessPermitCard() throws SuspendPlayerException
	{
		if( !this.checkPlayerCards() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Action: no politic cards", this.getGame().getCurrentState()));
			return false;
		}
		if( !this.checkBusinessPermitCardsDeckAll() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No more Business Permit Cards.", this.getGame().getCurrentState()));
			return false;
		}
		if( !this.checkPlayerCardsAndRegionsBalcony() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Action: no matches", this.getGame().getCurrentState()));
			return false;
		}
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_POLITICCARDS, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_REGION_BALCONIES, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_REGIONS_BUSINESSPERMITCARDS, this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_ACTION, this.getGame()));
		
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
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
				}
				else
				{
					BusinessPermitCard checkBusinessPermitCard;
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
								checkBusinessPermitCard = this.getGame().getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne();
								this.setBusinessPermitCard(checkBusinessPermitCard);
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
								checkBusinessPermitCard = this.getGame().getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo();
								this.setBusinessPermitCard(checkBusinessPermitCard);
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
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));		
		
		if( !this.checkPlayerCardsAndRegionChooseBalcony() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Action: no matches in region: [" + businessPermitCard.getCities().get(0).getRegionBoard().getName() + "]", this.getGame().getCurrentState()));
			return false;
		}
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_REQUEST_SATISFY_COUNCIL_FOR_BUSINESS, this.getGame()));
		
		List<PoliticCard> checkPoliticCardsList = new ArrayList<>();
		do
		{
			this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_POLITICCARDS, this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
			
			while(Colour.searchColorByName(this.getGame().getCurrentState().getMsg().getChoice()) == null && !ActionType.STOP.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()))
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Color", this.getGame().getCurrentState()));
				this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_POLITICCARDS, this.getGame().getCurrentState()));
				
				LockSupport.park();
				this.checkPlayerToSuspend();
			}
			
			if( ActionType.STOP.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()))
			{
				if(  checkPoliticCardsList.isEmpty()  )
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("You must choose at least one card", this.getGame().getCurrentState()));
			}
			else
			{
				checkPoliticCardsList.add(new PoliticCard(Colour.searchColorByName(this.getGame().getCurrentState().getMsg().getChoice())));
				
				/* controlla che l'utente abbia effettivamente le carte che sceglie */
				if( !this.checkCardOwnership( checkPoliticCardsList ) )
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("You don't have the card that you chose: [" + checkPoliticCardsList.remove(checkPoliticCardsList.size() - 1).toString() + "]", this.getGame().getCurrentState()));
				}
				/* controlla che le carte che sceglie corrispondono ai consiglieri del balcone */
				if( !this.checkBalconyColors( checkPoliticCardsList ) )
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("There is no [" + checkPoliticCardsList.remove(checkPoliticCardsList.size() - 1).toString() + "] card in this balcony", this.getGame().getCurrentState()));
				}
			}
			this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		}
		while(( !ActionType.STOP.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()) || checkPoliticCardsList.isEmpty() ) && checkPoliticCardsList.size() < 4);
		
		//controlla che abbia abbastanza monete per pagare le carte mancanti
		if( !this.checkMoney(checkPoliticCardsList) )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No enough Coins", this.getGame().getCurrentState()));
			return false;
		}
		this.setPoliticCardsList(checkPoliticCardsList);
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
	public void execute() 
	{
		//scarto le carte dal mazzo del player
		List<Colour> colorArray = new ArrayList<>();
		for(int i=0; i< politicCardsList.size(); i++)
		{
			colorArray.add(politicCardsList.get(i).getColor());
		}
		this.getGame().getCurrentPlayer().discardCards(colorArray);
		
		//aggiungo le carte al mazzo di scarti
		for(int i = 0; i < politicCardsList.size(); i++)
			this.getGame().getGameBoard().getDiscardedPoliticCardsDeck().addCard(politicCardsList.get(i));
		
		//il player paga le dovute monete
		int money = 0;
		if(politicCardsList.size() == 1)
			money = money + 10;
		else if(politicCardsList.size() == 2)
			money = money + 7;
		else if(politicCardsList.size() == 3)
			money = money + 4;
		int jollyMoney= 0;
		
		for(int i=0; i<politicCardsList.size(); i++)
		{
			if(politicCardsList.get(i).getColor().equals(Colour.JOLLY))
				jollyMoney++;
		}
		
		money += jollyMoney;
		this.getGame().getCurrentPlayer().getRichnessMarkerDisc().moveBack(money);
		
		//pesca carta dal posto , aggiorna la carta nel posto e poi ritorna quella 
		// che c'era prima al player
		
		if(businessPermitCard.getCities().get(0).getRegionBoard().getBusinessPermitTileOne().equals(businessPermitCard))
			this.getGame().getCurrentPlayer().addBusinessPermitCard(businessPermitCard.getCities().get(0).getRegionBoard().drawBusinessPermitTileOne());
		if(businessPermitCard.getCities().get(0).getRegionBoard().getBusinessPermitTileTwo().equals(businessPermitCard))
			this.getGame().getCurrentPlayer().addBusinessPermitCard(businessPermitCard.getCities().get(0).getRegionBoard().drawBusinessPermitTileTwo());
		
		for(int i=0;i<businessPermitCard.getBonus().size();i++)
		{
			if( this.getGame().getCurrentState() != null && this.getGame().getCurrentState().countObservers() != 0)
				this.getGame().getCurrentState().notifyObservers(new InfoMessageType("You are earning: " + businessPermitCard.getBonus().get(i).toString(), this.getGame()));
			try 
			{
				businessPermitCard.getBonus().get(i).executeBonus(this.getGame());
			}
			catch (Exception e) 
			{
				log.log( Level.SEVERE, e.toString(), e);
			}
		}
	}
}
