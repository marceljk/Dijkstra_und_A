import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGUI extends JPanel implements ActionListener{
    private int width = 450;
    private int height = 450;
    private int cellSize = 30;

    private Timer t;

    private static Node[][] board;

    private int clickCounter = 1;
    private static int finishx;
    private static int finishy;
    private static int startx;
    private static int starty;

    public BoardGUI(){
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        setFocusable(true);
        setVisible(true);

        board = new Node[width/cellSize][height/cellSize];

        reset();
        randomWall();

        addMouseListener(new MouseListener2());

        t = new Timer(200, this);
        t.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }


    public void reset(){
        for(int x = 0; x < (width/cellSize) ; x++){
            for(int y = 0; y < (height/cellSize) ; y++){
                board[x][y] = new Node(0, x,y);
            }
        }
    }

    private void randomWall(){
        int i = 0;
        int amountCellRow = ((width+height)/2)/cellSize;
        while (i < (amountCellRow*percentWall(50)) ){
            int w = (int) (Math.random()*amountCellRow);
            int h = (int) (Math.random()*amountCellRow);
            if(!(board[w][h].getType() == 1)){
                board[w][h].setType(1);
                i++;
            }
        }
    }

    private double percentWall(int percent){
        double onePercent = (width/cellSize);
        onePercent /= 100;
        return (onePercent*percent);
    }

    private void setStartFinal(int x, int y){
        if(clickCounter == 1){
            board[x][y].setType(2);
            startx = x;
            starty = y;
        }else if (clickCounter == 2) {
            board[x][y].setType(3);
            finishx = x;
            finishy = y;
        }
        clickCounter ++;
    }

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
                g.fillRect(w*cellSize, h*cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
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

    public static Node[][] getBoard() {
        return board;
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
