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
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.market.MarketObject;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateTimer;
import it.polimi.ingsw.ps16.server.model.player.AssistantsHeap;
import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.model.turn.TurnState;
import it.polimi.ingsw.ps16.server.view.ConnectionView;

/**
 * The Class SaleState.
 */
public class SaleState extends TurnState
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5251462528310538449L;
	
	/** The Constant MATCH. */
	private static final String MATCH = "^[0-9]+$";

	/**
	 * Instantiates a new sale state.
	 *
	 * @param game
	 *            the game
	 * @param view
	 *            the view
	 */
	public SaleState(Game game, ConnectionView view) 
	{
		super(game, view);
	}
	
	/**
	 * Check player cards.
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
	 * Check business permit cards.
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
	 * Ask price.
	 *
	 * @param marketObjects
	 *            the market objects
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	private void askPrice(List<MarketObject> marketObjects) throws SuspendPlayerException
	{
		String choice;
		do
		{
			this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_MONEY_OR_ASSISTANT,this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
			
			choice = this.getMsg().getChoice();
			if(ActionType.ASSISTANTS.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
			{	
				this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_THE_PRICE, this.getGame().getCurrentState()));
				
				LockSupport.park();
				this.checkPlayerToSuspend();
				
				while(!this.getMsg().getChoice().matches(MATCH) && Integer.parseInt(this.getMsg().getChoice())<1 && Integer.parseInt(this.getMsg().getChoice())>15)
				{
					this.notifyObservers(new ErrorMessage(this.getInvalidInput(),this.getGame().getCurrentState()));
					this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_THE_PRICE, this.getGame().getCurrentState()));
					
					LockSupport.park();
					this.checkPlayerToSuspend();
				}
				int price = Integer.parseInt(this.getMsg().getChoice());
				marketObjects.get(marketObjects.size()-1).setPrice(new AssistantsHeap(price));
			}
			else if(ActionType.COINS.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
			{
				this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_THE_PRICE, this.getGame().getCurrentState()));
				
				LockSupport.park();
				this.checkPlayerToSuspend();
				
				while(!this.getMsg().getChoice().matches(MATCH) && Integer.parseInt(this.getMsg().getChoice())<1 && Integer.parseInt(this.getMsg().getChoice())>  Player.getRichnessLimit() )
				{
					this.notifyObservers(new ErrorMessage(this.getInvalidInput(),this.getGame().getCurrentState()));
					this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_THE_PRICE, this.getGame().getCurrentState()));
					
					LockSupport.park();
					this.checkPlayerToSuspend();
				}
				int price = Integer.parseInt(this.getMsg().getChoice());
				marketObjects.get(marketObjects.size()-1).setPrice(price);
			}
			else
			{
				this.notifyObservers(new ErrorMessage("Invalid Input",this.getGame().getCurrentState()));
			}
		}
		while(!ActionType.ASSISTANTS.getActionType().equalsIgnoreCase(choice) && !ActionType.COINS.getActionType().equalsIgnoreCase(choice));
	}
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.turn.TurnState#executeMarketState(java.util.List)
	 */
	@Override
	public void executeMarketState(List<MarketObject> marketObjects) throws SuspendPlayerException
	{
		this.getGame().launchTimer(this.getView());
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_SALE_TURN, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_IS_YOUR_SALE_TURN, this.getGame()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_STATE, this.getGame()));
		
		this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_STATE, this.getGame()));
		
		boolean isSelling= true;
		//finchè l'utente non schiaccia nothing
		
		while(isSelling)
		{
			this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_BUSINESSPERMITCARDS_FACEUP, this.getGame()));
			this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_POLITICCARDS, this.getGame()));	
			this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_WHAT_TO_SELL,this.getGame().getCurrentState()));
			
			LockSupport.park();
			this.checkPlayerToSuspend();
			
			if(ActionType.POLITICCARD.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
			{
				if( !this.checkPlayerCards() )
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Action: no politic cards", this.getGame().getCurrentState()));
				}
				else
				{
					this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_POLITICCARDS,this.getGame()));
					
					while(!ActionType.STOP.getActionType().equalsIgnoreCase(this.getMsg().getChoice()) && !this.getGame().getCurrentPlayer().getPoliticCardsList().isEmpty())
					{
						this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_POLITICCARDS,this.getGame().getCurrentState()));
						
						LockSupport.park();
						this.checkPlayerToSuspend();

						if(Colour.searchColorByName(this.getMsg().getChoice())!=null)
						{
							if(this.getGame().getCurrentPlayer().searchCardsExistence( Colour.searchColorByName(this.getMsg().getChoice()),1)){
								
								marketObjects.add(new MarketObject());
								marketObjects.get(marketObjects.size()-1).setSeller(this.getGame().getCurrentPlayer());
								marketObjects.get(marketObjects.size()-1).setForSaleObject(this.getGame().getCurrentPlayer().saleCard(this.getMsg().getChoice()));
								this.askPrice(marketObjects);
							}
							else
							{
								this.notifyObservers(new ErrorMessage("You don't have this card",this.getGame().getCurrentState()));
							}
						}	
						else if(!this.getMsg().getChoice().equalsIgnoreCase(ActionType.STOP.toString()))
						{
							this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
						}
					}
				}
			}
			else if(ActionType.BUSINESSPERMITCARD.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
			{
				if( !this.checkBusinessPermitCards() )
				{
					this.getGame().getCurrentState().notifyObservers(new ErrorMessage("You don't have any Business Permit Card.", this.getGame().getCurrentState()));
				}
				else
				{
					this.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_BUSINESSPERMITCARDS_FACEUP,this.getGame()));
					
					while(!ActionType.STOP.getActionType().equalsIgnoreCase(this.getMsg().getChoice()) && !this.getGame().getCurrentPlayer().getBusinessPermitCards().isEmpty() ){
						
						this.notifyObservers(new RequestMessage(RequestMessageTypeEnum.ASK_BUSINESSPERMITCARD,this.getGame().getCurrentState()));
						
						LockSupport.park();
						this.checkPlayerToSuspend();
						
						if(this.getMsg().getChoice().matches(MATCH) && Integer.parseInt(this.getMsg().getChoice())>= 1 && Integer.parseInt(this.getMsg().getChoice())<= this.getGame().getCurrentPlayer().getBusinessPermitCards().size())
						{
							marketObjects.add(new MarketObject());
							marketObjects.get(marketObjects.size()-1).setSeller(this.getGame().getCurrentPlayer());
							marketObjects.get(marketObjects.size()-1).setForSaleObject(this.getGame().getCurrentPlayer().saleBusinessCard(Integer.parseInt(this.getMsg().getChoice())-1));
							this.askPrice(marketObjects);
						}
						else if(!this.getMsg().getChoice().equalsIgnoreCase(ActionType.STOP.toString()))
						{
							this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
						}
					}
				}
			}
			else if(ActionType.NOTHING.getActionType().equalsIgnoreCase(this.getMsg().getChoice()))
			{
				isSelling=false;
				this.getGame().getCurrentState().notifyObservers(new UpdateTimer(this.getGame(), Game.getTimer(), "stop"));
			}
			else
			{
				this.notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			}	
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_SALE_TURN_IS_ENDED, this.getGame()));
		this.getGame().getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_YOUR_SALE_TURN_IS_ENDED, this.getGame()));
	} 
}
