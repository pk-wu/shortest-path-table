
public class Node {
    char vertex;
    int shortest;
    String prev;
    boolean visited;

    public Node(int vertex) {
        this.vertex = (char) (65 + vertex);
        this.shortest = -1;
        this.prev = "-";
        this.visited = false;
    }

    // helper method for testing only
    public void setValues(int shortest, String prev) {
        this.shortest = shortest;
        this.prev = prev;
    }
    public void setValues(int shortest, String prev, boolean tf) {
        setValues(shortest, prev);
        this.visited = tf;
    }

    @Override
    public String toString() {
        return "vertex=" + vertex +
                ", shortest=" + shortest +
                ", prev= " + prev +
                ", visited= " + visited;
    }
}
