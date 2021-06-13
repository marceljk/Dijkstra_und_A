
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class PanelControl extends JPanel {

    private JButton start = new JButton("Starte");
    private JButton reset = new JButton("Zurücksetzen");
    private JButton b1Astern = new JButton("Beispiel 1 A*");
    private JButton b2Astern = new JButton("Beispiel 2 A*");



    private String[] algorithms = {"A*", "Dijkstra"};

    private JComboBox algorithm = new JComboBox(algorithms);

    public PanelControl() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(start,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(reset,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(algorithm,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(b1Astern,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(b2Astern,gbc);

        reset.addActionListener(e -> {
            MainView.resetBoard();
        });

        b1Astern.addActionListener(e -> {
            MainView.beispiel1AStern();
        });

        b2Astern.addActionListener(e -> {
            MainView.beispiel2AStern();
        });


        start.addActionListener(e -> {
            //MainView.startAlgorithm();
            if(algorithm.getSelectedItem().equals("A*")) {
                MainView.startAStern();
            }
            else {
                MainView.startDijkstra();
            }
        });

    }





}