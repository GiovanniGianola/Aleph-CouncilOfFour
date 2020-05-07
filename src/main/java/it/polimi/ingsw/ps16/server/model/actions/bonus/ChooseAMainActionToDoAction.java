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

import java.util.concurrent.locks.LockSupport;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.ActionType;
import it.polimi.ingsw.ps16.server.model.actions.main.AcquireBusinessPermitCardAction;
import it.polimi.ingsw.ps16.server.model.actions.main.BuildEmporiumUsingKingAction;
import it.polimi.ingsw.ps16.server.model.actions.main.BuildEmporiumUsingPermitCardAction;
import it.polimi.ingsw.ps16.server.model.actions.main.ElectCouncillorAction;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;


/**
 * The Class ChooseAMainActionToDoAction.
 */
public class ChooseAMainActionToDoAction extends BonusAction
{   
	
	/**
	 * Instantiates a new choose a main action to do action.
	 *
	 * @param game
	 *            the game
	 */
	public ChooseAMainActionToDoAction(Game game) 
	{
		super(game);
	}
	
	
	
	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid() 
	{
		return true;
	}
	
	/**
	 * Choose a main action to do.
	 *
	 * @return true, if successful
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public boolean chooseAMainActionToDo() throws SuspendPlayerException
	{
		this.getGame().getCurrentState().notifyObservers(new RequestMessage(RequestMessageTypeEnum.MAIN_ACTIONS_TWO, this.getGame().getCurrentState()));
		
		LockSupport.park();
		this.checkPlayerToSuspend();
		
		boolean isValidAction = false;
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
		else
		{
			this.getGame().getCurrentState().notifyObservers(new ErrorMessage("Invalid Input.", this.getGame().getCurrentState()));
			return chooseAMainActionToDo();
		}
		if( !isValidAction )
		{
			return chooseAMainActionToDo();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() 
	{
		//eseguire come uno stato che ti sceglie un azione principale
	}
}
