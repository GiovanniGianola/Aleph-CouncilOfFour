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

/**
 * A factory for creating Bonus objects.
 */
//FINISH
public class BonusFactory implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1577124986138105815L;

	/**
	 * Creates a new Bonus object.
	 *
	 * @param bonusType
	 *            the bonus type
	 * @param quantity
	 *            the quantity
	 * @return the bonus
	 */
	public Bonus createBonus(String bonusType, int quantity)
	{
		switch(bonusType)
		{
			case "assistantbonus": 
				return new AssistantBonus(quantity);
			case "businesspermitcardrewardbonus": 
				return new BusinessPermitCardRewardBonus(quantity);
			case "cityrewardbonus": 
				return new CityRewardBonus(quantity);
			case "coinbonus": 
				return new CoinBonus(quantity);
			case "drawbusinesspermitcardbonus": 
				return new DrawBusinessPermitCardBonus(quantity);
			case "drawpoliticcardbonus": 
				return new DrawPoliticCardBonus(quantity);
			case "mainactionbonus": 
				return new MainActionBonus(quantity);
			case "movemarkernobilitytrackbonus": 
				return new MoveMarkerNobilityTrackBonus(quantity);
			case "victorypointbonus": 
				return new VictoryPointBonus(quantity);
			default :
				return null;
				
		}
	}
}
