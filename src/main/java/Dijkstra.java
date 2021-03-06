import java.util.ArrayList;
import java.util.HashMap;

public class Dijkstra implements Runnable{
    private BoardGUI gui;
    private ArrayList<Node> q;
    private HashMap<Node, Double> abstand;
    private HashMap<Node, Node> vorgänger;

    @Override
    public void run() {                     // Berechnungen können während der Laufzeit der GUI weiterlaufen
        searchPath();
    }

    public void setGui(BoardGUI gui){       // übernimmt die aktuelle GUI / Muster um den Dijkstra darauf berechnen lassen zu können.
        this.gui = gui;
    }

    /**
     * Bekommt den Knoten mit dem kleinsten Abstand vom Startpunkt
     * @return
     */
    private Node getMinDistNode() {
        double min = Double.MAX_VALUE;
        Node current = null;
        for(Node c: q){                                 //Suche Knoten mit niedrigsten Kosten
            double d = abstand.get(c);
            if(min > d){
                min = d;
                current = c;
            }
        }
        return current;
    }

    public void searchPath() {
        int gepruefte;
        gui.clearSearched();
        initialisiere();

        try {                                       // "Damit nicht prüftbare Knoten nicht das Programm abstürzen lässt"
            while (!q.isEmpty()) {                  // Solange Liste mit möglichen Knoten nicht leer ist, suche weiter
                Node u = getMinDistNode();          // Knoten mit niedrigsten Kosten    // Betrachtungsknoten
                q.remove(u);                        // Knoten mit niedrigsten Kosten wird von Liste entfernt.

                try {
                    Thread.sleep(10);          // Pause von 10 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (Node successor : u.getSuccessor()) {     // Bekommt die Nachbarknoten von dem letzten betrachteten Knoten, welcher entfernt wurde.     // Nachbarknoten

                    if (!(successor.getType() == 3 || successor.getType() == 2 || successor.getType() == 1)) {   //Prüft ob der Knoten nicht ein Start-, Zielfeld oder eine Wand ist.
                        successor.setSearched(true);        // Kasten auf Blau setzen (geprüfte)
                    }

                    gepruefte = 0;
                    for (int x = 0; x < gui.getBoard().length; x++) {
                        for (int y = 0; y < gui.getBoard()[x].length; y++) {
                            if (gui.getBoard()[x][y].isSearched()) {            // Alle geprüften (Blauen) werden aufgezählt.
                                gepruefte++;
                            }
                        }
                    }
                    PanelHopsControl.setDijkstraGeprueft(gepruefte);        // Zeigt an, wie viele Knoten bereits geprüft sind.

                    if (q.contains(successor)) {            // Wenn Nachbarknoten noch nicht uberprüft wurde (noch in Liste ist).
                        distanz_update(u, successor);       // Knoten mit niedrigsten Kosten + Nachbarknoten
                    }
                }
            }
        }catch (NullPointerException ex) {

        } finally {
            erstelleKuerzestenPfad();
        }
    }

    /**
     * Liste mit alle möglichen Knoten wird intitialisiert.
     */
    private void initialisiere() {
        q = new ArrayList<>();              // Liste mit allen Knoten
        abstand = new HashMap<>();
        vorgänger = new HashMap<>();
        for(int x = 0; x < gui.getBoard().length; x++) {
            for(int y = 0; y < gui.getBoard()[x].length; y++) {
                Node v = gui.getBoard()[x][y];
                abstand.put(v,Double.MAX_VALUE);
                vorgänger.put(v, null);
                if(v.getType() != 1) {      // Alle Kasten (außer Wand) werden der Liste hinzugefügt.
                    q.add(v);
                }
            }
        }
        abstand.put(gui.getBoard()[gui.getStartx()][gui.getStarty()], 0.0);
    }

    /**
     * Update der Abstände, falls ein kürzerer Weg gefunden wurde.
     * @param currentNode -> aktuell günstigster Knoten
     * @param successor -> Nachbarknoten
     */
    private void distanz_update(Node currentNode, Node successor) {
        Double costPath = abstand.get(currentNode) + successor.getCost();
        successor.setCostFromStart(costPath);

        if(costPath < abstand.get(successor)) {
            abstand.remove(successor);
            abstand.put(successor, costPath);
            vorgänger.remove(successor);
            vorgänger.put(successor, currentNode);
        }
    }

    private void erstelleKuerzestenPfad() {
        ArrayList<Node> path = new ArrayList<>();
        Node finalNode = gui.getFinalNode();
        path.add(finalNode);
        if(vorgänger.get(finalNode) == null) {
            System.out.println("Kein Weg gefunden");
        } else {
            while (vorgänger.get(finalNode) != null) {
                finalNode = vorgänger.get(finalNode);
                path.add(finalNode);
            }
            System.out.println("Größe: "+path.size());
            for(Node node:path) {
                if( !( node.equals(gui.getBoard()[gui.getStartx()][gui.getStarty()]) || node.equals(gui.getFinalNode()) ) ){
                    node.setPath(true);
                }
            }
            System.out.println("Kosten: "+ gui.getFinalNode().getCostFromStart());
            PanelHopsControl.setDijkstraHopTxt(gui.getFinalNode().getCostFromStart());
            System.out.println("Weg gefunden");
        }

    }

}