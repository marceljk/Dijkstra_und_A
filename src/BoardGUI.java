import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGUI extends JPanel implements ActionListener{
    private static int widthX = 450;
    private static int height = 450;
    private static int cellSize = 30;

    private Timer t;

    private Node[][] board;

    private int clickCounter = 1;
    private static int finishx;
    private static int finishy;
    private static int startx;
    private static int starty;

    public BoardGUI(){
        setPreferredSize(new Dimension(widthX, height));
        setBackground(Color.WHITE);
        setFocusable(true);
        setVisible(true);

        board = new Node[widthX /cellSize][height/cellSize];

        reset();

        addMouseListener(new MouseListener2());

        t = new Timer(200, this);       //Aktualisiert die Gui laufend
        t.start();

    }


    /**
     * Aktualisiert nach jeder Aktion das Programm/GUI.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void reset(){
        clickCounter = 1;           // Damit man einen neuen Start- / und Endpunkt setzten kann.
        for(int x = 0; x < (widthX /cellSize) ; x++){
            for(int y = 0; y < (height/cellSize) ; y++){
                board[x][y] = new Node(0, x,y);
                board[x][y].setGui(this);
            }
        }
        //randomWall();
    }

    private void randomWall(){
        int i = 0;
        int amountCellRow = ((widthX +height)/2)/cellSize;
        while (i < (amountCellRow*percentWall(55)) ){
            int w = (int) (Math.random()*amountCellRow);
            int h = (int) (Math.random()*amountCellRow);
            if(!(board[w][h].getType() == 1)){
                board[w][h].setType(1);
                i++;
            }
        }
    }

    private double percentWall(int percent){
        double onePercent = (widthX /cellSize);
        onePercent /= 100;
        return (onePercent*percent);
    }

    private void setStartFinal(int x, int y){
        if(clickCounter == 1) {
            board[x][y].setType(2);
            startx = x;
            starty = y;
            clickCounter ++;
        }else if(x == startx && y == starty){
        }else if (clickCounter == 2) {
            board[x][y].setType(3);
            finishx = x;
            finishy = y;
            clickCounter ++;
        }
    }

    /**
     * Erstellt ein Raster mit einer U Form, um A* darstellen zu können.
     */
    public void beispiel1AStern() {
        allWhite();
        //Startpunkt
        startx = 7;
        starty = 3;
        board[startx][starty].setType(2);

        //Zielpunkt
        finishx = 7;
        finishy = 13;
        board[finishx][finishy].setType(3);

        // U Form
        for (int j = 5; j <= 10; j++) {
            if(!(board[4][j].getType() == 1)){
                board[4][j].setType(1);
            }
        }
        for (int j = 5; j <= 10; j++) {
            if(!(board[11][j].getType() == 1)){
                board[11][j].setType(1);
            }
        }
        for (int j = 4; j <= 10; j++) {
            if(!(board[j][10].getType() == 1)){
                board[j][10].setType(1);
            }
        }
    }

    public void beispiel2AStern() {
        allWhite();
        // U Form
        for (int j = 5; j <= 10; j++) {
            if(!(board[4][j].getType() == 1)){
                board[4][j].setType(1);
            }
        }
        for (int j = 5; j <= 10; j++) {
            if(!(board[11][j].getType() == 1)){
                board[11][j].setType(1);
            }
        }
        for (int j = 4; j <= 10; j++) {
            if(!(board[j][10].getType() == 1)){
                board[j][10].setType(1);
            }
        }
        board[6][10].setType(6);

    }

    public void allWhite() {
        int amountCellRow = ((widthX +height)/2)/cellSize;
        for (int i = 0; i < amountCellRow; i++) {
            for (int j = 0; j < amountCellRow; j++) {
                if(!(board[i][j].getType() == 0)){
                    board[i][j].setType(0);
                }
            }
        }
    }


    /**
     * Prüft, um welche Art von "Kästchen" es sich handelt und färbt es entsprechend.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int w = 0; w < board.length;w++) {
            for (int h = 0; h < board[w].length; h++) {
                // 0 = leer, 1 = wand, 2 = start, 3 = ende, 4 = untersucht, 5 = weg, 6 = wasser, 7 = wüste, 8 = busch
                if(board[w][h].getType() == 0) {
                    g.setColor(Color.WHITE);
                }
                if(board[w][h].getType() == 1) {
                    g.setColor(Color.BLACK);
                }
                if(board[w][h].getType() == 2) {
                    g.setColor(Color.GREEN);
                }
                if(board[w][h].getType() == 3) {
                    g.setColor(Color.RED);
                }
                if(board[w][h].getType() == 4) {
                    g.setColor(Color.CYAN);
                }
                if(board[w][h].getType() == 5) {
                    g.setColor(Color.YELLOW);
                }
                if(board[w][h].getType() == 6) {
                    g.setColor(Color.BLUE);
                }
                if(board[w][h].getType() == 7) {
                    g.setColor(Color.orange);
                }
                if(board[w][h].getType() == 8) {
                    g.setColor(Color.YELLOW);
                }
                g.fillRect(w*cellSize, h*cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                //g.drawString(w + "|" + h , w*cellSize, (h*cellSize)+cellSize);
                g.drawString(""+(int) board[w][h].getCostFromStart() +"|"+ (int) board[w][h].getEuclidDist() , w*cellSize, (h*cellSize)+cellSize);
                g.drawRect(w*cellSize, h*cellSize, cellSize, cellSize);
            }
        }
    }

    public static int getStartx() {
        return startx;
    }

    public static int getStarty() {
        return starty;
    }

    public static int getFinishx() {
        return finishx;
    }

    public static int getFinishy() {
        return finishy;
    }

    public Node getFinishNode(){return board[finishx][finishy];}

    public Node[][] getBoard() {
        return board;
    }

    public static int getCellSize() {
        return cellSize;
    }

    public static int getWidthX() {
        return widthX;
    }

    public static int getHeightY() {
        return height;
    }

    private class MouseListener2 implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                int x = e.getX();
                int y = e.getY();
                setStartFinal(x/cellSize,y/cellSize);
            } catch(Exception z) {
                System.err.println(z);
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
