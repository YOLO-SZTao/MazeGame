package FunctionA;

import java.util.ArrayList;

//reference:https://weblog.jamisbuck.org/2011/1/10/maze-generation-prim-s-algorithm
//prim algorithm is selected for the MST;

//Choose an arbitrary vertex from G (the graph), and add it to some (initially empty) set V.
//Choose the edge with the smallest weight from G, that connects a vertex in V with another vertex not in V.
//Add that edge to the minimal spanning tree, and the edge’s other vertex to V.
//Repeat steps 2 and 3 until V includes every vertex in G.

//To generate the maze, since the wall is the same px-square size of a passage. wall will be the edge of the passage
//so to do the generation, odd number of col and row is needed.
//Also, a border is added to the maze
//size will be 27x27 by 30-2(two for border)-1(for optimization of the mst algorithm)

public class MST {
    public static int width = 30;
    public static int height = 30;
    public static int starting = 2;
    public static int ending = 28;
    public static final int barrier = 1;
    public static final int passage = 0;
    static Tuple startTuple = new Tuple(14, 1);
    static Tuple endTuple = new Tuple(14, 29);
    static Tuple mazestart = new Tuple(14, 2);
    static Tuple mazeend = new Tuple(14, 28);
    public static ArrayList<Tuple> visited;//passage
    public static ArrayList<Tuple> toVisit;//wall/edge
    public static int[][] maze_matrix = new int[height][width];

    static ArrayList<Tuple> pathcontainer= new ArrayList<>() ;


