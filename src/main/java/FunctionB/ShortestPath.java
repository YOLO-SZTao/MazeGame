package FunctionB;

import java.util.*;

public class ShortestPath {
    static int ROW = 30;
    static int COL = 30;

    // To store matrix cell coordinates
    public static class Tuple {
        public int x;
        public int y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }



//        public void copy(Tuple tupleB) {
//            this.x = tupleB.x;
//            this.y = tupleB.y;
//        }
    }

    ;

    // A Data Structure for queue used in BFS
    static class queueNode {
        Tuple pt; // The coordinates of a cell
        int dist; // cell's distance of from the source
        Tuple pv; // cell's previous vertex used for tracing

        public queueNode(Tuple pt, int dist, Tuple pv) {
            this.pt = pt;
            this.dist = dist;
            this.pv = pv;
        }
    }


    // check whether given cell (row, col)
// is a valid cell or not.
    static boolean isValid(int row, int col) {
        // return true if row number and
        // column number is in range
        return (row >= 0) && (row < ROW) &&
                (col >= 0) && (col < COL);
    }

    // These arrays are used to get row and column
// numbers of 4 neighbours of a given cell
    static int rowNum[] = {-1, 0, 0, 1};
    static int colNum[] = {0, -1, 1, 0};

    // function to find the shortest path between
// a given source cell to a destination cell.
    static queueNode BFS(int mat[][], Tuple src, Tuple dest, Stack stack) {
        // check source and destination cell
        // of the matrix have value 1
        if (mat[src.x][src.y] != 0 ||
                mat[dest.x][dest.y] != 0)
            return null;

        boolean[][] visited = new boolean[ROW][COL];

        // Mark the source cell as visited
        visited[src.x][src.y] = true;

        // Create a queue for BFS
        Queue<queueNode> q = new LinkedList<>();
        // Distance of source cell is 0
        queueNode s = new queueNode(src, 0, src);
        q.add(s); // Enqueue source cell
        // Do a BFS starting from source cell
        while (!q.isEmpty()) {
            queueNode curr = q.peek();
            Tuple pt = curr.pt;
            stack.push(curr);
            // If we have reached the destination cell,
            // we are done
            if (pt.x == dest.x && pt.y == dest.y)
                return curr;

            // Otherwise dequeue the front cell
            // in the queue and enqueue
            // its adjacent cells
            q.remove();

            for (int i = 0; i < 4; i++) {
                int row = pt.x + rowNum[i];
                int col = pt.y + colNum[i];

                // if adjacent cell is valid, has path
                // and not visited yet, enqueue it.
                if (isValid(row, col) &&
                        mat[row][col] == 0 &&
                        !visited[row][col]) {
                    // mark cell as visited and enqueue it
                    visited[row][col] = true;
                    queueNode Adjcell = new queueNode(new Tuple(row, col), curr.dist + 1, new Tuple(pt.x,pt.y));
                    q.add(Adjcell);
                }
            }
        }

        // Return null if destination cannot be reached
        return null;
    }

    public static Tuple next_step(int [][] mat, Tuple source, Tuple dest){
        Stack<queueNode> stack = new Stack<>();
        queueNode destination = BFS(mat, source, dest, stack);
        queueNode buffer = new queueNode(source,0,source);
        int routex[] = new int[destination.dist];
        int routey[] = new int[destination.dist];
        for (int i = destination.dist - 1; i >= 0; i--) {
            routex[i] = destination.pt.x;
            routey[i] = destination.pt.y;
            while (!stack.isEmpty()) {
                buffer = stack.peek();
                stack.pop();
                if (buffer.pt.x == destination.pv.x && buffer.pt.y == destination.pv.y) {
                    destination = buffer;
                    break;
                }
            }
        }
        Tuple returnTuple = new Tuple(routex[0],routey[0]);
        return returnTuple;
    }
    // Driver Code
    public static Tuple [] route(int [] [] mat, Tuple source, Tuple dest) {

        Stack<queueNode> stack = new Stack<>();
        queueNode destination = BFS(mat, source, dest, stack);
        queueNode buffer = new queueNode(source,0,source);
        int routex[] = new int[destination.dist];
        int routey[] = new int[destination.dist];
        int x = destination.dist;
        for (int i = destination.dist - 1; i >= 0; i--) {
            routex[i] = destination.pt.x;
            routey[i] = destination.pt.y;
            while (!stack.isEmpty()) {
                buffer = stack.peek();
                stack.pop();
                if (buffer.pt.x == destination.pv.x && buffer.pt.y == destination.pv.y) {
                    destination = buffer;
                    break;
                }
            }
        }
        Tuple route[] = new Tuple[x+1];
        route [0] = new Tuple(source.x,source.y);
        for (int a =1; a<x+1;a++)
        {
            route[a] = new Tuple(routex[a-1],routey[a-1]);
        }
        return route;
    }
}