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

import java.util.List;
import java.util.concurrent.locks.LockSupport;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.bonus.Bonus;
import it.polimi.ingsw.ps16.server.model.bonus.MoveMarkerNobilityTrackBonus;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;
import it.polimi.ingsw.ps16.server.model.player.Player;


/**
 * The Class ChooseARewardInCitiesWithPlayerEmporiumAction.
 */
public class ChooseARewardInCitiesWithPlayerEmporiumAction extends BonusAction
{	
	
	/** The city. */
	private City city;
	
	/**
	 * Instantiates a new choose a reward in cities with player emporium action.
	 *
	 * @param game
	 *            the game
	 */
	public ChooseARewardInCitiesWithPlayerEmporiumAction(Game game) {
		
		super(game);
	}
	
	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Sets the city where take bonus.
	 *
	 * @param city
	 *            the new city
	 */
	public void setCity(City city) {
		this.city = city;
	}
	
	/**
	 * Check that city is in those without nobilityTrackBonus where have built.
	 *
	 * @return true, if successful
	 */
	//controlla se la città inserità è presente tra quelle senza NobilityTrackBonus in cui ha costruito
	public boolean checkCity()
	{
		if(this.getCity().getEmporiums()[this.getGame().getCurrentPlayer().getPlayerID()] != null)
		{
			for(Bonus bonus : this.getCity().getBonus())
			{
				if(bonus instanceof MoveMarkerNobilityTrackBonus)
					return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Check that player have built an emporium yet.
	 *
	 * @return true, if successful
	 */
	//controlla che abbia costruito un emporio in almeno una città
	public boolean checkCities()
	{
		if( this.getGame().getCurrentPlayer().getEmporiumStack().size() < Player.getNumberOfEmporiums() )
			return true;
		return false;
	}
	
	/**
	 * Check that at least one city where built don't have a MoveMarkerNobilityTrackBonus.
	 *
	 * @return true, if successful
	 */
	//controlla che almeno una città in cui ha costruito non abbia un MoveMarkerNobilityTrackBonus
	public boolean checkNobilityCities()
	{
		boolean haveNobilityBonus = false;
		for(RegionBoard regionBoard : this.getGame().getGameBoard().getRegionBoard())
		{
			for(City checkCity : regionBoard.getCities())
			{
				if(checkCity.getEmporiums()[this.getGame().getCurrentPlayer().getPlayerID()] != null)
				{
					for(int i =0; i< checkCity.getBonus().size() && !haveNobilityBonus;i++)
					{	
						if(checkCity.getBonus().get(i) instanceof MoveMarkerNobilityTrackBonus)
							haveNobilityBonus = true;									
					}
					if(haveNobilityBonus)
					{
						haveNobilityBonus = false;
					}
					else
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Check that there is a city where had built of which player has not got bonuses yet.
	 *
	 * @param citiesChoosen
	 *            the cities choosen
	 * @return true, if successful
	 */
	//controlla che ci sia una città, tra quelle compatibili in cui ha costruito, di cui non abbia già preso il bonus
	public boolean checkCitiesBonus(List<City> citiesChoosen)
	{
		int totalCitiesWhereTakeBonus = 0;
		boolean haveNobilityBonus = false;
		for(RegionBoard regionBoard : this.getGame().getGameBoard().getRegionBoard())
		{
			for(City city : regionBoard.getCities())
			{
				if(city.getEmporiums()[this.getGame().getCurrentPlayer().getPlayerID()] != null)
				{
					for(int i =0; i< city.getBonus().size() && !haveNobilityBonus;i++)
					{	
						if(city.getBonus().get(i) instanceof MoveMarkerNobilityTrackBonus)
							haveNobilityBonus = true;									
					}
					if(haveNobilityBonus)
					{
						haveNobilityBonus = false;
					}
					else
					{
						totalCitiesWhereTakeBonus++;
					}
				}
			}
		}
		if(citiesChoosen.isEmpty() || citiesChoosen.size() < totalCitiesWhereTakeBonus)
			return true;
		return false;
	}
	
	/**
	 * Check that player has not got bonuses of city chosen yet.
	 *
	 * @param citiesChoosen
	 *            the cities chosen
	 * @return true, if successful
	 */
	//controlla che la città scelta non sia tra quelle di cui ha già preso il bonus
	public boolean checkCityBonus(List<City> citiesChoosen)
	{
		for(City checkCity : citiesChoosen)
		{
			if(checkCity.equals(this.getCity()))
				return false;
		}
		return true;
	}
	
	/**
	 * Choose a reward in cities with player emporium if all conditions are verified.
	 *
	 * @param citiesChoosen
	 *            the cities choosen
	 * @return the city
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public City chooseARewardInCitiesWithPlayerEmporium(List<City> citiesChoosen) throws SuspendPlayerException
	{
		if( !this.checkCities() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No emporiums built.", this.getGame().getCurrentState()));
			return null;
		}
		if( !this.checkNobilityCities() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No cities without nobility bonus.", this.getGame().getCurrentState()));
			return null;
		}
		if( !this.checkCitiesBonus(citiesChoosen) )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No more cities where take bonus.", this.getGame().getCurrentState()));
			return null;
		}
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_CITIES_WHERE_BUILT_CURRENT_PLAYER, this.getGame()));
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
			
			//controlla se la città scelta è effettivamente tra quelle senza NobilityTrackBonus in cui ha costruito
			if( !this.checkCity() )
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage("There is no [" + this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()).getName() + "] in your choices.", this.getGame().getCurrentState()));
			}
			//controlla se la città scelta è tra quelle in cui non ho ancora preso i bonus
			else if( !this.checkCityBonus(citiesChoosen) )
			{
				this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Already taken bonus from [" + this.getGame().getGameBoard().searchCityByName(this.getGame().getCurrentState().getMsg().getChoice()).getName() + "].", this.getGame().getCurrentState()));
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
		this.execute();
		
		return this.getCity();
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() throws SuspendPlayerException
	{
		for(int i = 0; i<city.getBonus().size(); i++ )
		{
			city.getBonus().get(i).executeBonus(this.getGame());
		}
	}
}
