
import java.util.ArrayList;
import java.util.HashMap;

public class A_Stern implements Runnable{

    private Node node;
    private HashMap<Double, Node> openlist;
    private ArrayList<Node> closedList;
    private ArrayList<Node> sortList;
    private boolean search;
    private Node end;
    private boolean debug = true;
    private BoardGUI gui;


    @Override
    public void run() {
        searchPath();
    }

    public void setGui(BoardGUI gui){
        this.gui = gui;
    }

    public void searchPath(){
        gui.clearSearched();
        openlist = new HashMap<>();
        closedList = new ArrayList<>();
        openlist.put(0.0, gui.getBoard()[gui.getStartx()][gui.getStarty()]);

        search = true;
        Node currentNode;
        end = gui.getFinalNode();
        do {

            double min = Double.MAX_VALUE;
            for(Double key: openlist.keySet()){     //Suche Knoten mit niedrigsten Kosten
                if(min > key){
                    min = key;
                }
            }

            if(openlist.size() <= 0){               //Wenn Kein Startpunkt vorhanden, dann Suche beenden
                search = false;
                System.out.println("Kein Weg gefunden!");
                break;
            }

            try {
                Thread.sleep(50);               //Thread wartet auf vorherige Operationen, um Fehler zu vermeiden
            } catch (Exception e) {

            }

            currentNode = openlist.get(min);        //Neuer Knoten wird zum aktuellen Knoten
            openlist.remove(min);
            if(currentNode == end){                 //Prüft ob currentNode der Zielknoten ist
                System.out.println("Weg gefunden!");
                showPath(currentNode);
                search = false;
                break;
            }
            closedList.add(currentNode);            //Damit der Knoten nicht wiederholt geprüft wird

            //System.out.println("Currentnode: " + currentNode.getX() + " " + currentNode.getY());

            expandNode(currentNode);

        }while(search);
    }


    /**
     * Nachbarknoten werden überprüft.
     * @param currentNode
     */
    private void expandNode(Node currentNode) {
        for(Node successor: currentNode.getSuccessor()){
            if(closedList.contains(successor)){
                continue;
            }

            /*
            System.out.println("_______________");
            System.out.println(successor.getX());
            System.out.println(successor.getY());
            System.out.println(successor.getEuclidDist());
             */


            double cost = currentNode.getCostFromStart() + successor.getCost();

            if(openlist.containsValue(successor) && cost >= (successor.getCostFromStart())){     //Wenn der Nachbarknoten in der Liste höhere Kosten als der zu betrachtende Knoten,
                continue;                                                                                               //dann wird dieser nicht weiter betrachtet.
            }

            if(debug){
                //Prüfe ob lastNode bei successor schon gesetzt wurde und ob er weiter entfernt zum Ziel ist als vom currentNode
                if(successor.isLastNodeSet()){
                    Node lastNode = gui.getBoard()[currentNode.getLastX()][currentNode.getLastY()];
                    if(lastNode.getEuclidDist() > successor.getEuclidDist()){
                        successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                        successor.setCostFromStart(cost);
                    }
                } else {
                    successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                    successor.setCostFromStart(cost);
                }

            }else {
                successor.setLastNode(currentNode.getX(), currentNode.getY());          // Speichert den letzten Schritt, damit wir den Gesamtweg am Ende anzeigen können (ff.)
                successor.setCostFromStart(cost);
            }

            if(!(successor.getType() == 3 || successor.getType() == 2 || successor.getType() == 1)){   //Prüft ob der Knoten nicht ein Start-, Zielfeld oder eine Wand ist.
                successor.setType(4);
            }

            if(openlist.containsValue(successor)){
                try{
                    for(Double key: openlist.keySet()){
                        if(openlist.get(key).getX() == successor.getX() && openlist.get(key).getY() == successor.getY()){
                            openlist.remove(key);
                            break;
                        }
                    }
                }catch (Exception e){
                    System.err.println(e);
                }

            }
            successor.setHops(currentNode.getHops()+1);     // Zählt jedesmal um eins hoch.
            openlist.put((cost + successor.getEuclidDist()), successor);
        }
    }

    /**
     * Zeigt am Ende den richtigen Weg in Gelb an.
     * @param lastNode
     */
    private void showPath(Node lastNode){
        int x;
        int y;
        Node finalNode = gui.getBoard()[end.getFinishx()][end.getFinishy()];
        System.out.println(finalNode.getHops());
            for (int i = 0; i <= finalNode.getHops(); i++){
                x = lastNode.getLastX();
                y = lastNode.getLastY();
                lastNode = gui.getBoard()[x][y];
                if((x == gui.getStartx()) && (y == gui.getStarty())) {
                    break;
                }
                lastNode.setType(5);
            }
    }
}

