package FunctionA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;


class Window extends JFrame{
    private static final long serialVersionUID = -2542001418764869760L;

    public static ArrayList<ArrayList<DataOfSquare>> Grid; //here
    public static int width = 30;
    public static int height = 30;
    public  int[][] matrix;

    public Window(){


        // Creates the arraylist that'll contain the threads
        Grid = new ArrayList<ArrayList<DataOfSquare>>();
        ArrayList<DataOfSquare> data;

        //maze generation by MST algorithm

        matrix=MST.MST1_maze();

        matrix=MST.MST2_maze(matrix);

        // Creates Threads and its data and adds it to the arrayList
        for(int i=0;i<width;i++){
            data= new ArrayList<DataOfSquare>();
            for(int j=0;j<height;j++){
                DataOfSquare c = new DataOfSquare(matrix[i][j]);
                data.add(c);
            }
            Grid.add(data);
        }


        // Setting up the layout of the panel
        getContentPane().setLayout(new GridLayout(30,30,0,0));

        // Start & pauses all threads, then adds every square of each thread to the panel
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                getContentPane().add(Grid.get(i).get(j).square);
            }
        }
        // Links the window to the keyboardlistenner.
        this.addKeyListener((KeyListener) new KeyboardListener());



    }

}