
import java.util.ArrayList;
import java.util.HashMap;

public class A_Stern implements Runnable {

    private PanelControl pc;

    private HashMap<Node, Double> openlist;
    private ArrayList<Node> closedList;
    private boolean search;
    private Node end;
    private BoardGUI gui;
    private int gepruefte;


    @Override
    public void run() {
        searchPath();
    }

    public void setGui(BoardGUI gui) {
        this.gui = gui;
    }

    public void setPanelControl(PanelControl panelControl) {
        this.pc = panelControl;
    }

    public void searchPath() {
        gepruefte = 0;
        gui.clearSearched();
        openlist = new HashMap<>();
        closedList = new ArrayList<>();
        openlist.put(gui.getBoard()[gui.getStartx()][gui.getStarty()], 0.0);

        search = true;
        Node currentNode = null;
        end = gui.getFinalNode();
        do {

            if (openlist.size() <= 0) {               //Wenn Kein Startpunkt vorhanden, dann Suche beenden
                search = false;
                System.out.println("Kein Weg gefunden!");
                break;
            }

            try {
                Thread.sleep(100);               //Thread wartet auf vorherige Operationen, um Fehler zu vermeiden
            } catch (Exception e) {

            }

            double min = Double.MAX_VALUE;
            for (Node key : openlist.keySet()) {     //Suche Knoten mit niedrigsten Kosten
                if (min > openlist.get(key)) {
                    min = openlist.get(key);
                    currentNode = key;
                }
            }

            openlist.remove(currentNode);

            if (currentNode == end) {                 //Prüft ob currentNode der Zielknoten ist
                System.out.println("Weg gefunden!");
                showPath();
                search = false;
                break;
            }

            closedList.add(currentNode);            //Damit der Knoten nicht wiederholt geprüft wird

            expandNode(currentNode);

        } while (search);
    }


    /**
     * Nachbarknoten werden überprüft.
     *
     * @param currentNode
     */
    private void expandNode(Node currentNode) {
        for (Node successor : currentNode.getSuccessor()) {
            if (closedList.contains(successor)) {
                continue;
            }

            double cost = currentNode.getCostFromStart() + successor.getCost();

            if (openlist.containsKey(successor) && cost >= (successor.getCostFromStart())) {     //Wenn der Nachbarknoten in der Liste höhere Kosten als der zu betrachtende Knoten,
                continue;                                                                                               //dann wird dieser nicht weiter betrachtet.
            }

            gepruefte++;
            PanelHopsControl.setASternGeprueft(gepruefte);


            //Prüfe ob lastNode bei successor schon gesetzt wurde und ob er weiter entfernt zum Ziel ist als vom currentNode
            if (successor.isLastNodeSet()) {
                Node lastNode = gui.getBoard()[currentNode.getLastX()][currentNode.getLastY()];

                if (pc.getSelectedItemAlgorithm().equals("A* Manhatten")) {
                    if (lastNode.getManhattenDist() > successor.getManhattenDist()) {
                        successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                        successor.setCostFromStart(cost);
                    }
                } else {
                    if (lastNode.getEuclidDist() > successor.getEuclidDist()) {
                        successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                        successor.setCostFromStart(cost);
                    }
                }

            } else {
                successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                successor.setCostFromStart(cost);
            }


            if (!(successor.getType() == 3 || successor.getType() == 2 || successor.getType() == 1 || successor.getType() == 6)) {   //Prüft ob der Knoten nicht ein Start-, Zielfeld oder eine Wand ist.
                successor.setSearched(true);
            }


            if (openlist.containsValue(successor)) {
                openlist.remove(successor);
                /*
                try {
                    for (Node key : openlist.keySet()) {
                        if (key.getX() == successor.getX() && key.getY() == successor.getY()) {
                            openlist.remove(key);
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }

                 */

            }
            successor.setHops(currentNode.getHops() + 1);     // Zählt jedesmal um eins hoch.
            openlist.put(successor, (cost + successor.getManhattenDist()));
        }
    }

    /**
     * Zeigt am Ende den richtigen Weg in Gelb an.
     */
    private void showPath() {
        Node finalNode = gui.getFinalNode();
        Node startNode = gui.getStartNode();
        System.out.println(finalNode.getHops() + 1);
        PanelHopsControl.setaSternhoptext(finalNode.getCostFromStart());

        while (!finalNode.equals(startNode)) {
            finalNode = gui.getBoard()[finalNode.getLastX()][finalNode.getLastY()];
            if (!finalNode.equals(startNode)) {
                finalNode.setPath(true);
            }
        }
    }
}

