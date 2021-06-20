import javax.swing.*;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class MainView extends JFrame {

    private static PanelControl panelcontrol;
    private MainPanelControl mainPanelControl = new MainPanelControl();

    private static BoardGUI boardgui = new BoardGUI();
    private static A_Stern aStern = new A_Stern();
    private static Dijkstra dijkstra = new Dijkstra();

    public static void resetBoard() {
        boardgui.reset();
    }

    public static void startAStern() {
        System.out.println("A* wurde gestartet");
        aStern = new A_Stern();
        clearNodeInfos();
        aStern.setPanelControl(panelcontrol);
        aStern.setGui(boardgui);
        Thread x = new Thread(aStern);
        x.start();
    }

    public static void startDijkstra() {
        System.out.println("Dijkstra wurde gestartet");
        dijkstra = new Dijkstra();
        clearNodeInfos();
        dijkstra.setGui(boardgui);
        Thread x = new Thread(dijkstra);
        x.start();
    }


    private static void clearNodeInfos() {
        for(int x = 0; x < boardgui.getBoard().length; x++) {
            for(int y = 0; y < boardgui.getBoard()[x].length; y++) {
                Node temp = boardgui.getBoard()[x][y];
                temp.reset(temp);
            }
        }
    }


    public static void beispiel1AStern() {
        boardgui.beispiel1AStern();
    }

    public static void randomWall(){ boardgui.randomWall();}

    public static void randomFields() {
        boardgui.randomFields();
    }

    public MainView(){

        // Layout
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.getContentPane().add(mainPanelControl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.getContentPane().add(boardgui, gbc);

        setResizable(false);
        pack();

        setTitle("Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelcontrol = mainPanelControl.getPanelControl();

        boardgui.setPanelControl(panelcontrol);

    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame board = new MainView();
                board.setVisible(true);
            }
        });
    }
}
