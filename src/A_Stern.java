
import java.util.ArrayList;
import java.util.HashMap;

public class A_Stern {

    private Node node;
    private HashMap<Double, Node> openlist;
    private ArrayList<Node> closedList;
    private ArrayList<Node> sortList;
    private boolean search;
    private Node end;


    synchronized public void searchPath(){
        openlist = new HashMap<>();
        closedList = new ArrayList<>();
        openlist.put(0.0, BoardGUI.getBoard()[BoardGUI.getStartx()][BoardGUI.getStarty()]);
        search = true;
        Node currentNode;
        end = BoardGUI.getFinishNode();

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
            currentNode = openlist.get(min);        //Neuer Knoten wird zum aktuellen Knoten
            openlist.remove(min);
            if(currentNode == end){                 //Prüft ob currentNode der Zielknoten ist
                System.out.println("Weg gefunden!");
                showPath(currentNode);
                search = false;
                break;
                //return true;
            }
            closedList.add(currentNode);            //Damit der Knoten nicht wiederholt geprüft wird

            System.out.println("Currentnode: " + currentNode.getX() + " " + currentNode.getY());

            expandNode(currentNode);

        }while(search);
        //return false;
    }

    /**
     * Nachbarknoten werden überprüft.
     * @param currentNode
     */
    synchronized private void expandNode(Node currentNode) {
        for(Node successor: currentNode.getSuccessor()){
            if(closedList.contains(successor)){
                continue;
            }
            System.out.println("_______________");
            System.out.println(successor.getX());
            System.out.println(successor.getY());
            System.out.println(successor.getEuclidDist());
            double cost = currentNode.getCost() + successor.getEuclidDist();

            if(openlist.containsValue(successor) && cost >= (currentNode.getCost() + currentNode.getEuclidDist())){
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
            for (int i = 0; i < 100; i++){
                x = lastNode.getLastX();
                y = lastNode.getLastY();
                lastNode = BoardGUI.getBoard()[x][y];
                if((x == BoardGUI.getStartx()) && (y == BoardGUI.getStarty())) {
                    break;
                }
                lastNode.setType(5);
            }
    }

    private void sortQue(ArrayList<Node> sort) {

    }


}

