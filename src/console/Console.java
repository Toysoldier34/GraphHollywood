/**
 * Tony Thompson
 * May 27, 2018
 * Console.java
 *
 */
package console;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * handles console IO
 * prints menu and prompts
 * gets user inputs
 * @author Tony Thompson
 *
 */

/*
Write a console program that allows a user to do the following:

Load an input file of their choice
Builds a graph based on the input file
Enter a source actor/actress name
Lists interesting details for all actor numbers based on the source actor
 */
public class Console {

    //field
    private static Scanner console = new Scanner(System.in);
    private static final int NUM_MENU = 3;


    /**
     * prints out text for menu
     */
    public static void printMenu() {
        System.out.println();
        System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
        System.out.println("Welcome to the Six-Degrees of Separation Game!\n");
        System.out.println("Please choose one of the following files:");
        System.out.println("0. End Program");
        System.out.println("1. 20_actors_100_movies.txt");
        System.out.println("2. 300_actors_50_movies.txt");
        System.out.println("3. 900_actors_100_movies.txt");
        System.out.println();
    }//end printMenu


    /**
     * prompts user for an int between 0-NUM_MENU to return
     * @param prompt String to display to user
     * @return int entered by user
     */
    public static int getInt(String prompt) {
        System.out.println(prompt + ": ");
        boolean valid = false;
        int result = 0;
        while (!valid) {
            while (!console.hasNextInt()) {  //waits for user input
                System.out.println("Please enter a valid integer between 0 and "+NUM_MENU+": ");
                //clear the scanner buffer
                console.nextLine();
            }//end while
            result = console.nextInt();  //store user input
            if ((result >= 0) && (result <= NUM_MENU)) {  //validates user input
                valid = true;  //breaks while after good input
            } else {
                System.out.println("Please enter a valid integer between 0 and "+NUM_MENU+": ");
            }//end if
        }//end while
        //clear the scanner buffer
        console.nextLine();
        return result;
    }//end getInt


    /**
     * prompts user for a String
     * @param prompt String to display to user
     * @return String entered by user
     */
    public static String getString(String prompt) {
        System.out.println(prompt + ": ");
        return console.nextLine();
    }//end getString


}//end class
