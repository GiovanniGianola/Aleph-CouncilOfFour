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
import it.polimi.ingsw.ps16.server.model.actions.ActionType;
import it.polimi.ingsw.ps16.server.model.bonus.Bonus;
import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.gameboard.Councillor;
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
 * The action that player choose to build an emporium using the king
 */
public class BuildEmporiumUsingKingAction extends MainAction 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( BuildEmporiumUsingKingAction.class.getName() );
	
	/** The politic cards list. */
	private List<PoliticCard> politicCardsList;
	
	/** The city. */
	private City city;
	
	/**
	 * Instantiates a new builds the emporium using king action.
	 *
	 * @param game
	 *            the game
	 */
	public BuildEmporiumUsingKingAction(Game game) 
	{
		super(game);
	}
	
	
	/**
	 * Gets the politic cards list.
	 *
	 * @return the politic cards list
	 */
	public List<PoliticCard> getPoliticCardsList() {
		return politicCardsList;
	}


	/**
	 * Gets the city where build.
	 *
	 * @return the city where build
	 */
	public City getCity() {
		return city;
	}


	/**
	 * Sets the city where build.
	 *
	 * @param city
	 *            the city where player want to build an emporium
	 */
	public void setCity(City city) {
		this.city = city;
	}


	/**
	 * Sets the politic cards list.
	 *
	 * @param politicCardsList
	 *            the new politic cards list
	 */
	public void setPoliticCardsList(List<PoliticCard> politicCardsList) {
		this.politicCardsList = politicCardsList;
	}
	
	//Fa la visita in ampiezza ritornando la distanza tra la città del Re in cui è ora e la città in cui si vuole costruire
	/**
	 * Breadth first search.
	 *
	 * @param kingCity
	 *            the city where is the king
	 * @param cityWhereBuild
	 *            the city where build
	 * @return the distance between the city where the king is and the city where build
	 */
	//Passi come parametri la città in cui è il Re e la città in cui si vuole costruire
	private int breadthFirstSearch(City kingCity, City cityWhereBuild)
	{
		int distance = kingCity.getDistance();
		if( cityWhereBuild.equals(kingCity) )
		{
			return distance;
		}
		
		kingCity.setColor("GREY");
		
		City actualCity; //Città attuale di cui sto controllando le città vicine
		Queue<City> queue = new LinkedList<>();
		queue.add(kingCity);
		
		while( !queue.isEmpty() )
		{
			actualCity = queue.remove();
			
			for( City nearCity /*Città vicina all'actualCity*/ : actualCity.getNearCities() )
			{
				if( (Node.NodeColor.WHITE).equals(nearCity.getColor()) )
				{
					nearCity.setColor(Node.NodeColor.GREY.getColor());
					nearCity.setDistance(actualCity.getDistance() + 1);
					
					if( cityWhereBuild.equals(nearCity) )
					{
						distance = nearCity.getDistance();
						setDefaultColorAndDistanceToAllCities();
						return distance;
					}
					queue.add(nearCity);
				}
			}
			actualCity.setColor(Node.NodeColor.BLACK.getColor());
		}
		//BTW In teoria dovrebbe esistere la città passata quindi non si arriva mai qui
		throw new IllegalArgumentException();
	}
	
	//Fa la visita in ampiezza ritornando la lista di bonus accumulati da tutte le città, in cui il giocatore ha
	//costruito, adiacenti a quella in cui ha appena costruito e così via
	/**
	 * Breadth first search 2.
	 *
	 * @param cityWhereHadBuilt
	 *            the city where had built
	 * @return the bonus' list of all the cities near the city the player has just built 
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
	 * Sets the default color and distance to all cities to do breadth first search.
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
	 * Check that player has at least a politic card
	 *
	 * @return true, if successful
	 */
	//controlla che l'utente abbia almeno una carta politica
	public boolean checkPlayerCards()
	{
		if( !this.getGame().getCurrentPlayer().getPoliticCardsList().isEmpty() )
			return true;
		return false;
	}
	
	/**
	 * Check that player can accommodate the king's balcony 
	 *
	 * @return true, if successful
	 */
	//controlla che l'utente possa soddisfare il balcone del re
	public boolean checkPlayerCardsAndKingBalcony()
	{
		if(this.getGame().getCurrentPlayer().searchCardsExistence(Colour.JOLLY, 1))
			return true;
		
		for( Councillor councillor : this.getGame().getGameBoard().getKingBoard().getBalcony().getBalcony() )
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
	 * Check that player has cards that choose
	 *
	 * @param politicCardsList
	 *            the politic cards list player chose
	 * @return true, if successful
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
	 * Check that cards checks with balcony's coucillors
	 *
	 * @param politicCardsList
	 *            the politic cards list chosen
	 * @return true, if successful
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
				result= this.getGame().getGameBoard().getKingBoard().getBalcony().searchCouncillorPresence(copyCardsList.get(0).getColor(), countNumOfCards );
			
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
	 * Check that player has enough money for missing cards
	 *
	 * @param politicCardsList
	 *            the politic cards list chosen
	 * @return true, if successful
	 */
	//controlla che abbia abbastanza monete per pagare le carte mancanti
	public boolean checkMoneyForCards(List<PoliticCard> politicCardsList)
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
	
	//controlla che abbia abbastanza monete per pagare le carte mancanti
	/**
	 * Check that player has enough money for missing cards and moving king
	 *
	 * @param politicCardsList
	 *            the politic cards list
	 * @return true, if successful
	 */
	// e lo spostamento del re
	public boolean checkMoneyForKingAndCards(List<PoliticCard> politicCardsList){
		
		int money = 0;
		
		if(politicCardsList.size()==1)
			money = money + 10;
		
		else if(politicCardsList.size()==2)
			money = money + 7;
		
		else if(politicCardsList.size()==3)
			money = money + 4;
		
		int jollyMoney= 0;
		
		for(int i=0; i<politicCardsList.size(); i++){
			
			if(politicCardsList.get(i).getColor().equals(Colour.JOLLY))
				jollyMoney++;
		}
		
		money= money + jollyMoney;
		
		int distanceMoney = 2*(this.breadthFirstSearch(this.getGame().getGameBoard().getKing().getCity(),city ));
			
		if(this.getGame().getCurrentPlayer().getRichnessMarkerDisc().getPosition() >= money + distanceMoney)
			return true;
		
		return false;
	} 
	
	/**
	 * Check that player has enough assistants for pay other empories in the city
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
	 * Check that player hadn't built in that city yet
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
	 * Check that player has at least an emporium
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
	 * Builds the empurium using king.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean buildEmpuriumUsingKing() throws SuspendPlayerException
	{
		if( !this.checkEmporiums() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Action: no emporiums", this.getGame().getCurrentState()));
			return false;
		}
		if( !this.checkPlayerCards() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Action: no politic cards", this.getGame().getCurrentState()));
			return false;
		}
		if( !this.checkPlayerCardsAndKingBalcony() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Action: no matches in King balcony", this.getGame().getCurrentState()));
			return false;
		}
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_POLITICCARDS, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_KING_BALCONY, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_REQUEST_SATISFY_KING_COUNCIL, this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_ACTION, this.getGame()));
		
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
		}
		while(( !ActionType.STOP.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()) || checkPoliticCardsList.isEmpty() ) && checkPoliticCardsList.size() < 4);
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		
		//controlla che abbia abbastanza monete per pagare le carte mancanti
		if( !this.checkMoneyForCards(checkPoliticCardsList) )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No enough Coins", this.getGame().getCurrentState()));
			return false;
		}
		this.setPoliticCardsList(checkPoliticCardsList);

		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_KING_POSITION, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_CITIES, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_CITY, this.getGame().getCurrentState()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		boolean check = false;
		do
		{
			//controlla se ha inserito una città esistente
			while(this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()) == null)
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input", this.getGame().getCurrentState()));
				this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_CITY, this.getGame().getCurrentState()));
				
				LockSupport.park();
				this.checkPlayerToSuspend();
			}
			this.setCity(this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()));
						
			//controlla che non abbia già costruito in quella città
			if( !this.checkEmporium() )
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage("You already built an emporium in [" + this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()).getName() + "].", this.getGame().getCurrentState()));
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
		
		//controlla se ha abbastanza monete per pagare le carte mancanti e spostare il re
		if( !this.checkMoneyForKingAndCards(checkPoliticCardsList) )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No enough Coins to move King in [" + this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()).getName() + "].", this.getGame().getCurrentState()));
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
		//scarto le carte dal mazzo del player
		List<Colour> colorArray = new ArrayList<>();
			
		for(int i=0; i< politicCardsList.size(); i++){
				
			colorArray.add(politicCardsList.get(i).getColor());
		}
			
		this.getGame().getCurrentPlayer().discardCards(colorArray);
			
		//aggiungo le carte al mazzo di scarti
		for(int i=0; i<politicCardsList.size();i++){
				
			this.getGame().getGameBoard().getDiscardedPoliticCardsDeck().addCard(politicCardsList.get(i));
		}
			
		//il player paga le dovute monete ( per le carte e per lo spostamento del re )
			
		int money = 0;
			
		if(politicCardsList.size()==1)				
			money = money + 10;
			
		else if(politicCardsList.size()==2)
			money = money + 7;
			
		else if(politicCardsList.size()==3)
			money = money + 4;
			
		int jollyMoney= 0;
			
		for(int i=0; i<politicCardsList.size(); i++){
				
			if(politicCardsList.get(i).getColor().equals(Colour.JOLLY))
				jollyMoney++;
		}
			
		money= money + jollyMoney;
		
		int distanceMoney = 2*(this.breadthFirstSearch(this.getGame().getGameBoard().getKing().getCity(),city ));
	
		if( this.getGame().getCurrentState() != null && this.getGame().getCurrentState().countObservers() != 0)
		{
			this.getGame().getCurrentPlayer().getRichnessMarkerDisc().moveBack(money + distanceMoney);
			this.getGame().getCurrentState().notifyObservers(new InfoMessageType("\n-The King took: " + (distanceMoney/2) + " steps, " + "you are spending: " + distanceMoney + " coins.", this.getGame()));
		}
		
		//spostamento del re
		this.getGame().getGameBoard().getKing().setCity(this.city);
		
		//rimuove gli assistenti di num uguale agli altri giocatori che hanno costruito nella stessa città
		int numAssistants =0;
		for(int i=1;i<city.getEmporiums().length; i++){
			
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
		
		//applica i bonus delle città adiacenti in cui ha costruito
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
