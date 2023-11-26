package FunctionA;

public class Tuple {
    public  int x;
    public  int y;
    public int xl;
    public int yl;
    public int weight;


    //passage Tuple or wall that is not an edge
    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }


    //wall Tuple which is an edge
    public Tuple(int x, int y, int w, int xl,int yl) {
        //Tuple(posx,posy,weight for mst algo, posx of destination vertex,posy of destination vertex
        this.x = x;
        this.y = y;
        this.weight=w;
        this.xl=xl;
        this.yl=yl;
    }

    public void ChangeData(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getweight(){
        return weight;
    }

    public int getxl()
    {
        return xl;
    }

    public int getyl()
    {
        return yl;
    }
}