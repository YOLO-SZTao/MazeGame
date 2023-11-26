package FunctionA;

import java.awt.*;
import java.util.ArrayList;

public class DataOfSquare {


    //ArrayList that'll contain the colors
    ArrayList<Color> C =new ArrayList<Color>();
    int color; //0: path , 1:barrier
    public SquarePanel square;
    public DataOfSquare(int col){

        //Lets add the color to the arrayList
        C.add(Color.white);//0
        C.add(Color.darkGray);   //1
        C.add(Color.red); // 2 jerry
        C.add(Color.blue); // 3 tom
        color=col;
        square = new SquarePanel(C.get(color));
    }
    public void lightMeUp(int c){
        square.ChangeColor(C.get(c));
        color=c;
    }
}