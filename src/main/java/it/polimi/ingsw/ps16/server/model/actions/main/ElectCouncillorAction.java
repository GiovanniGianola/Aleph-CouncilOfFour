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
 * The Class is an action that permit to elect a councillor
 */
public class ElectCouncillorAction extends MainAction
{
	private static final Logger log = Logger.getLogger( ElectCouncillorAction.class.getName() );
	
	/** The councillor chosen. */
	private Councillor councillorChosen;
	
	/** The region chosen. */
	private Object regionChosen;
	
	
	
	/**
	 * Instantiates a new elect councillor action.
	 *
	 * @param game
	 *            the game
	 */
	public ElectCouncillorAction(Game game) 
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
	 * Sets the region of the balcony chosen.
	 *
	 * @param regionChosen
	 *            the new region chosen
	 */
	public void setRegionChosen(RegionBoard regionChosen) 
	{
		this.regionChosen = this.getGame().getGameBoard().searchRegionByName(regionChosen.getName());
	}
	
	/**
	 * Sets the region of the balcony chosen.
	 */
	public void setRegionChosen() 
	{
		this.regionChosen = this.getGame().getGameBoard().getKingBoard();
	}
	
	/**
	 * Elect a councillor.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean electCouncillor() throws SuspendPlayerException
	{				
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_POLITICCARDS, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_BALCONIES, this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_ACTION, this.getGame()));
		
		this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_REGION, this.getGame().getCurrentState()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		while(this.getGame().getGameBoard().searchRegionByName(this.getGame().getCurrentState().getMsg().getChoice()) == null && !this.getGame().getCurrentState().getMsg().getChoice().equalsIgnoreCase(this.getGame().getGameBoard().getKingBoard().getName()))
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input.", this.getGame().getCurrentState()));
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
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Color.", this.getGame().getCurrentState()));
			this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_COUNCILLOR, this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		
		this.setCouncillorChosen(new Councillor(Colour.searchColorByName(this.getGame().getCurrentState().getMsg().getChoice())));

		this.execute();
			
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_MODIFIES_STRING, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_BALCONIES, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_CURRENT_PLAYER, this.getGame()));
		return true;
	}

	@Override
	public void execute() 
	{	
		if(this.regionChosen instanceof RegionBoard)
			((RegionBoard)this.regionChosen).getBalcony().electCouncillor(this.councillorChosen);
		if(this.regionChosen instanceof KingBoard)
			((KingBoard)this.regionChosen).getBalcony().electCouncillor(this.councillorChosen);
		
		try 
		{
			this.getGame().getCurrentPlayer().getRichnessMarkerDisc().moveForward(4);
		}
		catch (Exception e) 
		{
			int limit =this.getGame().getCurrentPlayer().getRichnessMarkerDisc().getLimitPosition();
			int position = this.getGame().getCurrentPlayer().getRichnessMarkerDisc().getPosition();
			try 
			{
				this.getGame().getCurrentPlayer().getRichnessMarkerDisc().moveForward(limit-position);
			} 
			catch (Exception e1) 
			{
				log.log( Level.SEVERE, e1.toString(), e1);
				log.log( Level.SEVERE, e.toString(), e);
			}
	    }	
	}
}
