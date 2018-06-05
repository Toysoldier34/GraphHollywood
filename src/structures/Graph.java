/**
 * Tony Thompson
 * May 27, 2018
 * Graph.java
 *
 */
package structures;

import java.util.*;



/**
 * Graph undirected structure to hold actors and movies with the ties between them
 */
public class Graph {

    //field
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[][] adjMatrix;  //adjacency matrix holding only relations
    private Node[] vData;  //data of each vertex
    private int V;  //number of vertices (elements)
    private int E;  //number of edges (relations)


    /***************  CONSTRUCTOR  ***************/

    /**
     * Sets size of graph and initializes to that size
     * @param V number of items that will be added
     */
    public Graph(int V) {
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
        Map<String,Integer> map = new LinkedHashMap<String, Integer>();  //data to return

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

            //loops through, finding all actors of incrementing distance
            //builds return map in priority order with closest actors first
            for (int n = 0; n < V; n+=2) {  //n = distance
                for (int m = 0; m < V; m++) {  //m = vertex index
                    //divide distance by 2 to remove movies
                    if (distTo[m] == n) {
                        map.put(vData[m].name , n/2);
                    }
                }
            }
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
