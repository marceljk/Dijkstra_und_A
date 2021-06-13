import java.util.ArrayList;

public class Node {

    private int cellType;
    private int hops;
    private final int x;
    private final int y;
    private int lastX;
    private int lastY;
    private boolean lastNodeSet = false;
    private double distanz = 0;
    private double cost;
    private double costFromStart;
    private BoardGUI gui;

    public Node(int type, int x, int y) {
        cellType = type;
        this.x = x;
        this.y = y;
        hops = -1;
    }

    /**
     * Kalkuliert die euklidische Distanz zum Endpunkt
     * @return
     */
    public double getEuclidDist() {
        int xdif = Math.abs(x - getFinishx());
        int ydif = Math.abs(y - getFinishy());
        distanz = Math.sqrt((xdif * xdif) + (ydif * ydif));
        return distanz;
    }

    /**
     * Gibt alle Nachbarknoten von dem aktuellen Knoten zurück.
     * @return
     */
    public ArrayList<Node> getSuccessor(){
        ArrayList<Node> output = new ArrayList<>();
        int counter = 0;
        int cellsize = gui.getCellSize();
        Node temp = this;
        for(int xtemp = -1; xtemp <= 1; xtemp++){
            for (int ytemp = -1; ytemp <= 1; ytemp++){
                int randX = (x+(xtemp));
                int randY = (y+(ytemp));
                if(randX >= 0 && randY >= 0 && randX < (gui.getWidthX()/cellsize) && randY < (gui.getHeightY()/cellsize)){
                    if(xtemp == 0 && ytemp == 0){           //Prüft, ob man den Ürsprungsknoten betrachtet.
                        continue;
                    }
                    temp = gui.getBoard()[x+xtemp][y+ytemp];
                    if(temp.getType() != 1){                //Prüft ob der Knoten nicht eine Wand ist.
                        output.add(gui.getBoard()[x+xtemp][y+ytemp]);
                        counter++;
                    }
                    if(!(temp.getType() == 3 || temp.getType() == 2 || temp.getType() == 1)){   //Prüft ob der Knoten nicht ein Start-, Zielfeld oder eine Wand ist.
                        temp.setType(4);
                    }
                }
            }
        }
        return output;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    // 0 = leer, 1 = wand, 2 = start, 3 = ende, 4 = untersucht, 5 = weg, 6 = wasser, 7 = wüste, 8 = busch
    public int getType() {
        return cellType;
    }

    //TODO: Anzahl der Schritte die gelaufen werden müssen
    public int getHops() {
        return hops;
    }

    public double getCost(){
        switch (getType()) {
            case 0:
                return 1;
            case 1:
                return Double.MAX_VALUE;
            case 6:
                return Double.MAX_VALUE;
            case 7:
                return 10;
            case 8:
                return 5;
            default:
                return 1;
        }
    }


    public void setGui(BoardGUI gui) {
        this.gui = gui;
    }

    public int getFinishx() {
        return gui.getFinishx();
    }

    public int getFinishy() {
        return gui.getFinishy();
    }

    // 0 = leer, 1 = wand, 2 = start, 3 = ende, 4 = untersucht, 5 = weg
    public void setType(int type) {
        cellType = type;
    }

    public void setLastNode(int x, int y) {
        lastX = x;
        lastY = y;
        lastNodeSet = true;
    }

    public boolean isLastNodeSet() {
        return lastNodeSet;
    }

    public double getCostFromStart() {
        return costFromStart;
    }

    public void setCostFromStart(double costFromStart) {
        this.costFromStart = costFromStart;
    }

    //TODO: Anzahl der Schritte die gelaufen werden müssen
    public void setHops(int hops) {
        this.hops = hops;
    }
}
