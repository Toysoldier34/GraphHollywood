package test;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

import console.Console;
import structures.Graph;

/**
 * Gets input to build a graph of actor movie relations and returns the info
 */
public class driverGraph {

    /**
     * Main method to start program to generate actor relation numbers
     * @param args
     */
    public static void main(String[] args) {
        boolean killProgram = false;
        while (!killProgram) {
            Console.printMenu();
            //gets user choice then carries out the action returning here
            killProgram = menuChoice(Console.getInt("Enter number for desired action"));
        }//end while
        System.exit(0);
    }



    //triggers action based on menu choice
    private static boolean menuChoice(int choice) {
        switch (choice) {  //cases match corresponding menu items
            case 0:  //breaks main while loop ending program
                return true;
            case 1:  processFile("20_actors_15_movies.txt");
                return false;
            case 2:  processFile("300_actors_50_movies.txt");
                return false;
            case 3:  processFile("900_actors_100_movies.txt");
                return false;
            default: System.out.println("Menu Switch Error");
                return false;
        }//end switch
    }//end menuChoice


    /**
     * takes in name of file and loads it
     * each line is processed into actors and movies which
     * are then entered into graph. Connections to a given
     * actor are returned to the user
     */
    private static void processFile(String filename) {
        ArrayList<String> contents = getFileContents(filename);  //returned file contents
        HashSet<String> unique = new HashSet<>();  //tracks unique vertices
        ArrayList<String[]> processed = new ArrayList<>();  //stores each split line

        //process file contents
        for (String line : contents) {
            //split line into actor and movies
            String[] parts = line.split(Pattern.quote(" | "));
            processed.add(parts);  //add whole line for processing
            //add parts to HashSet to get total count of unique items
            for (String part : parts) {
                unique.add(part);
            }
        }

        //Initialize graph to number of unique entries
        Graph graph = new Graph(unique.size());

        //code to add to graph
        for (String[] line : processed) {  //for each line
            String actor = actorFormat(line[0]);
            graph.addVertex(actor, false);  //first item of line is actor; add actor to graph

            for (int i = 1; i < line.length; i++) {  //loop remaining movies
                graph.addVertex(line[i], true);
                graph.addEdge(actor, line[i]);
            }
        }

        //prompt user for actor name to print results for
        String name = Console.getString("Enter Actor Name");
        printActorNumbers(graph, name);

    }//end processFile


    /**
     * Reverses name order removing the comma
     * @param original
     * @return corrected name format
     */
    private static String actorFormat(String original) {
        String[] s = original.split(", ");
        return (s[1] + " " + s[0]);
    }


    //gets actor numbers generated and prints them out
    private static void printActorNumbers(Graph graph, String name) {
        //generate the actor distance numbers for given actor
        Map<String,Integer> actorNumbers = graph.generateActorNumbers(name);

        //print results
        for (Map.Entry<String,Integer> actor : actorNumbers.entrySet()) {  //TODO fix map iterator
            System.out.print(actor.getKey()+":");
            System.out.println(actor.getValue());
        }
    }//end printActorNumbers


    //takes in file name and loads all contents into an ArrayList
    private static ArrayList<String> getFileContents(String filename) {
        ArrayList<String> contents = new ArrayList<>();
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNext()) {
                String temp = input.nextLine();
                contents.add(temp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contents;
    }

}//end class driverGraph
