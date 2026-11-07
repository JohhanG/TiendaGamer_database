package Views;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Utilidad gráfica para gestionar fotos de productos de la Tienda Gamer.
 * Guarda las fotos optimizadas en la carpeta 'product_photos/'.
 */
public class ProductImageHelper {

    private static final String PHOTOS_DIR = "product_photos";

    /**
     * Asegura que el directorio exista en el proyecto.
     */
    public static void ensurePhotosDirectory() {
        File dir = new File(PHOTOS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Retorna la imagen del producto redimensionada y con esquinas redondeadas.
     * Si no existe o es nula, retorna un placeholder gamer elegante.
     */
    public static ImageIcon getProductImage(String imageName, int width, int height) {
        ensurePhotosDirectory();

        if (imageName != null && !imageName.trim().isEmpty()) {
            File photoFile = new File(PHOTOS_DIR, imageName.trim());
            if (!photoFile.exists()) {
                // También verificar si la ruta es absoluta o relativa
                photoFile = new File(imageName.trim());
            }

            if (photoFile.exists() && photoFile.isFile()) {
                try {
                    BufferedImage original = ImageIO.read(photoFile);
                    if (original != null) {
                        return createRoundedImage(original, width, height, 12, new Color(79, 70, 229, 180));
                    }
                } catch (Exception e) {
                    System.err.println("Aviso al cargar foto de producto (" + imageName + "): " + e.getMessage());
                }
            }
        }

        // Placeholder por defecto
        return createPlaceholder(width, height, "Sin Foto");
    }

    /**
     * Redimensiona y redondea una imagen con bordes suavizados y contorno gamer.
     */
    public static ImageIcon createRoundedImage(Image original, int width, int height, int cornerRadius, Color borderColor) {
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Fondo oscuro de tarjeta
        g2.setColor(new Color(15, 23, 42)); // Slate 900
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));

        int borderWidth = 2;
        int innerW = width - (borderWidth * 2);
        int innerH = height - (borderWidth * 2);

        // Escalar imagen manteniendo proporción
        int origW = original.getWidth(null);
        int origH = original.getHeight(null);
        if (origW <= 0 || origH <= 0) {
            g2.dispose();
            return new ImageIcon(output);
        }

        double scale = Math.min((double) innerW / origW, (double) innerH / origH);
        int drawW = Math.max(1, (int) (origW * scale));
        int drawH = Math.max(1, (int) (origH * scale));

        int drawX = borderWidth + (innerW - drawW) / 2;
        int drawY = borderWidth + (innerH - drawH) / 2;

        // Clip redondeado interior
        RoundRectangle2D.Float clip = new RoundRectangle2D.Float(
                borderWidth, borderWidth, innerW, innerH, cornerRadius - 2, cornerRadius - 2);
        g2.setClip(clip);
        g2.drawImage(original, drawX, drawY, drawW, drawH, null);
        g2.setClip(null);

        // Borde exterior
        g2.setColor(borderColor != null ? borderColor : new Color(99, 102, 241));
        g2.setStroke(new BasicStroke(borderWidth));
        g2.draw(new RoundRectangle2D.Float(1, 1, width - 2, height - 2, cornerRadius, cornerRadius));

        g2.dispose();
        return new ImageIcon(output);
    }

    /**
     * Genera un placeholder visual gamer moderno cuando no hay foto disponible.
     */
    public static ImageIcon createPlaceholder(int width, int height, String label) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int corner = 12;
        // Fondo degradado sutil
        g2.setColor(new Color(30, 41, 59)); // Slate 800
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, corner, corner));

        // Borde suave
        g2.setColor(new Color(71, 85, 105, 180));
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(1, 1, width - 2, height - 2, corner, corner));

        // Dibujar icono de gamepad / cámara
        int cx = width / 2;
        int cy = height / 2 - 10;
        int boxSize = Math.min(width, height) / 3;

        g2.setColor(new Color(148, 163, 184)); // Slate 400
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawRoundRect(cx - boxSize / 2, cy - boxSize / 2, boxSize, boxSize, 8, 8);
        g2.drawOval(cx - boxSize / 5, cy - boxSize / 5, boxSize * 2 / 5, boxSize * 2 / 5);

        // Texto descriptivo
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g2.setColor(new Color(203, 213, 225));
        FontMetrics fm = g2.getFontMetrics();
        int textW = fm.stringWidth(label);
        g2.drawString(label, cx - textW / 2, cy + boxSize / 2 + 18);

        g2.dispose();
        return new ImageIcon(image);
    }

    /**
     * Abre un JFileChooser para seleccionar una foto de producto,
     * la optimiza y la guarda en 'product_photos/prod_<id_or_code>.png'.
     * Retorna el nombre del archivo guardado o null si se cancela.
     */
    public static String chooseAndSaveProductImage(java.awt.Component parent, String productCodeOrId) {
        ensurePhotosDirectory();

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar Imagen del Producto");
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Imágenes (*.png, *.jpg, *.jpeg, *.webp)", "png", "jpg", "jpeg", "webp"));
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                BufferedImage original = ImageIO.read(selectedFile);
                if (original == null) {
                    JOptionPane.showMessageDialog(parent,
                            "El archivo seleccionado no es una imagen válida o compatible.",
                            "Error de Imagen",
                            JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                // Generar nombre único y limpio
                String cleanTag = (productCodeOrId != null && !productCodeOrId.trim().isEmpty())
                        ? productCodeOrId.trim().replaceAll("[^a-zA-Z0-9_-]", "")
                        : "nuevo";
                String fileName = "prod_" + cleanTag + "_" + System.currentTimeMillis() + ".png";
                File destFile = new File(PHOTOS_DIR, fileName);

                // Redimensionar a un tamaño óptimo para catálogo (máximo 400x400)
                int maxDim = 400;
                int origW = original.getWidth();
                int origH = original.getHeight();
                double scale = Math.min((double) maxDim / origW, (double) maxDim / origH);
                int newW = (scale < 1.0) ? Math.max(1, (int) (origW * scale)) : origW;
                int newH = (scale < 1.0) ? Math.max(1, (int) (origH * scale)) : origH;

                BufferedImage toSave = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = toSave.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(original, 0, 0, newW, newH, null);
                g.dispose();

                ImageIO.write(toSave, "png", destFile);

                return fileName;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        "Error al guardar la imagen: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
