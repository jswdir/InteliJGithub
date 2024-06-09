import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.TreeSet;

class LottoGenerator {
    private Set<Integer> generatedNumbers;

    public Set<Integer> generateNumbers() {
        generatedNumbers = new TreeSet<>();
        while (generatedNumbers.size() < 6) {
            int number = (int) (Math.random() * 45) + 1;
            generatedNumbers.add(number);
        }
        return generatedNumbers;
    }

    public Set<Integer> getGeneratedNumbers() {
        return generatedNumbers;
    }
}

class BackgroundPanel extends JPanel {
    private BufferedImage image;

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
