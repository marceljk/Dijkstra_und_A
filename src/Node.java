public class Node {

    // 0 = leer, 1 = wand, 2 = start, 3 = ende
    private int cellType = 0;
    private int hops;
    private int x;
    private int y;
    private int lastX;
    private int lastY;
    private double distanz = 0;
    private static int finishx = BoardGUI.getFinishx();
    private static int finishy = BoardGUI.getFinishy();

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
