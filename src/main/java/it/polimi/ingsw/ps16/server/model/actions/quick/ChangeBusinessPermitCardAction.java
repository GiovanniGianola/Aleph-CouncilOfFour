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
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;

/**
 * The Class ChangeBusinessPermitCardAction (  the player changes the b cards in a region with new ones).
 */
public class ChangeBusinessPermitCardAction extends QuickAction 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ChangeBusinessPermitCardAction.class.getName() );
	
	/** The region board. */
	private RegionBoard regionBoard;
	
	/**
	 * Instantiates a new change business permit card action.
	 *
	 * @param game
	 *            the game
	 */
	public ChangeBusinessPermitCardAction(Game game) 
	{
		super(game);
	}
	
	/**
	 * Gets the region board where player want to change cards.
	 *
	 * @return the region board
	 */
	public RegionBoard getRegionBoard() {
		return regionBoard;
	}

	/**
	 * Sets the region chosen where change cards.
	 *
	 * @param regionBoard
	 *            the new region chosen
	 */
	public void setRegionChosen(RegionBoard regionBoard) {
		this.regionBoard = regionBoard;
	}

	/**
	 * Check player has enough assistants to do this action.
	 *
	 * @return true, if successful
	 */
	public boolean checkAssistants()
	{
		if(this.getGame().getCurrentPlayer().getAssistants().getAssistants()>=1)
			return true;
		return false;
	}
	
	/**
	 * Check that business permit cards deck is not empty.
	 *
	 * @return true, if successful
	 */
	public boolean checkBusinessPermitCardsDeck()
	{
		if( !regionBoard.getBusinessPermitCardsDeck().getcards().isEmpty() )
			return true;
		return false;
	}
	
	/**
	 * Check that at least a business permit cards deck of a region is not empty.
	 *
	 * @return true, if successful
	 */
	public boolean checkBusinessPermitCardsDeckAll()
	{
		for( RegionBoard checkRegionBoard : this.getGame().getGameBoard().getRegionBoard() )
		{
			if( !checkRegionBoard.getBusinessPermitCardsDeck().getcards().isEmpty() )
				return true;
		}
		return false;
	}
	
	/**
	 * Change business permit card of the region chosen.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean changeBusinessPermitCard() throws SuspendPlayerException
	{
		if( !this.checkAssistants() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Not enough Assistants, requred at least 1 Assistant.", this.getGame().getCurrentState()));
			return false;
		}
		if( !this.checkBusinessPermitCardsDeckAll() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("No more Business Permit Cards.", this.getGame().getCurrentState()));
			return false;
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_ACTION, this.getGame()));
		
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_REGIONS_BUSINESSPERMITCARDS2, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_REGION, this.getGame().getCurrentState()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		while(this.getGame().getGameBoard().searchRegionByName(this.getGame().getCurrentState().getMsg().getChoice()) == null)
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_REGION, this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		
		this.setRegionChosen(this.getGame().getGameBoard().searchRegionByName(this.getGame().getCurrentState().getMsg().getChoice()));
		if( !this.checkBusinessPermitCardsDeck() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("BusinessPermitCardsDeck of " + this.getRegionBoard().getName() + " empty", this.getGame().getCurrentState()));
			return false;
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
	public void execute() 
	{
		try {
			
			this.getGame().getCurrentPlayer().getAssistants().removeAssistants(1);
		} 
		catch (Exception e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
		
		regionBoard.getBusinessPermitCardsDeck().addCard(regionBoard.drawBusinessPermitTileOne());
		
		regionBoard.getBusinessPermitCardsDeck().addCard(regionBoard.drawBusinessPermitTileTwo());
		
	}
}
