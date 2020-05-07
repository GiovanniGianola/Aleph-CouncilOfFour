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
package it.polimi.ingsw.ps16.server.model.player;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;

/**
 * The Class Player contains all the informations about a player.
 */
public class Player implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7666104121086076494L;
	
	/** The Constant VICTORY_LIMIT. */
	private static final int VICTORY_LIMIT = Integer.MAX_VALUE;
	
	/** The Constant RICHNESS_LIMIT. */
	private static final int RICHNESS_LIMIT = 20;
	
	/** The Constant NOBILITY_LIMIT. */
	private static final int NOBILITY_LIMIT = 20;
	
	/** The Constant NUMBER_OF_EMPORIUMS. */
	private static final int NUMBER_OF_EMPORIUMS = 10;
	
	/** The suspended. */
	private boolean suspended;
	
	/** The player ID. */
	private final int playerID;
	
	/** The name. */
	private String name;
	
	/** The color. */
	private Color color;
	
	/** The emporium stack. */
	private final List<Emporium> emporiumStack;
	
	/** The assistants. */
	private final AssistantsHeap assistants;
	
	/** The victory marker disc. */
	private final MarkerDisc victoryMarkerDisc;
	
	/** The nobility marker disc. */
	private final MarkerDisc nobilityMarkerDisc;
	
	/** The richness marker disc. */
	private final MarkerDisc richnessMarkerDisc;
	
	/** The politic cards list. */
	private final List<PoliticCard> politicCardsList;
	
	/** The business permit cards. */
	private final List<BusinessPermitCard> businessPermitCards;
	
	/** The business permit cards face down. */
	private final List<BusinessPermitCard> businessPermitCardsFaceDown;
	
	/** The bonus tiles. */
	private final List<BonusTile> bonusTiles;
	

	/**
	 * Instantiates a new player.
	 *
	 * @param playerID
	 *            the player ID
	 * @param nAssistants
	 *            the n assistants
	 * @param posRichness
	 *            the pos richness
	 */
	public Player(int playerID, int nAssistants, int posRichness)
	{
		this.playerID = playerID;
		this.name = null;
		this.color = null;
		
		emporiumStack = new ArrayList<>();	     
		for(int i=0;i<NUMBER_OF_EMPORIUMS;i++)
	    {
			emporiumStack.add(new Emporium(playerID));
	    }
		 
		assistants = new AssistantsHeap(nAssistants);		 
		victoryMarkerDisc = new MarkerDisc(playerID,0,VICTORY_LIMIT);		 
		nobilityMarkerDisc = new MarkerDisc(playerID,0,NOBILITY_LIMIT);		 
		richnessMarkerDisc = new MarkerDisc(playerID,posRichness,RICHNESS_LIMIT);		 
		politicCardsList = new ArrayList<>();		
		businessPermitCards= new ArrayList<>();		
		businessPermitCardsFaceDown = new ArrayList<>();		
		bonusTiles = new ArrayList<>();
		
		this.suspended = false;
	}
	
	
	
	/**
	 * Gets the number of emporiums.
	 *
	 * @return the number of emporiums
	 */
	public static int getNumberOfEmporiums()
	{
		return Player.NUMBER_OF_EMPORIUMS;
	}
	
	/**
	 * Gets the richness limit of the richness track.
	 *
	 * @return the richness limit
	 */
	public static int getRichnessLimit()
	{
		return Player.RICHNESS_LIMIT;
	}
	
	/**
	 * suspend the current player.
	 *
	 * @param suspended
	 *            the new suspend
	 */
	public void setSuspend(boolean suspended)
	{
		this.suspended = suspended;
	}
	
	/**
	 * Checks if the current is suspended.
	 *
	 * @return true, if is suspended
	 */
	public boolean isSuspended()
	{
		return suspended;
	}
	
	/**
	 * Gets the player ID.
	 *
	 * @return the player ID
	 */
	public int getPlayerID() 
	{
		return playerID;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Gets the string of the color of the player.
	 *
	 * @return the color string
	 */
	public String getColorString() 
	{
		return this.printColor(this.color);
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() 
	{
		return this.color;
	}
	
	/**
	 * Sets the color.
	 *
	 * @param color
	 *            the new color
	 */
	public void setColor(Color color) 
	{
		this.color = color;
	}

	/**
	 * Gets the emporium stack.
	 *
	 * @return the emporium stack
	 */
	public List<Emporium> getEmporiumStack() 
	{
		return emporiumStack;
	}

	/**
	 * Gets the assistants.
	 *
	 * @return the assistants
	 */
	public AssistantsHeap getAssistants() 
	{
		return assistants;
	}

	/**
	 * Gets the victory marker disc.
	 *
	 * @return the victory marker disc
	 */
	public MarkerDisc getVictoryMarkerDisc() 
	{
		return victoryMarkerDisc;
	}

	/**
	 * Gets the richness marker disc.
	 *
	 * @return the richness marker disc
	 */
	public MarkerDisc getRichnessMarkerDisc() 
	{
		return richnessMarkerDisc;
	}

	/**
	 * Gets the nobility marker disc.
	 *
	 * @return the nobility marker disc
	 */
	public MarkerDisc getNobilityMarkerDisc() 
	{
		return nobilityMarkerDisc;
	}

	/**
	 * Gets the politic cards list.
	 *
	 * @return the politic cards list
	 */
	public List<PoliticCard> getPoliticCardsList() 
	{
		return politicCardsList;
	}

	/**
	 * Gets the business permit cards.
	 *
	 * @return the business permit cards
	 */
	public List<BusinessPermitCard> getBusinessPermitCards() 
	{
		return businessPermitCards;
	}

	/**
	 * Gets the business permit cards face down.
	 *
	 * @return the business permit cards face down
	 */
	public List<BusinessPermitCard> getBusinessPermitCardsFaceDown() 
	{
		return businessPermitCardsFaceDown;
	}

	/**
	 * Gets the bonus tiles.
	 *
	 * @return the bonus tiles
	 */
	public List<BonusTile> getBonusTiles() 
	{
		return bonusTiles;
	}

	/**
	 * Removes the emporium.
	 *
	 * @return the emporium
	 */
	public Emporium removeEmporium()
	{
		return emporiumStack.remove(0);
	}
	
	/**
	 * Adds the politic card.
	 *
	 * @param politicCard
	 *            the politic card
	 */
	public void addPoliticCard(PoliticCard politicCard)
	{
		politicCardsList.add(politicCard);
	}
	
	//controlla che esistano carte di quel colore( le carte dello stesso colore le 
	/**
	 * check if the player has a politic card n times.
	 *
	 * @param color
	 *            the color of the politic card
	 * @param numColoredCards
	 *            the number colored cards
	 * @return true, if successful
	 */
	//controlla assieme) , poi torna true se il player le ha tutte nel mazzo di polcard
	public boolean searchCardsExistence(Colour color, int numColoredCards)
	{
		int nColoredCards = numColoredCards;
		for(PoliticCard x: politicCardsList)
		{
			if(x.getColor()==color)
			{
				nColoredCards--;
			}
		}
		if (nColoredCards <= 0)
			return true;
		return false;
	}
	
	//prende come parametro i colori delle carte che vuole scartare, scandisce l'array
	/**
	 * Discard cards in the given list.
	 *
	 * @param colorArray
	 *            the color array
	 */
	//di colori e scarta man mano le carte di quel colore dal mazzo di carte politiche del player
	public void discardCards(List<Colour> colorArray)
	{
		int dimension = colorArray.size();
		
		for(int i=0; i<dimension;i++ )
		{
			Colour colorOfCard=colorArray.get(i);
			int j=0;
			
			boolean x = false;
			while(!x)
			{
				if(politicCardsList.get(j).getColor() == colorOfCard)
				{
					politicCardsList.remove(j);
					x = true;
				}
				else j++;
			}
		}
	}
	
	/**
	 * Adds the business permit card to the player.
	 *
	 * @param bCard
	 *            the b card
	 */
	//aggiunge una carta permesso di costruzione quando il player la pesca
	public void addBusinessPermitCard(BusinessPermitCard bCard)
	{
		businessPermitCards.add(bCard);
	}
	
	//gli viene passato l'indice della carta che il giocatore ha deciso di usare
	/**
	 * Turn upside down business permit card when used.
	 *
	 * @param cardIndex
	 *            the card index
	 */
	//la carta viene tolta dall'array e messa in quello di carte capovolte
	public void turnUpsideDownBusinessPermitCard(int cardIndex)
	{
		BusinessPermitCard removedCard = businessPermitCards.remove(cardIndex);
		businessPermitCardsFaceDown.add(removedCard);
	}
	
	/**
	 * Adds the bonus tiles.
	 *
	 * @param bonusTiles
	 *            the bonus tiles
	 */
	public void addBonusTiles(List<BonusTile> bonusTiles)
	{	
		this.bonusTiles.addAll(bonusTiles);
	}
	
	
	/**
	 * Sale business card.
	 *
	 * @param position
	 *            the position
	 * @return the business permit card
	 */
	public BusinessPermitCard saleBusinessCard(int position)
	{
		return businessPermitCards.remove(position);
		
	}
	
	/**
	 * remove the card chosen and send it to the market
	 *
	 * @param color
	 *            the color
	 * @return the politic card
	 */
	//rimuove la carta dal player e la mette nel mercato
	public PoliticCard saleCard(String color){
		
		Colour colorOfCard = Colour.searchColorByName(color);
		for( int i = 0; i < this.politicCardsList.size(); i++ )
		{
			if(this.politicCardsList.get(i).getColor()==colorOfCard)
			{
				return this.politicCardsList.remove(i);
			}
		}
		return null;
	}
	
	/**
	 * To string without politic cards.
	 *
	 * @return the string
	 */
	public String toStringWithoutPoliticCards()
	{
		StringBuilder toString = new StringBuilder("");
		
		toString.append("\tPlayerID: " + (this.playerID + 1) + "\n");
	
		toString.append("\tName: " + this.name + "\n");
		
		toString.append("\tColor: " + this.getColorString() + "\n");
		
		toString.append("\tNumber of emporiums available: " + this.emporiumStack.size()+ "\n");
		
		toString.append("\tNumber of assistants: " + this.assistants.getAssistants()+ "\n");
		
		toString.append("\tVictoryMarkerDisc at position: " + this.getVictoryMarkerDisc().getPosition()+ "\n");
		
		toString.append("\tNobilityMarkerDisc at position: " + this.getNobilityMarkerDisc().getPosition()+ "\n");
		
		toString.append("\tRichnessMarkerDisc at position: " + this.getRichnessMarkerDisc().getPosition()+ "\n");
		
		toString.append("\tBusinessPermitCards available to use: ");
		if( businessPermitCards.isEmpty() )
		{
			toString.append("no business permit cards FACE UP\n");
		}
		else
		{
			for( int i = 0; i < businessPermitCards.size(); i++ )
			{
				toString.append("\n\t" + (i + 1) + ". " + businessPermitCards.get(i).toString());
				if(businessPermitCards.get(i) != businessPermitCards.get(businessPermitCards.size() - 1))
				{
					toString.append("\t\t");
				}
			}
			toString.append("\n");
		}
		
		toString.append("\tBusinessPermitCards NOT available to use: ");
		if( businessPermitCardsFaceDown.isEmpty() )
		{
			toString.append("no business permit cards FACE DOWN\n");
		}
		else
		{
			for( int i = 0; i < businessPermitCardsFaceDown.size(); i++ )
			{
				toString.append("\n\t\t" + (i + 1) + ". " + businessPermitCardsFaceDown.get(i).toString());
				if(businessPermitCardsFaceDown.get(i) != businessPermitCardsFaceDown.get(businessPermitCardsFaceDown.size() - 1))
				{
					toString.append("\t\t");
				}
			}
		}
		
		toString.append("\tSum of victory points in BonusTiles: ");
		if( bonusTiles.isEmpty() )
		{
			toString.append("no bonus tiles\n");
		}
		else
		{
			int sumOfVictoryPoints = 0;
			for(BonusTile bonusTile : bonusTiles)
			{
				sumOfVictoryPoints = sumOfVictoryPoints + bonusTile.getBonus().getQuantity();
			}
			toString.append(sumOfVictoryPoints);
		}
		toString.append("\n\n");
		
		return toString.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder toString = new StringBuilder("");
		
		toString.append("\tPlayerID: " + (this.playerID + 1) + "\n");
		
		toString.append("\tName: " + this.name + "\n");
		
		toString.append("\tColor: " + this.getColorString() + "\n");
		
		toString.append("\tNumber of emporiums available: " + this.emporiumStack.size()+ "\n");
		
		toString.append("\tNumber of assistants: " + this.assistants.getAssistants()+ "\n");
		
		toString.append("\tVictoryMarkerDisc at position: " + this.getVictoryMarkerDisc().getPosition()+ "\n");
		
		toString.append("\tNobilityMarkerDisc at position: " + this.getNobilityMarkerDisc().getPosition()+ "\n");
		
		toString.append("\tRichnessMarkerDisc at position: " + this.getRichnessMarkerDisc().getPosition()+ "\n");
		
		toString.append("\tPoliticCards in your hand: ");
		if(politicCardsList.isEmpty())
		{
			toString.append("no cards");
		}
		else
		{
			for( PoliticCard card : politicCardsList )
			{
				toString.append(card.toString() + " ");
			}
		}
		toString.append("\n");
		
		toString.append( printBusinessPermit() );
		
		toString.append("\tSum of victory points in your BonusTiles: ");
		if( bonusTiles.isEmpty() )
		{
			toString.append("no bonus tiles\n");
		}
		else
		{
			int sumOfVictoryPoints = 0;
			for(BonusTile bonusTile : bonusTiles)
			{
				sumOfVictoryPoints = sumOfVictoryPoints + bonusTile.getBonus().getQuantity();
			}
			toString.append(sumOfVictoryPoints);
		}
		toString.append("\n\n");
		
		return toString.toString();
	}
	
	/**
	 * Prints the color.
	 *
	 * @param color
	 *            the color
	 * @return the string
	 */
	private String printColor(Color color)
	{
		if(color == Color.BLACK)
			return "Black";
		if(color == Color.WHITE)
			return "White";
		if(color == Color.RED)
			return "Red";
		if(color == Color.BLUE)
			return "Blue";
		if(color == Color.GREEN)
			return "Green";
		if(color == Color.CYAN)
			return "Cyan";
		if(color == Color.YELLOW)
			return "Yellow";
		return "#NoColor";
	}
	
	/**
	 * Prints the business permit.
	 *
	 * @return the string
	 */
	private String printBusinessPermit()
	{
		StringBuilder toString = new StringBuilder("");
		
		toString.append("\tBusinessPermitCards available to use: ");
		if( businessPermitCards.isEmpty() )
		{
			toString.append("no business permit cards\n");
		}
		else
		{
			for( int i = 0; i < businessPermitCards.size(); i++ )
			{
				toString.append("\n\t" + (i + 1) + ". " + businessPermitCards.get(i).toString());
				if(businessPermitCards.get(i) != businessPermitCards.get(businessPermitCards.size() - 1))
				{
					toString.append("\t\t");
				}
			}
			toString.append("\n");
		}
		
		toString.append("\tBusinessPermitCards NOT available to use: ");
		if( businessPermitCardsFaceDown.isEmpty() )
		{
			toString.append("no business permit cards\n");
		}
		else
		{
			for( int i = 0; i < businessPermitCardsFaceDown.size(); i++ )
			{
				toString.append("\n\t\t" + (i + 1) + ". " + businessPermitCardsFaceDown.get(i).toString());
				if(businessPermitCardsFaceDown.get(i) != businessPermitCardsFaceDown.get(businessPermitCardsFaceDown.size() - 1))
				{
					toString.append("\t\t");
				}
			}
		}
		
		return toString.toString();
	}
}

			