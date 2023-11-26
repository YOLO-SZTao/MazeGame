package FunctionA;

import java.util.ArrayList;
public class MatrixPathFinder {

    ArrayList<Tuple> path;
    public ArrayList<Tuple> findPath(int[][] mat, int currentRow, int currentCol, int endRow, int endCol) {
        if (currentRow < 2 || currentRow >= 29 || currentCol < 2 || currentCol >= 29 || mat[currentRow][currentCol] == 1) {
            // Out of bounds or blocked cell
            return null;
        }

        // Mark the current cell as visited
        mat[currentRow][currentCol] = 1;

        if (currentRow == endRow && currentCol == endCol) {

            // Reached the destination
            ArrayList<Tuple> path = new ArrayList<>();
            path.add(new Tuple(currentRow, currentCol));
            return path;
        }

        // Explore in all four directions (up, down, left, right)


        // Try moving up
        path = findPath(mat, currentRow - 1, currentCol, endRow, endCol);
        if (path != null) {
            path.add(0, new Tuple(currentRow, currentCol));
            return path;
        }

        // Try moving down
        path = findPath(mat, currentRow + 1, currentCol, endRow, endCol);
        if (path != null) {
            path.add(0, new Tuple(currentRow, currentCol));
            return path;
        }

        // Try moving left
        path = findPath(mat, currentRow, currentCol - 1, endRow, endCol);
        if (path != null) {
            path.add(0, new Tuple(currentRow, currentCol));
            return path;
        }

        // Try moving right
        path = findPath(mat, currentRow, currentCol + 1, endRow, endCol);
        if (path !=null ) {
            path.add(0, new Tuple(currentRow, currentCol));
            return path;
        }

        return null;
    }
}