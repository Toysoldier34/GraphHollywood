package test;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

import console.Console;
import structures.Graph;

public class driverGraph {

    public static void main(String[] args) {
        //test();  //TODO remove
        boolean killProgram = false;
        while (!killProgram) {
            Console.printMenu();
            //gets user choice then carries out the action returning here
            //TODO correct version
            //killProgram = menuChoice(Console.getInt("Enter number for desired action"));
            killProgram = menuChoice(1);
        }//end while
        System.exit(0);
    }



    /*  Example code to handle the return
           HashMap< String,Integer> hm = new HashMap< String,Integer>();
          hm.put("a", new Integer(100));
          hm.put("b", new Integer(200));
          hm.put("c", new Integer(300));
          hm.put("d", new Integer(400));

          // Returns Set view
          Set< Map.Entry< String,Integer> > st = hm.entrySet();

          for (Map.Entry< String,Integer> me:st)
          {
              System.out.print(me.getKey()+":");
              System.out.println(me.getValue());
          }
          */




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

    private static void test() {
        Graph graph = new Graph(3);
        graph.addVertex("Test", false);
        graph.addVertex("Test2", false);
        graph.addVertex("TestM", true);
        graph.addEdge("Test", "TestM");
        graph.addEdge("Test2", "TestM");
        //System.out.println(graph.findIndex("Test2"));
        //System.out.println("__print__");
        printActorNumbers(graph, "Test");
        //System.out.println("Test success?");
    }


    private static void processFile(String filename) {
        System.out.println("processFile");
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
                //System.out.println(part);
                unique.add(part);
            }
            //System.out.println("\n *New Line* \n");
        }
        System.out.println("UNIQUE SIZE:" + unique.size());



        //TODO code to process text file


        Graph graph = new Graph(3000);  //TODO replace with uni size

        //TODO code to add to graph
        for (String[] line : processed) {  //for each line
            String actor = actorFormat(line[0]);
            graph.addVertex(actor, false);  //first item of line is actor; add actor to graph
            //System.out.println("Actor: " + actor);
            //System.out.println("********* Find: Cicely Tyson " + graph.findIndex("Cicely Tyson"));

            for (int i = 1; i < line.length; i++) {  //loop remaining movies
                graph.addVertex(line[i], true);
                graph.addEdge(actor, line[i]);

            }
        }



        String name = Console.getString("Enter Actor Name");
        //String name = "Jack Palance";
        //printActorNumbers(graph, name);
        System.out.println(name+"<-Name entered");
        Map<String,Integer> actorNumbers = new HashMap<>();
                actorNumbers = graph.generateActorNumbers(name);
        for (Map.Entry<String,Integer> actor : actorNumbers.entrySet()) {  //TODO fix map iterator
            System.out.print(actor.getKey()+":");
            System.out.println(actor.getValue());
        }

    }//end processFile


    /**
     * Reverses name order removing the comma
     * @param original
     * @return corrected name format
     */
    private static String actorFormat(String original) {
        String[] s = original.split(", ");
        //System.out.println(original);  //TODO remove
        //System.out.println(s[1] + " " + s[0]);
        return (s[1] + " " + s[0]);
    }


    /** Jack Palance
     *  Alicia Silverstone
     * */

    //gets actor numbers generated and prints them out
    private static void printActorNumbers(Graph graph, String name) {
        System.out.println("printActorNumbers ::" + name + "<-------------------------------");
        // Returns Set view
        Map<String,Integer> actorNumbers = graph.generateActorNumbers(name);
        /*
        Set<Map.Entry<String,Integer>> actos = actorNumbers.entrySet();

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }


        Map<String, MyGroup> map = new HashMap<String, MyGroup>();
        for (Map.Entry<String, MyGroup> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        */

        for (Map.Entry<String,Integer> actor : actorNumbers.entrySet()) {  //TODO fix map iterator
            System.out.print(actor.getKey()+":");
            System.out.println(actor.getValue());
        }

    }//end printActorNumbers


    //takes in file name and loads all contents into an ArrayList
    private static ArrayList<String> getFileContents(String filename) {
        System.out.println("getFileContents");
        ArrayList<String> contents = new ArrayList<>();
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNext()) {
                String temp = input.nextLine();
                contents.add(temp);
                //System.out.println(temp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contents;
    }

}//end class driverGraph
