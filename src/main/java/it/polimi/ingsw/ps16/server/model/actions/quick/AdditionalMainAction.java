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
import it.polimi.ingsw.ps16.server.model.actions.ActionType;
import it.polimi.ingsw.ps16.server.model.actions.bonus.ChooseARewardInAllBusinessPermitCardAction;
import it.polimi.ingsw.ps16.server.model.actions.main.AcquireBusinessPermitCardAction;
import it.polimi.ingsw.ps16.server.model.actions.main.BuildEmporiumUsingKingAction;
import it.polimi.ingsw.ps16.server.model.actions.main.BuildEmporiumUsingPermitCardAction;
import it.polimi.ingsw.ps16.server.model.actions.main.ElectCouncillorAction;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;

/**
 * The Class AdditionalMainAction ( do an other main action paying assistants).
 */
public class AdditionalMainAction extends QuickAction 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ChooseARewardInAllBusinessPermitCardAction.class.getName() );
	
	/**
	 * Instantiates a new additional main action.
	 *
	 * @param game
	 *            the game
	 */
	public AdditionalMainAction(Game game) 
	{
		super(game);
	}
	
	/**
	 * Check that player has enough assistants to do an other main action.
	 *
	 * @return true, if successful
	 */
	//Controlla che il giocatore abbia abbastanza assistenti per eseguire un azione principale aggiuntiva
	public boolean checkAssistants() 
	{
		if(this.getGame().getCurrentPlayer().getAssistants().getAssistants()>=3)
			return true;
		return false;
	}
	
	/**
	 * Do an additional main action.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean additionalMainAction() throws SuspendPlayerException
	{
		if( !this.checkAssistants() )
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Not enough assistants, requred at least 3 Assistant.", this.getGame().getCurrentState()));
			return false;
		}
		this.execute();		
		this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.MAIN_ACTIONS_ONE, this.getGame().getCurrentState()));
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_ACTION, this.getGame()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		boolean isValidAction;
		if(ActionType.ACQUIRE.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()))
		{
			AcquireBusinessPermitCardAction actionRequested = new AcquireBusinessPermitCardAction(this.getGame());
			isValidAction = actionRequested.acquireBusinessPermitCard();
		}
		else if(ActionType.BUILDKING.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()))
		{
			BuildEmporiumUsingKingAction actionRequested = new BuildEmporiumUsingKingAction(this.getGame());
			isValidAction = actionRequested.buildEmpuriumUsingKing();
		}
		else if(ActionType.BUILDPERMIT.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()))
		{
			BuildEmporiumUsingPermitCardAction actionRequested = new BuildEmporiumUsingPermitCardAction(this.getGame());
			isValidAction = actionRequested.buildEmporiumUsingPermitCard();
		}
		else if(ActionType.ELECT.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()))
		{
			ElectCouncillorAction actionRequested = new ElectCouncillorAction(this.getGame());
			isValidAction = actionRequested.electCouncillor();
		}
		else if(ActionType.BACK.getActionType().equalsIgnoreCase(this.getGame().getCurrentState().getMsg().getChoice()))
		{
			this.getGame().getCurrentPlayer().getAssistants().addAssistants(3);
			return false;
		}
		else
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage(this.getInvalidInput(), this.getGame().getCurrentState()));
			return additionalMainAction();
		}
		if( !isValidAction )
		{
			return additionalMainAction();
		}
		
		this.getGame().notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_CHOICE, this.getGame()));
		
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
			this.getGame().getCurrentPlayer().getAssistants().removeAssistants(3);
		} 
		catch (Exception e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
		
	}
}
