import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGUI extends JPanel implements ActionListener{
    private static int widthX = 450;
    private static int height = 450;
    private static int cellSize = 30;

    private Timer t;

    private static Node[][] board;

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
            }
        }
        randomWall();
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
     * Prüft, um welche Art von "Kästchen" es sich handelt und färbt es entsprechend.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int w = 0; w < board.length;w++) {
            for (int h = 0; h < board[w].length; h++) {
                // 0 = leer, 1 = wand, 2 = start, 3 = ende
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
                g.fillRect(w*cellSize, h*cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawString(w + "|" + h , w*cellSize, (h*cellSize)+cellSize);
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

    public static Node getFinishNode(){return board[finishx][finishy];}

    public static Node[][] getBoard() {
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
