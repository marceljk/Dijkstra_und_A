import javax.swing.*;

import java.awt.*;

public class MainView extends JFrame {

    private PanelControl panelcontrol = new PanelControl();
    private static BoardGUI boardgui = new BoardGUI();
    private static A_Stern aStern = new A_Stern();
    private static Dijkstra dijkstra = new Dijkstra();

    public static void resetBoard() {
        MainView.boardgui.reset();
    }

    public static void startAStern() {
        System.out.println("A* wurde gestartet");
        aStern.searchPath();
    }

    public static void startDijkstra() {
        System.out.println("Dijkstra wurde gestartet");
        dijkstra.searchPath();
    }

    public static void beispiel1AStern() {
        MainView.boardgui.beispiel1AStern();
    }

    public MainView(){

        // Layout
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.getContentPane().add(panelcontrol, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.getContentPane().add(boardgui, gbc);

        setResizable(false);
        pack();

        setTitle("Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
