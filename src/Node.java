import java.util.ArrayList;

public class Node {

    // 0 = leer, 1 = wand, 2 = start, 3 = ende, 4 = untersucht
    private int cellType;
    private int hops;
    private int x;
    private int y;
    private int lastX;
    private int lastY;
    private double distanz = 0;
    private double cost = 0;
    private static int finishx;
    private static int finishy;

    public Node(int type, int x, int y) {    //CONSTRUCTOR
        cellType = type;
        this.x = x;
        this.y = y;
        hops = -1;
    }
    
    public double getEuclidDist() {        //CALCULATES THE EUCLIDIAN DISTANCE TO THE FINISH NODE
        int xdif = Math.abs(x - finishx);
        int ydif = Math.abs(y - finishy);
        distanz = Math.sqrt((xdif * xdif) + (ydif * ydif));
        return distanz;
    }

    /**
     * Gibt alle Nachbarknoten von dem aktuellen Knoten zurück.
     * @return
     */
    public ArrayList<Node> getSuccessor(){
        ArrayList<Node> output = new ArrayList<Node>();
        int counter = 0;
        int cellsize = BoardGUI.getCellSize();
        for(int xtemp = -1; xtemp <= 1; xtemp++){
            for (int ytemp = -1; ytemp <= 1; ytemp++){
                int randL = (x+(xtemp));
                int randO = (y+(ytemp));
                int randR = (x+(xtemp));
                int randU = (y+(ytemp));
                if(randL >= 0 && randO >= 0 && randR <= BoardGUI.getWidthX() && randU <= BoardGUI.getHeightY()){
                    if(x == 0 && y == 0){
                        continue;
                    }
                    Node temp = BoardGUI.getBoard()[x+xtemp][y+ytemp];
                    if(temp.getType() != 1){
                        output.add(BoardGUI.getBoard()[x+xtemp][y+ytemp]);
                        counter++;
                    }
                    if(!(temp.getType() == 3 || temp.getType() == 2)){
                        temp.setType(4);
                    }
                }
            }
        }
        return output;
    }

    public int getX() {
        return x;
    }        //GET METHODS

    public int getY() {
        return y;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public int getType() {
        return cellType;
    }

    public int getHops() {
        return hops;
    }

    public double getCost(){
        cost = getEuclidDist();
        return cost;
    }

    public void setCost(double cost){
        this.cost = cost;
    }

    public static int getFinishx() {
        finishx = BoardGUI.getFinishx();
        return finishx;
    }

    public static int getFinishy() {
        finishy = BoardGUI.getFinishy();
        return finishy;
    }

    public void setType(int type) {
        cellType = type;
    }        //SET METHODS

    public void setLastNode(int x, int y) {
        lastX = x;
        lastY = y;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }
}
