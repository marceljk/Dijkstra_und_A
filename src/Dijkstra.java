import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class Dijkstra implements Runnable{
    private BoardGUI gui;
    private ArrayList<Node> q;
    private HashMap<Node, Double> abstand;
    private HashMap<Node, Node> vorgänger;

    @Override
    public void run() {
        searchPath();
    }

    public void setGui(BoardGUI gui){
        this.gui = gui;
    }

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
        gui.clearSearched();
        initialisiere();

        boolean search = true;

        while (!q.isEmpty() && search) {
            Node u = getMinDistNode();
            q.remove(u);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(Node successor: u.getSuccessor()) {

                if(!(successor.getType() == 3 || successor.getType() == 2 || successor.getType() == 1)){   //Prüft ob der Knoten nicht ein Start-, Zielfeld oder eine Wand ist.
                    successor.setType(4);
                }

                if(q.contains(successor)) {
                    distanz_update(u, successor);
                }

                if(successor.equals(gui.getFinalNode())){
                    search = false;
                    break;
                }
            }
        }

        erstelleKuerzestenPfad();
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
        ArrayList<Node> path = new ArrayList<>();
        Node finalNode = gui.getFinalNode();
        path.add(finalNode);
        while (vorgänger.get(finalNode) != null) {
            finalNode = vorgänger.get(finalNode);
            path.add(finalNode);
        }
        for(Node node:path) {
            if( !( node.equals(gui.getBoard()[gui.getStartx()][gui.getStarty()]) || node.equals(gui.getFinalNode()) ) ){
                node.setType(5);
            }
        }
    }

}