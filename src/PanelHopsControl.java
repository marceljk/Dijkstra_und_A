import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelHopsControl extends JPanel {

    private JLabel aSternHops;
    private JLabel dijkstraHops;

    private String asternHopString = "000";
    private String dijkstraHopString = "000";

    private String asterngeprueftString = "000";
    private String dijkstrageprueft = "000";

    private JTextArea asterngepruefttxt = new JTextArea(asterngeprueftString);
    private JTextArea dijkstragepruefttxt = new JTextArea(dijkstraHopString);

    private JTextArea aSternhoptxt = new JTextArea(asternHopString);
    private JTextArea dijkstrahoptxt = new JTextArea(dijkstraHopString);

    private JLabel geprueft = new JLabel("geprüft");
    private JLabel schritte = new JLabel("Schritte");

    //BORDER
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    public PanelHopsControl() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        this.setBorder(BorderFactory.createTitledBorder(loweredetched,"Schritte"));
        this.setPreferredSize(new Dimension(180,80));

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(geprueft,gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(schritte,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(aSternHops = new JLabel("A* "),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(aSternhoptxt,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(asterngepruefttxt,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(dijkstraHops = new JLabel("Dijkstra "),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(dijkstrahoptxt,gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(dijkstragepruefttxt,gbc);
    }
}