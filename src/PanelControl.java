

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelControl extends JPanel {

    private JButton start = new JButton("Starte");
    private JButton reset = new JButton("Zurücksetzen");
    private JButton b1Astern = new JButton("Beispiel 1 A*");

    private JLabel algorithmen = new JLabel("Algorithmen:");
    private JLabel toolboxTXT = new JLabel("Toolbox:");
    private JLabel beispieleTXT = new JLabel("Beispiele:");
    private JLabel leereZeileTXT = new JLabel("");

    private static String[] algorithms = {"A* Manhatten","A* Euklidisch", "Dijkstra"};
    private String[] toolboxes = {"Startpunkt", "Ziel", "Wand", "Wasser", "Wüste", "Frei"};
    private String[] beispiele = {"U-Form", "Zufallswände"};

    private static JComboBox algorithm = new JComboBox(algorithms);
    private JComboBox toolboxJCB = new JComboBox(toolboxes);
    private JComboBox beispieleJCB = new JComboBox(beispiele);

    //BORDER
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    public PanelControl() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        this.setBorder(BorderFactory.createTitledBorder(loweredetched,"Einstellungen"));
        this.setPreferredSize(new Dimension(180,350));

        int breite = 150;
        int hoehe = 25;

        gbc.gridx = 0;
        gbc.gridy = 0;
        start.setPreferredSize(new Dimension(breite,hoehe));
        this.add(start,gbc);

        gbc.gridy = 1;
        this.add(new JLabel(" "),gbc);

        gbc.gridy = 2;
        reset.setPreferredSize(new Dimension(breite,hoehe));
        this.add(reset,gbc);

        gbc.gridy = 3;
        this.add(new JLabel(" "),gbc);

        gbc.gridy = 4;
        this.add(algorithmen,gbc);

        gbc.gridy = 5;
        algorithm.setPreferredSize(new Dimension(breite,hoehe));
        this.add(algorithm,gbc);

        gbc.gridy = 6;
        this.add(new JLabel(" "),gbc);

        gbc.gridy = 7;
        this.add(toolboxTXT,gbc);

        gbc.gridy = 8;
        toolboxJCB.setPreferredSize(new Dimension(breite,hoehe));
        this.add(toolboxJCB,gbc);

        gbc.gridy = 9;
        this.add(new JLabel(  " "),gbc);

        gbc.gridy = 10;
        this.add(beispieleTXT,gbc);

        gbc.gridy = 11;
        beispieleJCB.setPreferredSize(new Dimension(breite,hoehe));
        this.add(beispieleJCB,gbc);

        reset.addActionListener(e -> {
            MainView.resetBoard();
        });

        beispieleJCB.addActionListener(e -> {
            if(getSelectedItemBeispiele().equals("U-Form")) {
                MainView.beispiel1AStern();
            } else if(getSelectedItemBeispiele().equals("Zufallswände")) {
                MainView.randomWall();
            }
        });

        start.addActionListener(e -> {
            if(getSelectedItemAlgorithm().equals("A* Euklidisch")) {
                MainView.startAStern();
            } else if(getSelectedItemAlgorithm().equals("A* Manhatten")) {
                MainView.startAStern();
            }
            else {
                MainView.startDijkstra();
            }
        });
    }

    public static Object getSelectedItemAlgorithm() {
        return algorithm.getSelectedItem();
    }

    public Object getSelectedItemToolBox() {
        return toolboxJCB.getSelectedItem();
    }

    private Object getSelectedItemBeispiele() {
        return beispieleJCB.getSelectedItem();
    }

}