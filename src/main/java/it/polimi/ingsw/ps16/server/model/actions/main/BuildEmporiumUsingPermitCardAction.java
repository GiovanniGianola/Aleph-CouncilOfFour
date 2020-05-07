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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.bonus.Bonus;
import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.gameboard.Node;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;

/**
 * The Player choose a city where build using a permit card
 */
public class BuildEmporiumUsingPermitCardAction extends MainAction 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( BuildEmporiumUsingPermitCardAction.class.getName() );
	
	/** The business permit card. */
	private BusinessPermitCard businessPermitCard;
	
	/** The city. */
	private City city;
	
	/**
	 * Instantiates a new builds the emporium using permit card action.
	 *
	 * @param game
	 *            the game
	 */
	public BuildEmporiumUsingPermitCardAction(Game game) 
	{
		super(game);
	}
	
	/**
	 * Gets the business permit card.
	 *
	 * @return the business permit card
	 */
	public BusinessPermitCard getBusinessPermitCard() {
		return businessPermitCard;
	}
	
	/**
	 * Sets the business permit card.
	 *
	 * @param businessPermitCard
	 *            the new business permit card
	 */
	public void setBusinessPermitCard(BusinessPermitCard businessPermitCard) {
		this.businessPermitCard = businessPermitCard;
	}

	/**
	 * Gets the city where build.
	 *
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Sets the city where build.
	 *
	 * @param city
	 *            the new city
	 */
	public void setCity(City city) {
		this.city = city;
	}

	//Fa la visita in ampiezza ritornando la lista di bonus accumulati da tutte le città, in cui il giocatore ha
	//costruito, adiacenti a quella in cui ha appena costruito e così via
	/**
	 * do breadth first search of city near the city where build
	 *
	 * @param cityWhereHadBuilt
	 *            the city where had built
	 * @return the list of bonus of the city and all the city near it. 
	 */
	//Passi come parametri la città in cui si vuole costruire
	private List<Bonus> breadthFirstSearch(City cityWhereHadBuilt)
	{
		List<Bonus> bonusList = new ArrayList<>();
		bonusList.addAll(cityWhereHadBuilt.getBonus());
		
		cityWhereHadBuilt.setColor(Node.NodeColor.GREY.getColor());
		
		City actualCity; //Città attuale di cui sto controllando le città vicine
		Queue<City> queue = new LinkedList<>();
		queue.add(cityWhereHadBuilt);
		
		while( !queue.isEmpty() )
		{
			actualCity = queue.remove();
			
			for( City nearCity /*Città vicina all'actualCity*/ : actualCity.getNearCities() )
			{
				if( (Node.NodeColor.WHITE).equals(nearCity.getColor()) )
				{
					nearCity.setColor(Node.NodeColor.GREY.getColor());
					
					if( nearCity.getEmporiums()[this.getGame().getCurrentPlayer().getPlayerID()] != null )
					{
						bonusList.addAll(nearCity.getBonus());
						queue.add(nearCity);
					}
					else
					{
						nearCity.setColor(Node.NodeColor.BLACK.getColor());
					}
				}
			}
			actualCity.setColor(Node.NodeColor.BLACK.getColor());
		}
		setDefaultColorAndDistanceToAllCities();
		return bonusList;
	}
	
	/**
	 * Sets the default color and distance to all cities for the breadth first search.
	 */
	//Mette il colore di tutti i nodi-città a "WHITE" e le distanze a zero
	private void setDefaultColorAndDistanceToAllCities()
	{
		for( RegionBoard regionBoard : this.getGame().getGameBoard().getRegionBoard() )
		{
			for( City checkCity : regionBoard.getCities() )
			{
				checkCity.setColor("WHITE");
				checkCity.setDistance(0);
			}
		}
	}
	
	/**
	 * Check that player has at least a business permit cards.
	 *
	 * @return true, if successful
	 */
	//controlla che il giocatore abbia almeno un BusinessPermitCard
	public boolean checkBusinessPermitCards()
	{
		if( this.getGame().getCurrentPlayer().getBusinessPermitCards().isEmpty() )
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Check that city chosen is in the business permit card.
	 *
	 * @return true, if successful
	 */
	//controlla se la città inserità è presente nel business permit card
	public boolean checkCity()
	{
		for(int i = 0; i < businessPermitCard.getCities().size(); i++)
		{
			if(businessPermitCard.getCities().get(i).getName().equalsIgnoreCase(city.getName()))
				return true;
		}
		return false;
	}

	/**
	 * Check that player has enough assistants for other emporiums in the city 
	 *
	 * @return true, if successful
	 */
	//controlla che abbia abbastanza aiutanti per gli empori già presenti
	public boolean checkAssistants()
	{
		int countOtherPlayerEmporiums = 0;
		int dimension = city.getEmporiums().length;
		
		for(int i=0; i< dimension; i++)
		{
			if(this.city.getEmporiums()[i]!= null)
				countOtherPlayerEmporiums++;
		}
		if(this.getGame().getCurrentPlayer().getAssistants().getAssistants()>= countOtherPlayerEmporiums)
			return true;
		return false;
	}
	
	/**
	 * Check that player hadn't an emporium in that city yet.
	 *
	 * @return true, if successful
	 */
	//controlla poi che non abbia già costruito lì
	public boolean checkEmporium()
	{
		if(city.getEmporiums()[this.getGame().getCurrentPlayer().getPlayerID()]== null)
			return true;
		return false;
	}
	
	/**
	 * Check that player has at least an emporium.
	 *
	 * @return true, if successful
	 */
	//controlla che abbia empori
	public boolean checkEmporiums()
	{
		if(!this.getGame().getCurrentPlayer().getEmporiumStack().isEmpty())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Builds the emporium using permit card if all conditions are verified.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean buildEmporiumUsingPermitCard() throws SuspendPlayerException
	{		
		if( !this.checkEmporiums() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Action: no emporiums", this.getGame().getCurrentState()));
			return false;
		}
		if( !this.checkBusinessPermitCards() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("You don't have any Business Permit Card.", this.getGame().getCurrentState()));
			return false;
		}
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_BUSINESSPERMITCARDS_FACEUP, this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_ACTION, this.getGame()));		
		
		int choice = 0;
		while(choice <= 0 || choice > this.getGame().getCurrentPlayer().getBusinessPermitCards().size())
		{
			this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_BUSINESSPERMITCARD, this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
			
			try		
			{
				choice = Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice());
				if( choice <= 0 || choice > this.getGame().getCurrentPlayer().getBusinessPermitCards().size() )
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
				}
			}
			catch(NumberFormatException e)
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			}
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		
		BusinessPermitCard checkBusinessPermitCard = this.getGame().getCurrentPlayer().getBusinessPermitCards().get(Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice()) - 1);
		this.setBusinessPermitCard(checkBusinessPermitCard);
		
		this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_CITY, this.getGame().getCurrentState()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		boolean check = false;
		do
		{
			//controlla se ha inserito una città esistente
			while(this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()) == null)
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
				this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_CITY, this.getGame().getCurrentState()));
				
				LockSupport.park();
				this.checkPlayerToSuspend();
			}
			this.setCity(this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()));
			
			//controlla se la città scelta è nel permesso
			if( !this.checkCity() )
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage("There is no [" + this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()).getName() + "] in this permit.", this.getGame().getCurrentState()));
			}
			else
				check = true;
			
			if(!check)
			{
				this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_CITY, this.getGame().getCurrentState()));
				LockSupport.park();
				this.checkPlayerToSuspend();
			}
		}
		while(!check);
		
		//controlla che non abbia già costruito in quella città
		if( !this.checkEmporium() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("You already built an emporium in [" + this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()).getName() + "].", this.getGame().getCurrentState()));
			return false;
		}
		//controlla che abbia abbastanza aiutanti per gli empori già presenti
		else if( !this.checkAssistants() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No enough Assistant for [" + this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()).getName() + "].", this.getGame().getCurrentState()));
			return false;
		}
		this.setCity(this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()));
		this.execute();
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_MODIFIES_STRING, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_CURRENT_PLAYER, this.getGame()));
		return true;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() throws SuspendPlayerException
	{		
		//rimuove gli assistenti di num uguale agli altri giocatori che hanno costruito nella stessa città
		int numAssistants =0;
		for(int i=1;i<city.getEmporiums().length; i++)
		{	
			if(city.getEmporiums()[i] != null)
				numAssistants++;
		}
		this.getGame().getCurrentPlayer().getAssistants().removeAssistants(numAssistants);

		//mette emporio nella città
		List<BonusTile> bonusTiles = city.buildEmporium(this.getGame().getCurrentPlayer().removeEmporium(), this.getGame().getGameBoard().getKingBoard());
		if( !bonusTiles.isEmpty() )
		{
			this.getGame().getCurrentPlayer().addBonusTiles(bonusTiles);
		}
		
		//capovolgo la tessera che ho usato
		for(int i = 0;i<this.getGame().getCurrentPlayer().getBusinessPermitCards().size();i++)
		{
			if(this.getGame().getCurrentPlayer().getBusinessPermitCards().get(i)==businessPermitCard)
				this.getGame().getCurrentPlayer().turnUpsideDownBusinessPermitCard(i);
		}
		
		//raccolgo i bonus di tutte le città vicine
		List<Bonus> bonusArray = this.breadthFirstSearch(city);
		if(!bonusArray.isEmpty())
		{
			for(int i=0; i< bonusArray.size();i++ )
			{
				if( this.getGame().getCurrentState() != null && this.getGame().getCurrentState().countObservers() != 0)
					this.getGame().getCurrentState().notifyObservers(new InfoMessageType("-You are earning: "+ bonusArray.get(i).toString(), this.getGame()));
				try {
					
					bonusArray.get(i).executeBonus(this.getGame());
				} 
				catch (Exception e) 
				{
					log.log( Level.SEVERE, e.toString(), e);
				}
			}
		}
	}
}
