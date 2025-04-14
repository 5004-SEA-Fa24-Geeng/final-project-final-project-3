package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A panel that displays floating heart animations.
 */
public class TitleHeartAnimationPanel extends JPanel {

    /** List of currently visible hearts. */
    private final List<Heart> hearts = new ArrayList<>();
    /** Timer to handle heart animation (movement and fading). */
    private final Timer animationTimer;
    /** Timer to periodically spawn new hearts. */
    private final Timer spawnTimer;
    /** Image used to render each heart. */
    private final Image heartImage;

    /**
     * Constructs a heart animation panel with the given size.
     *
     * @param width  the width of the panel
     * @param height the height of the panel
     */
    public TitleHeartAnimationPanel(int width, int height) {
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));

        heartImage = new ImageIcon("src/main/resources/heart.png").getImage()
                .getScaledInstance(12, 12, Image.SCALE_SMOOTH);

        animationTimer = new Timer(30, e -> {
            for (Heart heart : hearts) {
                heart.y -= 1;
                heart.opacity -= 0.015f;
            }
            hearts.removeIf(h -> h.opacity <= 0);
            repaint();
        });
        animationTimer.start();

        spawnTimer = new Timer(600, e -> hearts.add(new Heart(width)));
        spawnTimer.start();
    }

    /**
     * Paints the animated hearts.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Heart heart : hearts) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, heart.opacity));
            g2.drawImage(heartImage, heart.x, heart.y, null);
        }
    }

    /**
     * Represents a single heart in the animation.
     */
    static class Heart {
        /** X coordinate of the heart. */
        int x;

        /** Y coordinate of the heart. */
        int y;

        /** Opacity of the heart (used for fading). */
        float opacity = 1.0f;

        Heart(int width) {
            Random rand = new Random();
            this.x = rand.nextInt(width);
            this.y = rand.nextInt(100);
        }
    }
}
