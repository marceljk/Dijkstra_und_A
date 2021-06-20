import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGUI extends JPanel implements ActionListener {
    private static int widthX = 600;
    private static int height = 600;
    private static int cellSize = 20;

    private Timer t;

    private Node[][] board;

    private int clickCounter = 1;
    private static int finishx;
    private static int finishy;
    private static int startx;
    private static int starty;

    private PanelControl panelControl;

    public BoardGUI() {
        setPreferredSize(new Dimension(widthX, height));
        setBackground(Color.WHITE);
        setFocusable(true);
        setVisible(true);

        board = new Node[widthX / cellSize][height / cellSize];

        reset();

        addMouseListener(new MouseListener2());

        t = new Timer(200, this);       //Aktualisiert die Gui laufend
        t.start();

    }


    /**
     * Aktualisiert nach jeder Aktion das Programm/GUI.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void reset() {
        clickCounter = 1;           // Damit man einen neuen Start- / und Endpunkt setzten kann.
        for (int x = 0; x < (widthX / cellSize); x++) {
            for (int y = 0; y < (height / cellSize); y++) {
                board[x][y] = new Node(0, x, y);
                board[x][y].setGui(this);
            }
        }
    }

    public void randomWall() {
        reset();
        int i = 0;
        int amountCellRow = ((widthX + height) / 2) / cellSize;
        while (i < (amountCellRow * percentWall(60))) {
            int w = (int) (Math.random() * amountCellRow);
            int h = (int) (Math.random() * amountCellRow);
            if (!(board[w][h].getType() == 1)) {
                board[w][h].setType(1);
                i++;
            }
        }
    }

    private double percentWall(int percent) {
        double onePercent = (widthX / cellSize);
        onePercent /= 100;
        return (onePercent * percent);
    }

    public void clearSearched() {
        for (int x = 0; x < getBoard().length; x++) {
            for (int y = 0; y < getBoard()[x].length; y++) {
                board[x][y].setSearched(false);
                board[x][y].setPath(false);
            }
        }
    }


    private void setType(int x, int y) {
        /*
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
         */

        if (panelControl.getSelectedItemToolBox().equals("Startpunkt")) {
            isSetStartFinal(2);
            board[x][y].setType(2);
            startx = x;
            starty = y;
        } else if (panelControl.getSelectedItemToolBox().equals("Ziel")) {
            isSetStartFinal(3);
            board[x][y].setType(3);
            finishx = x;
            finishy = y;
        } else if (panelControl.getSelectedItemToolBox().equals("Wand")) {
            board[x][y].setType(1);
        } else if (panelControl.getSelectedItemToolBox().equals("Wasser")) {
            board[x][y].setType(6);
        } else if (panelControl.getSelectedItemToolBox().equals("Wüste")) {
            board[x][y].setType(7);
        } else if (panelControl.getSelectedItemToolBox().equals("Frei")) {
            board[x][y].setType(0);
        } else if (panelControl.getSelectedItemToolBox() == null) {
            System.out.println("Probelm ist aufgetreten");
        }
    }

    private void isSetStartFinal(int type) {
        for (int x = 0; x < (widthX / cellSize); x++) {
            for (int y = 0; y < (height / cellSize); y++) {
                if (board[x][y].getType() == type) {
                    board[x][y].setType(0);
                    break;
                }
                ;
            }
        }
    }

    public void setPanelControl(PanelControl panelControl) {
        this.panelControl = panelControl;
    }

    /**
     * Erstellt ein Raster mit einer U Form, um A* darstellen zu können.
     */
    public void beispiel1AStern() {
        allWhite();
        //Startpunkt
        startx = (widthX / cellSize) / 2;
        starty = (int) ((height / cellSize) * 0.25);
        board[startx][starty].setType(2);

        //Zielpunkt
        finishx = (widthX / cellSize) / 2;
        finishy = (int) ((height / cellSize) * 0.75);
        board[finishx][finishy].setType(3);

        // U Form - Seiten
        for (int j = starty; j <= ((height / cellSize) * 0.6); j++) {
            if (!(board[(int) ((widthX / cellSize) * 0.3)][j].getType() == 1)) {
                board[(int) ((widthX / cellSize) * 0.3)][j].setType(1);
            }
            if (!(board[(int) ((widthX / cellSize) * 0.7)][j].getType() == 1)) {
                board[(int) ((widthX / cellSize) * 0.7)][j].setType(1);
            }
        }

        // Unterseite
        for (int j = (int) ((widthX / cellSize) * 0.3); j <= ((widthX / cellSize) * 0.7); j++) {
            if (!(board[j][(int) ((height / cellSize) * 0.6)].getType() == 1)) {
                board[j][(int) ((height / cellSize) * 0.6)].setType(1);
            }
        }
    }

    public void allWhite() {
        //int amountCellRow = ((widthX +height)/2)/cellSize;
        for (int x = 0; x < widthX / cellSize; x++) {
            for (int y = 0; y < height / cellSize; y++) {
                if (!(board[x][y].getType() == 0)) {
                    board[x][y].setType(0);
                }
            }
        }
    }

    /**
     * Prüft, um welche Art von "Kästchen" es sich handelt und färbt es entsprechend.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int w = 0; w < board.length; w++) {
            for (int h = 0; h < board[w].length; h++) {
                // 0 = leer, 1 = wand, 2 = start, 3 = ende, 4 = untersucht, 5 = weg, 6 = wasser, 7 = wüste, 8 = busch
                if (board[w][h].getType() == 0) {
                    g.setColor(Color.WHITE);
                }
                if (board[w][h].getType() == 1) {
                    g.setColor(Color.BLACK);
                }
                if (board[w][h].getType() == 2) {
                    g.setColor(Color.GREEN);
                }
                if (board[w][h].getType() == 3) {
                    g.setColor(Color.RED);
                }
                if (board[w][h].getType() == 6) {
                    g.setColor(Color.BLUE);
                }
                if (board[w][h].getType() == 7) {
                    g.setColor(Color.orange);
                }
                if(board[w][h].isSearched()) {
                    g.setColor(Color.CYAN);
                }
                if(board[w][h].isPath()) {
                    g.setColor(Color.YELLOW);
                }
                g.fillRect(w * cellSize, h * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawString(w + "|" + h, w * cellSize, (h * cellSize) + cellSize);
                //g.drawString(getBoard()[w][h].getHops()+"|"+ getBoard()[w][h].getCostFromStart() , w*cellSize, (h*cellSize)+cellSize);
                //g.drawString(""+(int) board[w][h].getCostFromStart() +"|"+ (int) board[w][h].getEuclidDist() , w*cellSize, (h*cellSize)+cellSize);
                g.drawRect(w * cellSize, h * cellSize, cellSize, cellSize);
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

    public Node getFinalNode() {
        return board[finishx][finishy];
    }

    public Node getStartNode() {
        return board[startx][starty];
    }

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

private class MouseListener2 implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            int x = e.getX();
            int y = e.getY();
            setType(x / cellSize, y / cellSize);
        } catch (Exception z) {
            System.err.println(z);
        }
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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
}
