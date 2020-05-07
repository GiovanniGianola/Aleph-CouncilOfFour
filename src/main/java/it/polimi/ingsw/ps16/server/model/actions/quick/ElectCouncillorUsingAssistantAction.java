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

import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.gameboard.Councillor;
import it.polimi.ingsw.ps16.server.model.gameboard.KingBoard;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;


/**
 * The Class ElectCouncillorUsingAssistantAction (elect a councillor using an assistant).
 */
public class ElectCouncillorUsingAssistantAction extends QuickAction 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ElectCouncillorUsingAssistantAction.class.getName() );
	
	/** The councillor chosen. */
	private Councillor councillorChosen;
	
	/** The region chosen. */
	private Object regionChosen;
	

	/**
	 * Instantiates a new elect councillor using assistant action.
	 *
	 * @param game
	 *            the game
	 */
	public ElectCouncillorUsingAssistantAction(Game game) 
	{
		super(game);
	}
	
	/**
	 * Sets the councillor chosen.
	 *
	 * @param councillorChosen
	 *            the new councillor chosen
	 */
	public void setCouncillorChosen(Councillor councillorChosen) 
	{
		this.councillorChosen = councillorChosen;
	}

	/**
	 * Sets the region of balcony chosen.
	 *
	 * @param regionChosen
	 *            the new region chosen
	 */
	public void setRegionChosen(RegionBoard regionChosen) 
	{
		this.regionChosen = this.getGame().getGameBoard().searchRegionByName(regionChosen.getName());
	}
	
	/**
	 * Sets the region of balcony chosen.
	 */
	public void setRegionChosen() 
	{
		this.regionChosen = this.getGame().getGameBoard().getKingBoard();
	}
	
	/**
	 * Check player has an assistant. 
	 *
	 * @return true, if successful
	 */
	public boolean checkAssistants() 
	{
		if(this.getGame().getCurrentPlayer().getAssistants().getAssistants() >= 1)
			return true;
		return false;
	}
	
	/**
	 * Elect councillor using assistant, if condition is verified.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean electCouncillorUsingAssistant() throws SuspendPlayerException
	{
		if( !this.checkAssistants() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Not enough Assistants, requred at least 1 Assistant.", this.getGame().getCurrentState()));
			return false;
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_ACTION, this.getGame()));
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_BALCONIES, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_REGION, this.getGame().getCurrentState()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		while(this.getGame().getGameBoard().searchRegionByName(this.getGame().getCurrentState().getMsg().getChoice()) == null && !this.getGame().getCurrentState().getMsg().getChoice().equalsIgnoreCase(this.getGame().getGameBoard().getKingBoard().getName()))
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_REGION, this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		
		if(this.getGame().getGameBoard().searchRegionByName(this.getGame().getCurrentState().getMsg().getChoice()) != null)
			this.setRegionChosen(this.getGame().getGameBoard().searchRegionByName(this.getGame().getCurrentState().getMsg().getChoice()));
		else if(this.getGame().getCurrentState().getMsg().getChoice().equalsIgnoreCase(this.getGame().getGameBoard().getKingBoard().getName()))
			this.setRegionChosen();
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_COUNCILLORS_COLOR, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_COUNCILLOR, this.getGame().getCurrentState()));
			
		LockSupport.park();
		this.checkPlayerToSuspend();
				
		while(Colour.JOLLY.toString().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()) || Colour.searchColorByName(this.getGame().getCurrentState().getMsg().getChoice()) == null)
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_COUNCILLOR, this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
		}
		
		this.setCouncillorChosen(new Councillor(Colour.searchColorByName(this.getGame().getCurrentState().getMsg().getChoice())));
		this.execute();
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_MODIFIES_STRING, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_BALCONIES, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_CURRENT_PLAYER, this.getGame()));
		return true;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() 
	{
		try 
		{
			this.getGame().getCurrentPlayer().getAssistants().removeAssistants(1);
		} 
		catch (Exception e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
		
		if(this.regionChosen instanceof RegionBoard)
		{	
			((RegionBoard)this.regionChosen).getBalcony().electCouncillor(this.councillorChosen);
		}
		
		if(this.regionChosen instanceof KingBoard)
		{	
			((KingBoard)this.regionChosen).getBalcony().electCouncillor(this.councillorChosen);		
		}
	}
}
