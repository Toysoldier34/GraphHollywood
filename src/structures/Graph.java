package structures;

import java.util.*;

    /*
Each line of a file has the following format. Note that tv shows are also included in the data set.

Actor/actress name | movie/tv show #1 | movie/tv show #2 | ...

Here is a few lines from one of the files:

Astaire, Fred | Puttin' on His Top Hat | "The Dick Cavett Show" | ...
Baio, Scott | Battle of the Network Stars XIV | Circus of the Stars #5 | ...
Bale, Christian | Larger Than Life Adversaries | "The Drunken Peasants" | ...
Brosnan, Pierce | The Tailor of Panama | 25th Film Independent Spirit Awards | ...



Building a Graph

Your first programming task is to build a graph structure that can be used to generate "actor numbers." An actor number is like a "Bacon number" except that you can choose any actor as the source vertex. Your graph should have the follow properties:

Edges are undirected.
Both actors and movies are represented in the graph by vertices. i.e. An edge cannot contain an actor or a movie.
You can use an adjacency list or adjacency matrix, whichever your prefer.




Create a new routine that accepts an actor name and calculates the "actor number" for all other actors in the graph above. For example:

To calculate "actor numbers",  you should use breadth-first search on your graph (BFS) to identify actors/movies. You should not be using depth-first-search. Take a moment and consider why this is the case. There are several complications when writing BFS:

You need to keep a counter for each set of neighbors traversed from your source vertex. For example:
All immediate neighbors should have an "actor number" of 1
All neighbors of immediate neighbors should have an "actor number" of 2
You must skip all vertices that store movies as part of your calculation
         */


public class Graph {

    //field
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[][] adjMatrix;  //adjacency matrix holding only relations
    private Node[] vData;  //data of each vertex  //TODO change to something resizable to accommodate for more movies
    private int V;  //number of vertices (elements)
    private int E;  //number of edges (relations)


    /***************  CONSTRUCTOR  ***************/

    /**
     * Sets size of graph and initializes to that size
     * @param V number of items that will be added
     */
    public Graph(int V) {
        System.out.println("const" + V);
        this.V = 0;
        adjMatrix = new boolean[V][V];
        vData = new Node[V];
    }//end constructor

    /***************  PRIMARY METHODS  ***************/

    /**
     * Print out all connections to a given actor
     * @param actorSource String name of actor
     */
    public Map< String,Integer> generateActorNumbers(String actorSource) {
        //find Actor to get index and verify it exists
        int actor = findIndex(actorSource);
        Map<String,Integer> map = new TreeMap<String, Integer>();  //data to return

        if (actor >= 0) {  //breadth search from person recording steps
            Queue<Integer> q = new LinkedList<>();  //main queue to work through
            boolean[] marked = new boolean[V];  //tracks already processed data
            int[] distTo = new int[V];  //tracks distance to objects from source
            int[] edgeTo = new int[V];  //tracks edge to the source

            //defaults all distances to max
            for (int i = 0; i < V; i++) distTo[i] = INFINITY;

            //setting actor info to start
            distTo[actor] = 0;
            marked[actor] = true;
            q.add(actor);

            //while new connections remain
            while (!q.isEmpty()) {
                int current = q.remove();
                ArrayList<Integer> edges = findEdges(current);
                for (int edge : edges) {
                    if (!marked[edge]) {  //ensures new vertices only
                        edgeTo[edge] = current;
                        distTo[edge] = distTo[current] + 1;
                        marked[edge] = true;
                        q.add(edge);
                    }
                }
            }

            System.out.println("Before map build ordering");

            /*
            TODO Everything works correctly so far aside from printing order
            Everything is added in correct order, HashMap doesn't retain insertion
            order. TreeMap suggested as alternative, but didn't work on first test
             */

            

            //loops through, finding all actors of incrementing distance
            //builds return map in priority order with closest actors first
            for (int n = 0; n < V; n+=2) {  //n = distance
                for (int m = 0; m < V; m++) {  //m = vertex index
                    //divide distance by 2 to remove movies
                    if (distTo[m] == n) {
                        System.out.println("map.put" + vData[m].name +" " + n/2);
                        map.put(vData[m].name , n/2);
                    }
                }
            }

            System.out.println("***** Before ForEach Map *****");
            for (Map.Entry<String,Integer> xx : map.entrySet()) {  //TODO fix map iterator
                System.out.print(xx.getKey()+":");
                System.out.println(xx.getValue());
            }
            System.out.println("***** After ForEach Map *****");
        } else {
            System.out.println("Actor not found.");
        }
        return map;
    }// end generateActorNumbers


    /**
     * Add vertex to graph but cannot exceed initially passed count and
     * all vertices need to be passed at start.
     * Stores actors and movies
     * @param name Name of actor or movie, only unique names
     * @param type boolean 0:false for actor; 1:true for movie
     */
    public void addVertex(String name, boolean type) {
        int i = findIndex(name);
        if (i == -1) {
            vData[V++] = new Node(name, type);
        } else {
            //System.out.println("&&&& ADD " + name+ ":"+i+"  Already added");
        }
    }//end addVertex


    /**
     * Add edge between Actor and Movie by name
     * @param actor
     * @param movie
     */
    public void addEdge(String actor, String movie) {
        int a = findIndex(actor);
        int b = findIndex(movie);

        if (a != -1 && b != -1) {  //ensures both items exist
            addEdge(a, b);
        } else {
            if (a == -1) System.out.println("Actor not found");
            if (b == -1) System.out.println("Movie not found");
        }
    }//end addEdge

    /**
     * Add an edge between two vertices by passing the index for each
     * @param a vertex index
     * @param b vertex index
     */
    private void addEdge(int a, int b) {
        if (adjMatrix[a][b] && adjMatrix[b][a]) return;  //checks if edge exists
        //check if vertices exist
        if (vData[a] != null && vData[b] != null) {
            adjMatrix[a][b] = true;
            adjMatrix[b][a] = true;
            E++;
        }
    }//end addEdge

    /***************  SECONDARY METHODS  ***************/
    /**
     * Finds index of given item. returns -1 if not found
     * @param name Actor or movie to search for
     * @return index of item, returns -1 if not found
     */
    public int findIndex(String name) {
        for (int i = 0; i < V; i++) {
            if (vData[i].name.equals(name)) return i;
        }
        return -1;
    }

    //returns an ArrayList of edges for given item
    private ArrayList findEdges(int x) {
        ArrayList<Integer> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (adjMatrix[x][i]) edges.add(i);
        }
        return edges;
    }


    /***************  VIEWING INTERNAL DATA  ***************/


    /***************  COUNTS  ***************/
    //getters


    /**
     * Get number of vertices
     * @return int number of vertices
     */
    public int vertexSize() {
        return V;
    }


    /**
     * Number of Edges
     * @return int Edges
     */
    public int edgeSize() {
        return E;
    }

    /***************  INNER CLASS  ***************/
    /**
     * Node to store vertex name and type
     */
    private class Node {  //TODO change
        public String name;
        public boolean type; //0:false for actor; 1:true for movie

        public Node(String name, boolean type) {
            this.name = name;
            this.type = type;
        }
    }


}//end class Graph
