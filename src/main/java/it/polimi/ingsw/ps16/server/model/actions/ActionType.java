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
package it.polimi.ingsw.ps16.server.model.actions;

/**
 * The ENUM ActionType.
 */
public enum ActionType 
{
	
	/** The main action. */
	/* Types of Actions */
	MAIN("Main"), 
	
	/** The quick action. */
	QUICK("Quick"), 
	
	/** The back action. */
	BACK("Back"), 
	
	/** The end anction. */
	END("End"), 
	
	/** The stop action. */
	STOP("Stop"),
	
	/** The acquire action. */
	/* Main Actions */
	ACQUIRE("Acquire"), 
	
	/** The build king action. */
	BUILDKING("BuildKing"), 
	
	/** The build permit action. */
	BUILDPERMIT("BuildPermit"), 
	
	/** The elect. */
	ELECT("Elect"),
	
	/** The add main action. */
	/* Quick Actions */
	ADDMAIN("AddMain"), 
	
	/** The change. */
	CHANGE("Change"), 
	
	/** The electassistant. */
	ELECTASSISTANT("ElectAssistant"), 
	
	/** The engageassistant. */
	ENGAGEASSISTANT("EngageAssistant"),
	
	/** The assistants. */
	/* Market Actions */
	ASSISTANTS("assistants"),
	
	/** The coins. */
	COINS("coins"),
	
	/** The politic card. */
	POLITICCARD("politiccard"),
	
	/** The business permit card. */
	BUSINESSPERMITCARD("businesspermitcard"),
	
	/** nothing. */
	NOTHING("nothing");
	
	/** The message. */
	private final String message;
	
	/**
	 * Instantiates a new action type.
	 *
	 * @param message
	 *            the ENUM message to show
	 */
	private ActionType(String message) 
	{
		this.message=message;
	}
	
	/**
	 * Gets the action type.
	 *
	 * @return the action type
	 */
	public String getActionType() 
	{
		return message;
	}
}
