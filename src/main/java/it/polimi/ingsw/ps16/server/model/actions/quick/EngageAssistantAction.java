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
package it.polimi.ingsw.ps16.server.model.actions.quick;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;

/**
 * The Class EngageAssistantAction ( take an assistant using 3 coins).
 */
public class EngageAssistantAction extends QuickAction 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( EngageAssistantAction.class.getName() );
	
	/**
	 * Instantiates a new engage assistant action.
	 *
	 * @param game
	 *            the game
	 */
	public EngageAssistantAction(Game game) 
	{
		super(game);
	}
	
	
	
	/**
	 * Check that player have enough money to buy an assistant.
	 *
	 * @return true, if successful
	 */
	public boolean checkMoney() 
	{
		if(this.getGame().getCurrentPlayer().getRichnessMarkerDisc().getPosition()>= 3)
			return true;
		return false;
	}
	
	/**
	 * Engage an assistant paying 3 coins.
	 *
	 * @return true, if successful
	 */
	public boolean engageAssitant()
	{		
		if( !this.checkMoney() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No enough Coins, requred at least 3 Coins.", this.getGame().getCurrentState()));
			return false;
		}
		this.execute();
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_MODIFIES_STRING, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_CURRENT_PLAYER, this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_ACTION, this.getGame()));
		
		return true;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() 
	{  //aggiunge un assistente e paga 3 monete
		try 
		{
			this.getGame().getCurrentPlayer().getRichnessMarkerDisc().moveBack(3);
		} 
		catch (Exception e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
		this.getGame().getCurrentPlayer().getAssistants().addAssistants(1);
	}
}
