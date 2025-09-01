import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        // initialize the graph as an adjacency matrix
        // either directly as int[][] or with string
        int[][] graph = {
                {0, 1, 3, 0, 0, 10, 0},
                {1, 0, 1, 7, 5, 0, 2},
                {3, 1, 0, 9, 3, 0, 0},
                {0, 7, 9, 0, 2, 0, 0},
                {0, 5, 3, 2, 0, 2, 0},
                {10, 0, 0, 1, 2, 0, 0},
                {0, 2, 0, 12, 0, 0, 0}
        };

        // OR

        // initialize the graph as an adjacency matrix
        // using string representation, separated by spaces ( ) and commas (,)
        String s =
                "0 1 3 0 0 10 0," +
                "1 0 1 7 5 0 2," +
                "3 1 0 9 3 0 0," +
                "0 7 9 0 2 0 0," +
                "0 5 3 2 0 2 0," +
                "10 0 0 1 2 0 0," +
                "0 2 0 12 0 0 0";
        // call stringToGraph() method to parse it into 2D int[][]
        int[][] newGraph = stringToGraph(s);

//        int[][] graph = {
//                {0, 100, 0, 160, 0},
//                {0, 0, 120, 180, 0},
//                {0, 0, 0, 0, 80},
//                {0, 0, 40, 0, 140},
//                {0, 100, 0, 0, 0}
//        };
//        int[][] graph = {
//                {0,6,12,3,0,0,0},
//                {6,0,0,1,7,0,0},
//                {12,0,0,1,0,10,0},
//                {3,1,1,0,0,5,4},
//                {0,7,0,0,0,0,9},
//                {0,0,10,5,0,0,0},
//                {0,0,0,4,9,0,0},
//        };
//        int[][] graph = {
//                {0,4,5,8,0,0,0,0,0,0},
//                {4,0,0,3,12,0,0,0,0,0},
//                {5,0,0,1,0,11,0,0,0,0},
//                {8,3,1,0,9,4,10,0,0,0},
//                {0,12,0,9,0,0,6,10,0,0},
//                {0,0,11,4,0,0,5,0,11,0},
//                {0,0,0,10,6,5,0,3,5,15},
//                {0,0,0,0,10,0,3,0,0,14},
//                {0,0,0,0,0,11,5,0,0,8},
//                {0,0,0,0,0,0,15,14,8,0}
//        };

        // INSTRUCTIONS ON HOW TO RUN:
        // Input the adjacency matrix, graph (2D int[][])
        // + start node (char)
        // + end node (char)
        ShortestTable shortestTable = new ShortestTable(graph,'A','F');
    }


    /**
     * helper method to parse a string representation of a graph into
     * a 2D int[][] array (which can be used to generating the path)
     * @param
     * @return
     */
    public static int[][] stringToGraph(String s) {
        // split String[][] input by comma, into rows
        String[] rows = s.split(",");
        int[][] graph = new int[rows.length][rows.length];
        for (int i = 0; i < rows.length; i++) {
            // split each row into another String[] array
            String[] currentRow = rows[i].split(" ");
            for (int j = 0; j < rows.length; j++) {
                graph[i][j] = Integer.parseInt(currentRow[j]);
            }
        }
        return graph;
    }
}
