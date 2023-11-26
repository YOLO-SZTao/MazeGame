package FunctionC;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

//import FunctionA.ThreadsController;
import FunctionB.ShortestPath;

import javax.swing.*;

public class Game {
    private Maze maze;
    private ShortestPath.Tuple TomPosition;
    private ShortestPath.Tuple JerryPosition;
    private int difficulty;
    private int counter = 0;
    private int result = 0;
    private int turn = 0; // 0 is Jerry's turn, 1 is Tom's turn
    private char move = 'n';
    private GameWindow gameWindow;

    public ShortestPath.Tuple getJerryPosition() {
        return JerryPosition;
    }

    public ShortestPath.Tuple getTomPosition() {
        return TomPosition;
    }

    public Maze getMaze(){
        return maze;
    }
    public Game() {
        // initialize maze of game
        maze = new Maze();


        // initialize game difficulty
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the difficulty level (3-10): ");
            difficulty = scanner.nextInt();
            if (difficulty >= 3 && difficulty <= 10 ){
                break;
            }
            System.out.println("Invalid difficulty level. Please enter an integer between 3 and 10.");
        }

        // initialize Tom and Jerry position
        TomPosition = new ShortestPath.Tuple(14,29);
        JerryPosition = new ShortestPath.Tuple(14,0);
    }

    public void TomTurn(){
        // should call find the shortest path function and move Tom one step toward Jerry along the shortest path
        GameWindow.Grid.get(getTomPosition().x).get(getTomPosition().y).lightMeUp(0);
        ShortestPath.Tuple newPosition = ShortestPath.next_step(maze.getMaze(), TomPosition, JerryPosition);
        TomPosition.x = newPosition.x;
        TomPosition.y = newPosition.y;
        GameWindow.Grid.get(getTomPosition().x).get(getTomPosition().y).lightMeUp(3);
        System.out.print("Tom moved.");
    }

    private ShortestPath.Tuple getNewPosition(char move){
        ShortestPath.Tuple newPosition = new ShortestPath.Tuple(JerryPosition.x, JerryPosition.y);

        // need to modify to listen the keyboard directly
        switch (move) {
            case 'a': // Move left
//                System.out.print(newPosition.x);
//                System.out.print(newPosition.y);
                newPosition.y = newPosition.y-1;
//                System.out.print(newPosition.x);
//                System.out.print(newPosition.y);

                break;
            case 's': // Move down
//                System.out.print(newPosition.x);
//                System.out.print(newPosition.y);
                newPosition.x = newPosition.x+1;
//                System.out.print(newPosition.x);
//                System.out.print(newPosition.y);
                break;
            case 'd': // Move right
//                System.out.print(newPosition.x);
//                System.out.print(newPosition.y);
                newPosition.y = newPosition.y+1;
//                System.out.print(newPosition.x);
//                System.out.print(newPosition.y);
                break;
            case 'w': // Move up
//                System.out.print(newPosition.x);
//                System.out.print(newPosition.y);
                newPosition.x = newPosition.x-1;
//                System.out.print(newPosition.x);
//                System.out.print(newPosition.y);
                break;
        }
        return newPosition;
    }
    private boolean checkNewPosition(ShortestPath.Tuple tuple){
        // in boundary
        if (tuple.x<0 || tuple.x>=30) {
            return false;
        }
        if (tuple.y<0 || tuple.y>=30) {
            return false;
        }
        // is empty
        if (maze.getMaze()[tuple.x][tuple.y] == 1){
            return false;
        }
        return true;
    }
    public void JerryTurn(){
        Scanner scanner = new Scanner(System.in);
        // let user input a direction to move
        boolean getValidInput = false;
        while (!getValidInput) {
            System.out.print("Enter a move for Jerry (a/s/d/w): ");
            char move = scanner.next().charAt(0);
            // check the validity of the move
            if (!(move == 'a' || move == 's' || move == 'd' || move == 'w')){
                System.out.print("Enter a move for Jerry again(a/s/d/w): ");
                continue;
            }
            // may need modify
            ShortestPath.Tuple newPosition = getNewPosition(move);
            getValidInput = checkNewPosition(newPosition);
            if (getValidInput){
                // update JerryPosition
                GameWindow.Grid.get(getJerryPosition().x).get(getJerryPosition().y).lightMeUp(0);
                JerryPosition.x = newPosition.x;
                JerryPosition.y = newPosition.y;
                GameWindow.Grid.get(getJerryPosition().x).get(getJerryPosition().y).lightMeUp(2);
                System.out.print("Jerry moved.");
            } else {
                System.out.print("Enter a move for Jerry again(a/s/d/w): ");
            }

        }
    }

//    public class DirectionListener extends KeyAdapter {
//
//        public void keyPressed(KeyEvent e){
//            switch(e.getKeyCode()){
//                case 39:	// -> Right
//                    //if it's not the opposite direction
//                    move = 'd';
//                    break;
//                case 38:	// -> Top
//                    move = 'w';
//                    break;
//
//                case 37: 	// -> Left
//                    move = 'a';
//                    break;
//
//                case 40:	// -> Bottom
//                    move = 's';
//                    break;
//
//                default:  // invalid
//                    move = 'n';
//                    break;
//            }
//        }
//
//    }

    public int checkResult() {
        // 0: game not finish; 1: Jerry win; 2: Tom win
        if (JerryPosition.x == TomPosition.x && JerryPosition.y == TomPosition.y){
            System.out.print("Tom win.");
            return 2;
        } else if (JerryPosition.x == maze.getExitPoint().x && JerryPosition.y == maze.getExitPoint().y){
            System.out.print("Jerry win.");
            return 1;
        } else {
            System.out.print("Match continue.");
            return 0;
        }
    }

    public void playGame() {
        gameWindow = new GameWindow(this);
        gameWindow.setTitle("MazeGame");
        gameWindow.setSize(300,300);
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (result == 0){
            if (turn ==0){
                JerryTurn();
                turn = 1;
            } else {
                TomTurn();
                if ((counter)%(difficulty+1) != (difficulty-1)){
                    turn = 0;
                }
                counter++;
            }
            result = checkResult();
        }
    }
}
