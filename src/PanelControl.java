

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelControl extends JPanel {

    private JButton start = new JButton("Starte");
    private JButton reset = new JButton("Zurücksetzen");
    private JButton b1Astern = new JButton("Beispiel 1 A*");
    private JButton b2Astern = new JButton("Beispiel 2 A*");

    private JLabel algorithmen = new JLabel("Algorithmen:");
    private JLabel toolboxTXT = new JLabel("Toolbox:");
    private JLabel beispieleTXT = new JLabel("Beispiele:");
    private JLabel leereZeileTXT = new JLabel("");

    private String[] algorithms = {"A*", "Dijkstra"};
    private String[] toolboxes = {"Startpunkt", "Ziel", "Wand", "Wasser", "Wüste", "Busch", "Frei"};
    private String[] beispiele = {"A* U-Form", "A* U-Form mit Hindernis", "Zufallswände"};

    private JComboBox algorithm = new JComboBox(algorithms);
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

        gbc.gridy = 12;
        this.add(new JLabel(" "),gbc);

        gbc.gridy = 13;
        this.add(b1Astern,gbc);

        gbc.gridy = 14;
        this.add(b2Astern,gbc);

        reset.addActionListener(e -> {
            MainView.resetBoard();
        });

        /*
        b1Astern.addActionListener(e -> {
            MainView.beispiel1AStern();
        });

        b2Astern.addActionListener(e -> {
            MainView.beispiel2AStern();
        });
         */

        beispieleJCB.addActionListener(e -> {
            if(getSelectedItemBeispiele().equals("A* U-Form")) {
                MainView.beispiel1AStern();
            } else if(getSelectedItemBeispiele().equals("A* U-Form mit Hindernis")) {
                MainView.beispiel2AStern();
            } else if(getSelectedItemBeispiele().equals("Zufallswände")) {
                MainView.randomWall();
            }
        });

        start.addActionListener(e -> {
            //MainView.startAlgorithm();
            if(getSelectedItemAlgorithm().equals("A*")) {
                MainView.startAStern();
            }
            else {
                MainView.startDijkstra();
            }
        });

    }

    private Object getSelectedItemAlgorithm() {
        return algorithm.getSelectedItem();
    }

    public Object getSelectedItemToolBox() {
        return toolboxJCB.getSelectedItem();
    }

    private Object getSelectedItemBeispiele() {
        return beispieleJCB.getSelectedItem();
    }

}