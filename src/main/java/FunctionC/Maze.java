package FunctionC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import FunctionA.MST;
import FunctionB.ShortestPath;

public class Maze {
    // class attributes
    private int[][] maze;
    private ShortestPath.Tuple entryPoint = new ShortestPath.Tuple(14, 0);
    private ShortestPath.Tuple exitPoint = new ShortestPath.Tuple(14, 29);

    public Maze() {
        initialize();
    }

    public int[][] getMaze(){
        return maze;
    }
    public ShortestPath.Tuple getEntryPoint() {
        return entryPoint;
    }

    public ShortestPath.Tuple getExitPoint(){
        return exitPoint;
    }

    private void initialize() {
        maze = new int[30][30];
        maze = MST.MST1_maze();
        maze = MST.MST2_maze(maze);
        maze = MST.MST2_maze(maze);
        maze = MST.MST2_maze(maze);



    }
}
