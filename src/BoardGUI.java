import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGUI extends JPanel implements ActionListener{
    private int width = 300;
    private int height = 300;
    private int cellSize = 30;

    private Image emptyField;
    private Image blackField;

    private Timer t;

    private static Image[][] board;

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

        board = new Image[width/cellSize][height/cellSize];

        init();
        randomWall();

        addMouseListener(new MouseListener2());

        t = new Timer(200, this);
        t.start();

    }

    /**
    *
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    private void init(){
        ImageIcon empty = new ImageIcon("weißes Feld.png");
        emptyField = empty.getImage();
        ImageIcon black = new ImageIcon("schwarzes Feld.png");
        blackField = black.getImage();
        for(int w = 0; w < (width/cellSize) ; w++){
            for(int h = 0; h < (height/cellSize) ; h++){
                board[w][h] = emptyField;
            }
        }
    }

    private void randomWall(){
        int i = 0;
        int amountCellRow = ((width+height)/2)/cellSize;
        while (i < (amountCellRow*percentWall(50)) ){
            int w = (int) (Math.random()*amountCellRow);
            int h = (int) (Math.random()*amountCellRow);
            if(!board[w][h].equals(blackField)){
                board[w][h] = blackField;
                i++;
            }
        }
    }

    private double percentWall(int percent){
        double onePercent = (width/cellSize);
        onePercent /= 100;
        return (onePercent*percent);
    }

    private void setStart(int x, int y){
        if(clickCounter == 1){
            Image startFeld = new ImageIcon("start Feld.png").getImage();
            board[x][y] = startFeld;
            startx = x;
            starty = y;
        }else if (clickCounter == 2) {
            Image endFeld = new ImageIcon("ende Feld.png").getImage();
            board[x][y] = endFeld;
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
                g.drawImage(board[w][h], w*cellSize, h*cellSize, this);
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

    private class MouseListener2 implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                int x = e.getX();
                int y = e.getY();
                setStart(x/cellSize,y/cellSize);
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
