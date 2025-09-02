import java.util.Stack;

public class ShortestTable {
    Node[] shortestTable;
    Stack<String>[] visitationStack;
    Stack<String>[] distanceStack;


    /**
     * constructor initializes the shortest table and fills it with generateTable(),
     * given user inputs of start and goal nodes.
     * @param graph 2D array representation of the adjacency matrix
     * @param start starting node to begin search from
     * @param goal goal node
     */
    ShortestTable(int[][] graph, char start, char goal) {
        this(graph.length);
        generateTable(graph, start, goal);
    }
    // overloaded constructor accepting char inputs
    ShortestTable(int[][] graph, int start, int goal) {
        this(graph, (char) (start + 65), (char) (goal + 65));
    }
    // constructor for testing, does not independently fill shortestTable via generateTable()
    ShortestTable(int tableLength) {
        this.shortestTable = new Node[tableLength];
        this.visitationStack = new Stack[tableLength];
        this.distanceStack = new Stack[tableLength];
        for (int i = 0; i < tableLength; i++) {
            this.shortestTable[i] = new Node(i);
            this.visitationStack[i] = new Stack<>();
            this.distanceStack[i] = new Stack<>();
        }
    }

    /**
     * generateTable method takes in a graph, start, goal, and iteratively fills in the
     * shortestTable, showing the shortest path from start to all connected nodes.
     * If a path exists between start and goal, the shortest path is displayed.
     *
     * @param graph 2D int array representation of the graph's adjacency matrix
     * @param start selected starting node to begin
     * @param goal  selected goal node
     */
    public void generateTable(int[][] graph, char start, char goal) {
        // pre-validation:
        // if start & end out of range, print error message and return
        System.out.println("Start: " + start);
        System.out.println("Goal: " + goal);
        if (start - 65 < 0 || goal - 65 < 0 || start - 65 > graph.length - 1 || goal - 65 > graph.length - 1) {
            System.out.println("Start or Goal nodes out of range, please enter valid values.");
            return;
        }

        // initialize the starting vertex for the table
        setSource(start);
        int i = 0;
        // iterate through the graph & fill the table
        while (i < graph.length) {
            // call chooseNext() method to determine next node for explore
            int next = chooseNext();
            // if next node to explore is -1, no more nodes to explore
            if (next == -1) {
                break;
            }
            // explore the next node
            updateTable(graph, next);
            // iterate i until the list is cleared
            i++;
        }
        // print shortest path from start to goal (if possible)
        printShortestPath(goal);
        // print out shortest table
        System.out.println(this);

    }

    /**
     * iterate through the graph at the specified row.
     * update the table if new cost is lower than existing cost
     *
     * @param graph adjacency matrix of the graph we wish to explore
     * @param row current row we are using to compare the cost
     */
    public void updateTable(int[][] graph, int row) {
        // iterate through a row in the graph[][]
        for (int i = 0; i < graph.length; i++) {
            // if the graph value is non-zero, ie edge exist
            if (graph[row][i] != 0) {
                // if the prev is '-', just overwrite
                if (shortestTable[i].prev.equals("-")) {
                    // update the prev
                    shortestTable[i].prev = Character.toString((char) row + 65);
                    visitationStack[i].push(Character.toString((char) row + 65));

                    // update the shortest
                    shortestTable[i].shortest = graph[row][i] + shortestTable[row].shortest;
                    distanceStack[i].push(String.valueOf(graph[row][i] + shortestTable[row].shortest));
                    continue;
                }
                // check, if new path (via this row node) is shorter than current path, we overwrite
                if (graph[row][i] + shortestTable[row].shortest < shortestTable[i].shortest) {
                    // replace the shortestTable's shortest & prev
                    shortestTable[i].prev = Character.toString((char) row + 65);
                    visitationStack[i].push(Character.toString((char) row + 65));

                    shortestTable[i].shortest = graph[row][i] + shortestTable[row].shortest;
                    distanceStack[i].push(String.valueOf(graph[row][i] + shortestTable[row].shortest));
                }
            }
        }
    }

    /**
     * method prints every node's updated values (prev, distance) throughout the calculations
     */
    public void printIteratively(){
        int len = this.visitationStack.length;
        for (int i = 0 ; i < len; i++) {
            System.out.println(i + " (" + (char)(i+65) +"): " + distanceStack[i] + visitationStack[i]);
        }
    }

    /**
     * displays the shortest possible path to the goal, assuming we have completed our shortestTable
     * @param goal the node we wish to reach
     */
    public void printShortestPath(char goal) {
        System.out.println("--------------------------------------------------");

        // check: if goal node has not been visited, no path exists to it.
        if (!shortestTable[goal - 65].visited) {
            // print error message, no path to goal, exit early.
            System.out.println("Error: No path to goal node from source exists.");
            System.out.println("--------------------------------------------------");
            return;
        }

        // string of shortest path
        String shortestPath = "";
        // store cost to be printed later
        int cost = shortestTable[goal - 65].shortest;
        // backtrack from goal to start
        while (true) {
            // check exit condition - exit once we reach start
            if (shortestTable[goal - 65].prev.equals("start")) {
                // add the start node to our solution
                shortestPath += shortestTable[goal - 65].vertex;
                break;
            }
            // append the prev of the current node to path
            shortestPath += goal;

            // update the current node to the prev
            goal = shortestTable[goal - 65].prev.charAt(0);
        }
        System.out.println("Shortest Path: " + shortestPath + ", Cost: " + cost);
        System.out.println("--------------------------------------------------");

    }

    // initializes the starting node in our shortestTable

    /**
     * initializes the starting node for our shortest table
     * @param i index of the starting node
     */
    public void setSource(int i) {
        if (i < 0 || i >= this.shortestTable.length) {
            throw new IllegalArgumentException("chooseSource() index out of bounds");
        }
        shortestTable[i].prev = "start";
        shortestTable[i].shortest = 0;
    }
    // overloaded method to achieve the same as above
    public void setSource(char ch) {
        setSource(ch - 65);
    }

    /**
     * method iterates through the shortest table and determines the next node to visit
     *
     * @return integer index of which node to visit next (0 to N-1); -1 if nothing can be visited
     */
    public int chooseNext() {
        // see which node to visit next
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        // loop through the table
        for (int i = 0; i < this.shortestTable.length; i++) {
            // if we have not visited it before, check
            if (!this.shortestTable[i].visited) {
                // if the shortest is not -1
                // find which is the minimum
                if (this.shortestTable[i].shortest != -1
                        && this.shortestTable[i].shortest < min) {
                    min = this.shortestTable[i].shortest;
                    minIndex = i;
                }
            }
        }
        // if there is no more nodes to explore, return -1
        if (minIndex == -1) {
            return -1;
        }
        // there exists next node to explore. Set it to visited, return the index
        this.shortestTable[minIndex].visited = true;
        return minIndex;
    }

    /**
     * returns string display of our shortest table
     *
     * @return string display of our shortest table
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : shortestTable) {
            sb.append(node.toString());
            sb.append("\n");
        }
        return sb.toString();

    }
}
