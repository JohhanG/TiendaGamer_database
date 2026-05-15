package Views;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class ToggleSwitch extends JPanel {

    private boolean darkMode = false;
    private float animPos   = 0f;   // 0.0 = claro, 1.0 = oscuro
    private Timer animTimer;

    // Colores modo claro
    private final Color TRACK_OFF  = new Color(0xD3D1C7);
    private final Color THUMB_OFF  = Color.WHITE;
    private final Color SUN_COLOR  = new Color(0xEF9F27);

    // Colores modo oscuro
    private final Color TRACK_ON   = new Color(0x534AB7);
    private final Color THUMB_ON   = Color.WHITE;
    private final Color MOON_COLOR = new Color(0x534AB7);

    private Runnable onToggle;

    public ToggleSwitch() {
        setPreferredSize(new Dimension(70, 34));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggle();
            }
        });
    }

    public void setOnToggle(Runnable r) {
        this.onToggle = r;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    private void toggle() {
        darkMode = !darkMode;

        if (animTimer != null && animTimer.isRunning()) {
            animTimer.stop();
        }

        float target = darkMode ? 1f : 0f;

        animTimer = new Timer(10, e -> {
            float diff = target - animPos;
            if (Math.abs(diff) < 0.05f) {
                animPos = target;
                ((Timer) e.getSource()).stop();
            } else {
                animPos += diff * 0.25f;
            }
            repaint();
        });
        animTimer.start();

        if (onToggle != null) {
            onToggle.run();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        int trackW = w - 4;
        int trackH = h - 4;
        int trackX = 2;
        int trackY = 2;
        int arc    = trackH;

        // --- TRACK ---
        Color trackColor = blend(TRACK_OFF, TRACK_ON, animPos);
        g2.setColor(trackColor);
        g2.fill(new RoundRectangle2D.Float(trackX, trackY, trackW, trackH, arc, arc));

        // --- THUMB ---
        int thumbDiam = trackH - 4;
        float minX = trackX + 2;
        float maxX = trackX + trackW - thumbDiam - 2;
        float thumbX = minX + (maxX - minX) * animPos;
        float thumbY = trackY + 2;

        g2.setColor(blend(THUMB_OFF, THUMB_ON, animPos));
        g2.fill(new Ellipse2D.Float(thumbX, thumbY, thumbDiam, thumbDiam));

        // --- ICONO EN EL THUMB ---
        float cx = thumbX + thumbDiam / 2f;
        float cy = thumbY + thumbDiam / 2f;
        float iconSize = thumbDiam * 0.38f;

        if (animPos < 0.5f) {
            // Sol
            drawSun(g2, cx, cy, iconSize, SUN_COLOR,
                    1f - animPos * 2f);
        } else {
            // Luna
            drawMoon(g2, cx, cy, iconSize, MOON_COLOR,
                    (animPos - 0.5f) * 2f);
        }

        g2.dispose();
    }

    private void drawSun(Graphics2D g2, float cx, float cy,
                         float r, Color color, float alpha) {
        g2.setColor(new Color(color.getRed(), color.getGreen(),
                color.getBlue(), (int)(255 * alpha)));
        g2.setStroke(new BasicStroke(1.2f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Círculo central
        g2.fill(new Ellipse2D.Float(cx - r, cy - r, r * 2, r * 2));

        // Rayos
        int rays = 8;
        float outerR = r * 1.8f;
        float innerR = r * 1.3f;
        for (int i = 0; i < rays; i++) {
            double angle = Math.toRadians(i * 360.0 / rays);
            float x1 = cx + (float)(Math.cos(angle) * innerR);
            float y1 = cy + (float)(Math.sin(angle) * innerR);
            float x2 = cx + (float)(Math.cos(angle) * outerR);
            float y2 = cy + (float)(Math.sin(angle) * outerR);
            g2.draw(new Line2D.Float(x1, y1, x2, y2));
        }
    }

    private void drawMoon(Graphics2D g2, float cx, float cy,
                          float r, Color color, float alpha) {
        g2.setColor(new Color(color.getRed(), color.getGreen(),
                color.getBlue(), (int)(255 * alpha)));

        // Luna creciente: círculo grande menos círculo desplazado
        Area moon = new Area(new Ellipse2D.Float(
                cx - r, cy - r, r * 2, r * 2));
        Area cut  = new Area(new Ellipse2D.Float(
                cx - r * 0.4f, cy - r * 1.1f, r * 1.8f, r * 1.8f));
        moon.subtract(cut);
        g2.fill(moon);
    }

    // Interpola entre dos colores según t (0.0 a 1.0)
    private Color blend(Color a, Color b, float t) {
        float s = 1f - t;
        return new Color(
            (int)(a.getRed()   * s + b.getRed()   * t),
            (int)(a.getGreen() * s + b.getGreen() * t),
            (int)(a.getBlue()  * s + b.getBlue()  * t)
        );
    }
}