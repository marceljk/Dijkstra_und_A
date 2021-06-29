
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
        openlist = new HashMap<>();                 //Liste mit Knoten die untersucht werden sollen
        closedList = new ArrayList<>();             //Liste mit Knoten die bereits untersucht wurden. Vermeidung von Doppelprüfung

        openlist.put(gui.getBoard()[gui.getStartx()][gui.getStarty()], 0.0);    //Startknoten wird in die OpenList eingefügt

        search = true;
        Node currentNode = null;
        end = gui.getFinalNode();                       //Endknoten aus der GUI
        do {

            if (openlist.size() <= 0) {               //Wenn die OpenList lerr ist, dann Suche beenden
                search = false;
                System.out.println("Kein Weg gefunden!");
                break;
            }

            try {
                Thread.sleep(10);                //Thread wartet auf vorherige Operationen, um Fehler zu vermeiden
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double min = Double.MAX_VALUE;
            for (Node key : openlist.keySet()) {        //Suche Knoten aus OpenList mit niedrigsten Kosten
                if (min > openlist.get(key)) {
                    min = openlist.get(key);
                    currentNode = key;                  //wird in currentNode gespeichert
                }
            }

            openlist.remove(currentNode);              //currentNode wird aus OpenList entfernt, da er nun untersucht wird

            if (currentNode == end) {                 //Prüft ob currentNode der Zielknoten ist
                System.out.println("Weg gefunden!");
                showPath();                             //zeigt endgültigen Pfad an
                search = false;
                break;
            }

            closedList.add(currentNode);            //Damit der Knoten nicht wiederholt geprüft wird

            expandNode(currentNode);                //Untersuche Nachbarnknoten von currentNode

        } while (search);
    }


    /**
     * Nachbarknoten werden überprüft.
     *
     * @param currentNode
     */
    private void expandNode(Node currentNode) {
        for (Node successor : currentNode.getSuccessor()) {         //Foreach für alle Nachbarnknoten
            if (closedList.contains(successor)) {                   //Wenn Nachbarnknoten in closedList, dann nächster Nachbarknoten
                continue;
            }

            double cost = currentNode.getCostFromStart() + successor.getCost();     //Berechnet Kosten vom Startknoten bis zum Nachbarknoten

            if (openlist.containsKey(successor) && cost >= (successor.getCostFromStart())) {     //Wenn der Nachbarknoten in der OpenList ist und die berechnet Kosten höher sind
                continue;                                                                              // als die zuvor berechneten Kosten über einen anderen Knoten, wird zum nächsten gesprungen
            }

            gepruefte++;
            PanelHopsControl.setASternGeprueft(gepruefte);              // Zählt in der GUI hoch, wie viele Knoten geprüft worden


            //Prüfe ob lastNode bei Nachbarnknoten schon gesetzt wurde und ob er weiter entfernt zum Ziel ist als vom currentNode
            if (successor.isLastNodeSet()) {
                Node lastNode = gui.getBoard()[currentNode.getLastX()][currentNode.getLastY()];

                if (pc.getSelectedItemAlgorithm().equals("A* Manhatten")) {                     //Manhatten Heuristik
                    if (lastNode.getManhattenDist() > successor.getManhattenDist()) {
                        successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                        successor.setCostFromStart(cost);
                    }
                } else {                                                                        //Euklidische Heuristik
                    if (lastNode.getEuclidDist() > successor.getEuclidDist()) {
                        successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                        successor.setCostFromStart(cost);
                    }
                }

            } else {
                successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                successor.setCostFromStart(cost);                                       // Speichert die Kosten vom Start aus
            }


            if (!(successor.getType() == 3 || successor.getType() == 2 || successor.getType() == 1 || successor.getType() == 6)) {   //Prüft ob der Knoten nicht ein Start-, Zielfeld, eine Wand oder Wasser ist.
                successor.setSearched(true);                                            //Knoten wird auf untersucht gesetzt, wird in der GUI angezeigt
            }


            if (openlist.containsKey(successor)) {                                      //Falls Knoten in OpenList vorhanden ist, solls entfernt werden
                openlist.remove(successor);

            }
            successor.setHops(currentNode.getHops() + 1);                               // Zählt jedesmal um eins hoch.
            openlist.put(successor, (cost + successor.getManhattenDist()));             //Fügt Nachbar Knoten mit neuen Kosten in die OpenList
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

        while (!finalNode.equals(startNode)) {                                              //Solange finalNode nicht Startknoten ist
            finalNode = gui.getBoard()[finalNode.getLastX()][finalNode.getLastY()];         //FinalNode wird auf vorherigen Knoten gesetzt
            if (!finalNode.equals(startNode)) {
                finalNode.setPath(true);                                                    //Knoten wird teil des endgültigen Weg und gelb gefärbt
            }
        }
    }
}

