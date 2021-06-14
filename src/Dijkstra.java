import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class Dijkstra {
    private BoardGUI gui;
    private ArrayList<Node> q;
    private HashMap<Node, Double> abstand;
    private HashMap<Node, Node> vorgänger;

    public void setGui(BoardGUI gui){
        this.gui = gui;
    }

    private Node getMinDistNode() {double min = Double.MAX_VALUE;
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

    public HashMap<Node, Node> searchPath() {
        initialisiere();

        while (!q.isEmpty()) {
            Node u = getMinDistNode();
            q.remove(u);

            for(Node successor: u.getSuccessor()) {
                if(q.contains(successor)) {
                    distanz_update(u, successor);
                }
            }
        }
        return vorgänger;
    }

    private void initialisiere() {
        q = new ArrayList<>();
        abstand = new HashMap<>();
        vorgänger = new HashMap<>();
        for(int x = 0; x < gui.getBoard().length; x++) {
            for(int y = 0; y < gui.getBoard()[x].length; y++) {
                Node v = gui.getBoard()[x][y];
                abstand.put(v,Double.MAX_VALUE);
                vorgänger.put(v, null);
                q.add(v);
            }
        }
        abstand.put(gui.getBoard()[gui.getStartx()][gui.getStarty()], 0.0);

    }

    private void distanz_update(Node currentNode, Node successor) {
        Double costPath = abstand.get(currentNode) + successor.getCost();

        if(costPath < abstand.get(successor)) {
            abstand.remove(successor);
            abstand.put(successor, costPath);
            vorgänger.remove(successor);
            vorgänger.put(successor, currentNode);
        }
    }

    private void erstelleKuerzestenPfad() {

    }
}