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
package it.polimi.ingsw.ps16.server.model.turn.marketturn;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.ActionType;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.market.MarketObject;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdatePlayerMessage;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateTimer;
import it.polimi.ingsw.ps16.server.model.player.AssistantsHeap;
import it.polimi.ingsw.ps16.server.model.turn.TurnState;
import it.polimi.ingsw.ps16.server.view.ConnectionView;


/**
 * The Class BuyState.
 */
public class BuyState extends TurnState
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3852799879767967765L;

	/** The object to buy. */
	private MarketObject objectToBuy;
		
	/**
	 * Instantiates a new buy state.
	 *
	 * @param game
	 *            the game
	 * @param view
	 *            the view
	 */
	public BuyState(Game game, ConnectionView view) {
		
		super(game, view);
	}
	
	/**
	 * Find market object.
	 *
	 * @param choice
	 *            the choice
	 * @return the market object
	 */
	//Reperisce l'oggetto da comprare
	private MarketObject findMarketObject(int choice)
	{
		int cho = choice;
		
		for( int i = 0; i < this.getGame().getMarketObjects().size(); i++ )
		{
			if( this.getGame().getMarketObjects().get(i).getSeller() != this.getGame().getCurrentPlayer() )
			{
				cho--;
				if( cho == 0 )
				{
					return this.getGame().getMarketObjects().get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Removes the from market.
	 *
	 * @param choice
	 *            the choice
	 */
	//Rimuove l'oggetto comprato dal market
	private void removeFromMarket(int choice)
	{
		int cho = choice;
		
		int i = 0;
		while( i < this.getGame().getMarketObjects().size() && cho > 0 )
		{
			if( this.getGame().getMarketObjects().get(i).getSeller() != this.getGame().getCurrentPlayer() )
			{
				cho--;
			}
			i++;
		}
		if( cho == 0 )
		{
			this.getGame().getMarketObjects().remove(i-1);
		}
	}
	
	/**
	 * Check assistants.
	 *
	 * @param objectToBuy
	 *            the object to buy
	 * @return true, if successful
	 */
	//Controlla che abbia abbastanza assistenti per comprare l'oggetto
	private boolean checkAssistants(MarketObject objectToBuy)
	{
		if( this.getGame().getCurrentPlayer().getAssistants().getAssistants()
				>= ((AssistantsHeap)objectToBuy.getPrice()).getAssistants() )
			return true;
		return false;	}
	
	/**
	 * Check money.
	 *
	 * @param objectToBuy
	 *            the object to buy
	 * @return true, if successful
	 */
	//Controlla che abbia abbastanza soldi per comprare l'oggetto
	private boolean checkMoney(MarketObject objectToBuy)
	{
		if( this.getGame().getCurrentPlayer().getRichnessMarkerDisc().getPosition()
				>= (Integer)objectToBuy.getPrice() )
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.turn.TurnState#executeMarketState(java.util.List)
	 */
	
	/**
	 * Buy state.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean buyState() throws SuspendPlayerException
	{		
		int marketObjects = 0;
		for( int i = 0; i < this.getGame().getMarketObjects().size(); i++ )
		{
			if( this.getGame().getMarketObjects().get(i).getSeller() != this.getGame().getCurrentPlayer() )
			{
				marketObjects++;
			}
		}
		
		int choice;
		boolean check = false;
		while( !check )
		{
			this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_MARKET_OBJECTS, this.getGame()));
			this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_MARKET_OBJECT, this.getGame().getCurrentState()));
			
			this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_STATE, this.getGame()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
			
			try		
			{
				choice = Integer.parseInt(this.getGame().getCurrentState().getMsg().getChoice());
				if( choice <= 0 || choice > marketObjects )
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input", this.getGame().getCurrentState()));
				}
				else
				{
					this.objectToBuy = this.findMarketObject(choice);
					if( this.objectToBuy.getPrice() instanceof AssistantsHeap )
					{
						if( !this.checkAssistants(this.objectToBuy) )
						{
							this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Not enough assistants", this.getGame().getCurrentState()));
						}
						else
						{
							this.getGame().getCurrentPlayer().getAssistants().removeAssistants(((AssistantsHeap)this.objectToBuy.getPrice()).getAssistants());
							this.objectToBuy.getSeller().getAssistants().addAssistants(((AssistantsHeap)this.objectToBuy.getPrice()).getAssistants());
							
							if( this.objectToBuy.getForSaleObject() instanceof BusinessPermitCard )
							{
								this.getGame().getCurrentPlayer().addBusinessPermitCard((BusinessPermitCard)this.objectToBuy.getForSaleObject());
							}
							else if( this.objectToBuy.getForSaleObject() instanceof PoliticCard )
							{
								this.getGame().getCurrentPlayer().addPoliticCard((PoliticCard)this.objectToBuy.getForSaleObject());
							}
							this.removeFromMarket(choice);
							
							check = true;
						}
					}
					else if( this.objectToBuy.getPrice() instanceof Integer )
					{
						if( !this.checkMoney(this.objectToBuy) )
						{
							this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Not enough coins", this.getGame().getCurrentState()));
						}
						else
						{
							this.getGame().getCurrentPlayer().getRichnessMarkerDisc().moveBack((Integer)this.objectToBuy.getPrice());
							this.objectToBuy.getSeller().getRichnessMarkerDisc().moveForward((Integer)this.objectToBuy.getPrice());
							
							if( this.objectToBuy.getForSaleObject() instanceof BusinessPermitCard )
							{
								this.getGame().getCurrentPlayer().addBusinessPermitCard((BusinessPermitCard)this.objectToBuy.getForSaleObject());
							}
							else if( this.objectToBuy.getForSaleObject() instanceof PoliticCard )
							{
								this.getGame().getCurrentPlayer().addPoliticCard((PoliticCard)this.objectToBuy.getForSaleObject());
							}
							this.removeFromMarket(choice);
							
							check = true;
						}
					}
				}
			}
			catch(NumberFormatException e)
			{
				if( !ActionType.STOP.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()))
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input", this.getGame().getCurrentState()));
				}
				else
				{
					this.getGame().getCurrentState().notifyObservers(new UpdatePlayerMessage(this.getGame()));
					this.getGame().getCurrentState().notifyObservers(new UpdateTimer(this.getGame(), Game.getTimer(), "stop"));
					return true;
				}
			}
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.turn.TurnState#executeMarketState(java.util.List)
	 */
	@Override
	public void executeMarketState(List<MarketObject> marketObjects) throws SuspendPlayerException
	{
		this.getGame().launchTimer(this.getView());

		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_BUY_TURN, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_IS_YOUR_BUY_TURN, this.getGame()));
		
		this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_STATE, this.getGame()));
		
		boolean endTrading = false;
		while( !endTrading )
		{
			endTrading = this.buyState();
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_BUY_TURN_IS_ENDED, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_YOUR_BUY_TURN_IS_ENDED, this.getGame()));
	}

}
