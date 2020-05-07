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
 package it.polimi.ingsw.ps16.server.model.bonus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.bonus.ChooseARewardInCitiesWithPlayerEmporiumAction;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.City;


/**
 * The Class CityRewardBonus.
 */
public class CityRewardBonus  extends Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6495361583982032179L;

	/**
	 * Instantiates a new city reward bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public CityRewardBonus(int quantity)
	{
		super(quantity);
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.bonus.Bonus#executeBonus(it.polimi.ingsw.ps16.server.model.Game)
	 */
	@Override
	public void executeBonus(Game game) throws SuspendPlayerException
	{
		ChooseARewardInCitiesWithPlayerEmporiumAction action = new ChooseARewardInCitiesWithPlayerEmporiumAction(game);
		
		List<City> citiesChoosen = new ArrayList<>();
		City newCity = null;
		
		boolean noCitiesReward = false;
		for( int i = 0; i < this.getQuantity() && !noCitiesReward; i++ )
		{
			newCity = action.chooseARewardInCitiesWithPlayerEmporium(citiesChoosen);
			if(newCity != null)
			{
				citiesChoosen.add(newCity);
			}
			else
			{
				noCitiesReward = true;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "CityRewardBonus with value: " + this.getQuantity();
	}
}
