package gui;

import javax.swing.*;
import java.awt.*;


public class Coords extends JInternalFrame {

    private TextArea r_coords;
    public Coords()
    {
        super("Координаты робота", true, true, true, true);
        r_coords = new TextArea("");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(r_coords, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
