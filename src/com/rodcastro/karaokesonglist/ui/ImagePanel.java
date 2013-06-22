package com.rodcastro.karaokesonglist.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author rodcastro
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String filename) throws IOException {
        this(new File(filename));
    }

    public ImagePanel(File file) throws IOException {
        image = ImageIO.read(file);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setImage(String filename) throws IOException {
        image = ImageIO.read(new File(filename));
    }
}