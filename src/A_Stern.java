
import java.util.ArrayList;
import java.util.HashMap;

public class A_Stern {

    private Node node;
    private HashMap<Double, Node> openlist;
    private ArrayList<Node> closedList;
    private ArrayList<Node> sortList;
    private boolean search;
    private Node end;

    public A_Stern(){
        openlist = new HashMap();
        closedList = new ArrayList<>();
    }

    public boolean searchPath(){
        openlist.put(0.0, BoardGUI.getBoard()[BoardGUI.getStartx()][BoardGUI.getStarty()]);
        search = true;
        Node currentNode;
        end = BoardGUI.getFinishNode();

        do {
            double min = Double.MAX_VALUE;
            if(openlist.size() <= 0){               //Wenn Kein Startpunkt vorhanden, dann Suche beenden
                search = false;
                break;
            }
            for(Double key: openlist.keySet()){     //Suche Knoten mit niedrigsten Kosten
                if(min > key){
                    min = key;
                }
            }
            currentNode = openlist.get(min);        //Neuer Knoten wird zum aktuellen Knoten
            openlist.remove(min);
            if(currentNode == end){                 //Prüft ob currentNode der Zielknoten ist
                System.out.println("Weg gefunden!");
                showPath(currentNode);
                return true;
            }
            closedList.add(currentNode);            //Damit der Knoten nicht wiederholt geprüft wird

            System.out.println("Currentnode: " + currentNode.getX() + "" + currentNode.getY());

            expandNode(currentNode);

        }while(search);
        System.out.println("Kein Weg gefunden!");
        return false;
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
            System.out.println("_______________");
            System.out.println(successor.getX());
            System.out.println(successor.getY());
            System.out.println(successor.getEuclidDist());
            double cost = currentNode.getCost() + successor.getEuclidDist();

            if(openlist.containsKey(successor) && cost >= (currentNode.getCost() + currentNode.getEuclidDist())){
                continue;
            }
            successor.setLastNode(currentNode.getX(), currentNode.getY());
            successor.setCost(cost);
            if(openlist.containsValue(successor)){
                try{
                    for(Double key: openlist.keySet()){
                        if(openlist.get(key).equals(successor)){
                            openlist.remove(key);
                        }
                    }
                }catch (Exception e){

                }

            }
            openlist.put(cost, successor);
        }
    }

    private void showPath(Node lastNode){
        int x;
        int y;
        do{
            x = lastNode.getLastX();
            y = lastNode.getLastY();
            lastNode = BoardGUI.getBoard()[x][y];
            lastNode.setType(5);
        }while ((x != BoardGUI.getStartx()) && (y != BoardGUI.getStarty()));
    }

    private void sortQue(ArrayList<Node> sort) {

    }


}

