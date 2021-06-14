import javax.swing.*;
import java.awt.*;

public class MainPanelControl extends JPanel {

    private PanelHopsControl panelHopsControl = new PanelHopsControl();
    private PanelControl panelControl = new PanelControl();

    public MainPanelControl() {
        // Layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);

        gbc.gridy = 0;
        gbc.gridx = 0;
        this.add(panelHopsControl,gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(panelControl,gbc);
    }

    public PanelControl getPanelControl(){
        return panelControl;
    }
}
