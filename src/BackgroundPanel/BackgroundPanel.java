package BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class BackgroundPanel extends JPanel {
    private final BufferedImage image;

    public BackgroundPanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
