import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class PanelControl extends JPanel {

    private JButton start = new JButton("Starte");
    private JButton reset = new JButton("Zurücksetzen");

    private String[] algorithms = {"Dijkstra", "A*"};

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

        reset.addActionListener(e -> {
            MainView.resetBoard();
        });

        start.addActionListener(e -> {
            //MainView.startAlgorithm();
            MainView.startAStern();
        });

    }





}