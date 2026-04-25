/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TikTakToeRetro;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class BackgroundPanel extends JPanel {
    private Image bg;

    public BackgroundPanel(String resourcePath) {
        try {
            bg = ImageIO.read(getClass().getResource(resourcePath));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Failed to load background: " + resourcePath);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}