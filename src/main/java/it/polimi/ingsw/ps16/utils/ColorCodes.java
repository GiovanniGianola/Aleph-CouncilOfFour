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
package it.polimi.ingsw.ps16.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Example Use
/*
 * Color code format WITH background color ->  :foreground,background:
 * Color code format WITHOUT background color -> :foreground,N:
 * Reset Color format -> [RC]
 *   
 *   Example Use: 
 *              String ansiColoredString = ColorCodes.parseColors("Hello, This :blue,n:is[RC] a :red,white:response[RC].");
 *              - or -
 *              String ansiColoredString = ColorCodes.RED + "Hello" + ColorCodes.WHITE + ". This is a " + ColorColorCodes.BLUE + "test";
 */

/**
 * The Class ColorCodes is used to color of the text in the standard output stream<br>
 * it uses ANSI color codes.
 */
public class ColorCodes 
{
	
	/** The Constant RESET. */
	public static final String RESET = "\u001B[0m";
	
	/** The Constant BLACK. */
	public static final String BLACK = "\u001B[30;40;1m";
	
	/** The Constant RED. */
	public static final String RED = "\u001B[31;40;1m";
	
	/** The Constant GREEN. */
	public static final String GREEN = "\u001B[32;40;1m";
	
	/** The Constant YELLOW. */
	public static final String YELLOW = "\u001B[33;40;1m";
	
	/** The Constant BLUE. */
	public static final String BLUE = "\u001B[34;40;1m";
	
	/** The Constant PURPLE. */
	public static final String PURPLE = "\u001B[35;40;1m";
	
	/** The Constant CYAN. */
	public static final String CYAN = "\u001B[36;40;1m";
	
	/** The Constant WHITE. */
	public static final String WHITE = "\u001B[37;40;1m";
	
	/**
	 * Instantiates a new color codes.
	 */
	private ColorCodes() 
	{
		/* Empty Constructor */
	}
	
	/**
	 * Parses the colors.
	 *
	 * @param input
	 *            the String that you want to color
	 * @return the colored string
	 */
	public static String parseColors(String input)
	{
		String ret = input;
		Pattern regexChecker = Pattern.compile(":\\S+,\\S+:");
		Matcher regexMatcher = regexChecker.matcher(input);
		while(regexMatcher.find())
		{
			if(regexMatcher.group().length() != 0)
			{
				String sub = regexMatcher.group().trim();
				sub = sub.replace(":", "");
				String[] colors = sub.split(",");
				
				ret = ("N").equalsIgnoreCase(colors[1]) ?
						ret.replace(regexMatcher.group().trim(), "\u001B[3" + getColorID(colors[0]) + ";1m")
				      : 
				    	ret.replace(regexMatcher.group().trim(), "\u001B[3" + getColorID(colors[0]) + ";4" + getColorID(colors[1]) + ";1m");
				ret = ret.replace("[RC]", ColorCodes.WHITE);
			}
		}
		ret = ret + ColorCodes.RESET; 
		return ret;
	}
	
	/**
	 * Gets the color ID.
	 *
	 * @param color
	 *            the color
	 * @return the color ID
	 */
	private static int getColorID(String color)
	{
		if(("BLACK").equalsIgnoreCase(color))
		{
			return 0;
		}
		else if(("RED").equalsIgnoreCase(color))
		{
			return 1;
		}
		else if(("GREEN").equalsIgnoreCase(color))
		{
			return 2;
		}
		else if(("YELLOW").equalsIgnoreCase(color))
		{
			return 3;
		}
		else if(("BLUE").equalsIgnoreCase(color))
		{
			return 4;
		}
		else if(("MAGENTA").equalsIgnoreCase(color))
		{
			return 5;
		}
		else if(("CYAN").equalsIgnoreCase(color))
		{
			return 6;
		}
		else if(("WHITE").equalsIgnoreCase(color))
		{
			return 7;
		}
		return 7;
	}
}