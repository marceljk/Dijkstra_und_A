
import java.util.ArrayList;

public class A_Stern {

    private Node node;
    private ArrayList<Node> openlist;
    private ArrayList<Node> closedList;
    private ArrayList<Node> sortList;
    private boolean search;
    private Node end;

    public A_Stern(){
        openlist = new ArrayList<>();
        closedList = new ArrayList<>();
        end = BoardGUI.getBoard()[BoardGUI.getFinishx()][BoardGUI.getFinishy()];
    }

    private void searchPath(){
        openlist.add(BoardGUI.getBoard()[BoardGUI.getStartx()][BoardGUI.getStarty()]);
        search = true;
        Node currentNode;

        do {
            if(openlist.size() <= 0){
                search = false;
                break;
            }
            currentNode = openlist.get(0);
            openlist.remove(0);
            if(currentNode == end){
                closedList.add(currentNode);
            }
            closedList.add(currentNode);

            expandNode(currentNode);

        }while(search);

    }

    private void expandNode(Node currentNode) {
        for()
    }

    private void sortQue(ArrayList<Node> sort) {

    }


}