    public static int[][] MST1_maze() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                maze_matrix[i][j] = 1;
            }
        }
        //Choose an arbitrary vertex from G (the graph), and add it to some (initially empty) set V.
        //mid-Tuple of the col is the start
        Tuple start = mazestart;

        visited = new ArrayList<Tuple>();//passage
        toVisit = new ArrayList<Tuple>();//wall or edge

        visited.add(start);
        maze_matrix[start.x][start.y] = passage;
        add_frontier(start);


        while (toVisit.size() > 0) {
            Tuple min = toVisit.get(0);
            int index = 0;
            //loop through the frontier which is the edge of all visited vertex
            //Choose the edge with the smallest weight from G
            for (int i = 1; i < toVisit.size(); i++) //choose edge
            {
                if (min.getweight() > toVisit.get(i).getweight()) {
                    min = toVisit.get(i);
                    index = i;
                }
            }

            Tuple min_edge = toVisit.remove(index);
            //connects a vertex in V with another vertex not in V.
            for (int i = 0; i < visited.size(); i++) {
                if (visited.get(i).getX() == min_edge.getxl() && visited.get(i).getY() == min_edge.getyl())//not connect
                {
                    break;
                }
                if (i == visited.size() - 1) //connect if the vertex is not in the tree which is not passage
                {
                    //add that edge to the minimal spanning tree, and
                    maze_matrix[min_edge.x][min_edge.y] = passage;
                    maze_matrix[min_edge.xl][min_edge.yl] = passage;
                    Tuple temp = new Tuple(min_edge.getxl(), min_edge.getyl());
                    visited.add(temp); //add
                    //Add the edge’s other vertex to V.
                    add_frontier(temp);
                }
            }
        }
        maze_matrix[startTuple.x][startTuple.y] = passage;
        maze_matrix[endTuple.x][endTuple.y] = passage;
        for (int i = 0; i < width; i++) {
            System.out.println("");
            for (int j = 0; j < height; j++) {
                if (maze_matrix[i][j] > barrier) {
                    maze_matrix[i][j] = barrier;
                }
                System.out.print(maze_matrix[i][j]);
            }

        }
        CSVWriter.writeMatrixToCsv(maze_matrix, "maze_matrix.csv");
        return maze_matrix;

    }

    public static void add_frontier(Tuple T) {
        //up
        if (T.x > starting) {
            if (maze_matrix[T.x - 1][T.y] == barrier && maze_matrix[T.x - 2][T.y] == barrier) {
                int ran = 0 + (int) (Math.random() * 100);
                Tuple temp = new Tuple(T.x - 1, T.y, ran, T.x - 2, T.y);
                toVisit.add(temp);
                maze_matrix[T.x - 1][T.y] = ran;
            }
        }
        //down
        if (T.x < ending) {
            if (maze_matrix[T.x + 1][T.y] == barrier && maze_matrix[T.x + 2][T.y] == barrier) {
                int ran = 0 + (int) (Math.random() * 100);
                Tuple temp = new Tuple(T.x + 1, T.y, ran, T.x + 2, T.y);
                toVisit.add(temp);
                maze_matrix[T.x + 1][T.y] = ran;
            }
        }
        //left
        if (T.y > starting) {
            if (maze_matrix[T.x][T.y - 1] == barrier && maze_matrix[T.x][T.y - 2] == barrier) {
                int ran = 0 + (int) (Math.random() * 100);
                Tuple temp = new Tuple(T.x, T.y - 1, ran, T.x, T.y - 2);
                toVisit.add(temp);
                maze_matrix[T.x][T.y - 1] = ran;
            }
        }
        //right
        if (T.y < ending) {
            if (maze_matrix[T.x][T.y + 1] == barrier && maze_matrix[T.x][T.y + 2] == barrier) {
                int ran = 0 + (int) (Math.random() * 100);
                Tuple temp = new Tuple(T.x, T.y + 1, ran, T.x, T.y + 2);
                toVisit.add(temp);
                maze_matrix[T.x][T.y + 1] = ran;
            }
        }
    }

    public static int[][] pathcreate(Tuple start, Tuple end, int[][] m) {
        // Create a straight-line path between the two Tuples
        int deltaX = end.x - start.x;
        int deltaY = end.y - start.y;

        // Determine the direction of movement
        int directionX = (int) Math.signum(deltaX);
        int directionY = (int) Math.signum(deltaY);

        // Move along the path and mark the cells
        int i = start.x;
        int j = start.y;

        while ((i != end.x || j != end.y) && i >= 2 && i < 29 && j >= 2 && j < 29) {
            Tuple temp =new Tuple(i,j);
            if(!pathcontainer.contains(temp))
            {
                pathcontainer.add(temp);
            }
            m[i][j] = 0; // Mark the cell as part of the path
            if (Math.abs(end.x - i) > Math.abs(end.y - j)) {
                i += directionX;
            } else {
                j += directionY;
            }
        }

        // Mark the final cell if within bounds
        if (i >= 0 && i < m.length && j >= 0 && j < m[0].length) {
            m[end.x][end.y] = 0;
        }
        return m;
    }

    public static int[][] MST2_maze(int[][] mat) {
        MatrixPathFinder pathFinder = new MatrixPathFinder();
        int[][] dummy =new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                dummy[i][j] = mat[i][j];
            }
        }
        pathcontainer = pathFinder.findPath(dummy, mazestart.x, mazestart.y, mazeend.x, mazeend.y);
        int ran1 = 0 + (int) (Math.random() * pathcontainer.size());
        int ran2 = ran1 % 2 + 1;
        Tuple V1 = pathcontainer.get(ran1);
        Tuple V3 = pathcontainer.get(ran2);
        Tuple V2= new Tuple(0,0);
        while(true)
        {
            int ran3 = 2 + (int) (Math.random() * 28);
            int ran4= 2 + (int) (Math.random() * 28);
            V3.ChangeData(ran3,ran4);
            if(!pathcontainer.contains(V2))
            {
                break;
            }
        }
        System.out.println();
        mat = pathcreate(V1, V3, mat);
        mat = pathcreate(V2, V3, mat);
        CSVWriter.writeMatrixToCsv(mat, "matrix1.csv");
        return mat;
    }




}