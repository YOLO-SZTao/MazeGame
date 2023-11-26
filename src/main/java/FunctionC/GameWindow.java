package FunctionC;

import FunctionA.DataOfSquare;
import FunctionA.SquarePanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;


class GameWindow extends JFrame{
    private static final long serialVersionUID = -2542001418764869760L;

    public static ArrayList<ArrayList<DataOfSquare>> Grid; //here
    public static int width = 30;
    public static int height = 30;
    public  int[][] maze;



    public GameWindow(Game game){


        // Creates the arraylist that'll contain the threads
        Grid = new ArrayList<ArrayList<DataOfSquare>>();
        ArrayList<DataOfSquare> col;

        //maze generation by MST algorithm

        int[][] maze = game.getMaze().getMaze();

        // Creates Threads and its data and adds it to the arrayList
        for(int i=0;i<width;i++){
            col= new ArrayList<DataOfSquare>();
            for(int j=0;j<height;j++){
                DataOfSquare c = new DataOfSquare(maze[i][j]);
                col.add(c);
            }
            Grid.add(col);
        }


        // Setting up the layout of the panel
        getContentPane().setLayout(new GridLayout(30,30,0,0));
        Grid.get(game.getJerryPosition().x).get(game.getJerryPosition().y).lightMeUp(2);
        Grid.get(game.getTomPosition().x).get(game.getTomPosition().y).lightMeUp(3);


        // Start & pauses all threads, then adds every square of each thread to the panel
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                getContentPane().add(Grid.get(i).get(j).square);
            }
        }

    }

    public void updateContentPane() {
        getContentPane().removeAll();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getContentPane().add(Grid.get(i).get(j).square);
            }
        }

        revalidate();
        repaint();
    }

}