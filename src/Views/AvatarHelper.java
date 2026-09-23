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
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Utilidad para gestionar y renderizar avatares circulares
 * y fotos de perfil de los empleados.
 */
public class AvatarHelper {

    private static final String PHOTOS_DIR = "profile_photos";

    /**
     * Asegura que el directorio de fotos de perfil exista en el proyecto.
     */
    private static File getPhotosDirectory() {
        File dir = new File(PHOTOS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * Retorna el archivo de foto para un empleado según su ID si existe.
     */
    public static File getEmployeePhotoFile(int employeeId) {
        File dir = getPhotosDirectory();
        String[] extensions = {".png", ".jpg", ".jpeg", ".webp"};
        for (String ext : extensions) {
            File f = new File(dir, "emp_" + employeeId + ext);
            if (f.exists() && f.isFile() && f.length() > 0) {
                return f;
            }
        }
        return null;
    }

    /**
     * Obtiene el avatar del empleado en formato circular.
     * Si no tiene foto personalizada, genera un avatar con sus iniciales.
     */
    public static ImageIcon getEmployeeAvatar(int employeeId, String fullName, int size) {
        File photoFile = getEmployeePhotoFile(employeeId);
        if (photoFile != null) {
            try {
                BufferedImage original = ImageIO.read(photoFile);
                if (original != null) {
                    return createCircularAvatar(original, size, 2, new Color(255, 255, 255, 220));
                }
            } catch (IOException e) {
                System.err.println("Error al cargar foto de perfil: " + e.getMessage());
            }
        }
        // Fallback: avatar con iniciales y degradado moderno
        return createInitialsAvatar(fullName, size);
    }

    /**
     * Convierte cualquier imagen en un avatar circular con bordes suavizados y contorno.
     */
    public static ImageIcon createCircularAvatar(Image original, int size, int borderWidth, Color borderColor) {
        BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int avatarSize = size - (borderWidth * 2);
        int offset = borderWidth;

        // Clip circular
        Ellipse2D.Float clip = new Ellipse2D.Float(offset, offset, avatarSize, avatarSize);
        g2.setClip(clip);

        // Escalar y centrar manteniendo proporción
        int origW = original.getWidth(null);
        int origH = original.getHeight(null);
        if (origW <= 0 || origH <= 0) {
            origW = 100;
            origH = 100;
        }

        double scale = Math.max((double) avatarSize / origW, (double) avatarSize / origH);
        int drawW = (int) (origW * scale);
        int drawH = (int) (origH * scale);
        int drawX = offset + (avatarSize - drawW) / 2;
        int drawY = offset + (avatarSize - drawH) / 2;

        g2.drawImage(original, drawX, drawY, drawW, drawH, null);

        // Quitar clip para dibujar el borde
        g2.setClip(null);

        if (borderWidth > 0 && borderColor != null) {
            g2.setStroke(new BasicStroke(borderWidth));
            g2.setColor(borderColor);
            g2.draw(new Ellipse2D.Float(offset / 2f, offset / 2f, size - offset, size - offset));
        }

        g2.dispose();
        return new ImageIcon(output);
    }

    /**
     * Crea un avatar moderno con las iniciales del empleado y fondo estilizado.
     */
    public static ImageIcon createInitialsAvatar(String fullName, int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fondo circular con color moderno (azul-violeta)
        Color bgTop = new Color(92, 107, 192);
        Color bgBottom = new Color(63, 81, 181);
        g2.setPaint(new java.awt.GradientPaint(0, 0, bgTop, 0, size, bgBottom));
        g2.fill(new Ellipse2D.Float(2, 2, size - 4, size - 4));

        // Borde blanco sutil
        g2.setColor(new Color(255, 255, 255, 180));
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new Ellipse2D.Float(2, 2, size - 4, size - 4));

        // Extraer iniciales (ej: "Johhan Gonzalez" -> "JG")
        String initials = extractInitials(fullName);

        // Dibujar texto centrado
        g2.setColor(Color.WHITE);
        int fontSize = (int) (size * 0.40);
        g2.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        FontMetrics fm = g2.getFontMetrics();
        int textX = (size - fm.stringWidth(initials)) / 2;
        int textY = ((size - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(initials, textX, textY);

        g2.dispose();
        return new ImageIcon(image);
    }

    private static String extractInitials(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "U";
        }
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        } else {
            return ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase();
        }
    }

    /**
     * Aplica el avatar del empleado al botón indicado, configurándolo para un aspecto moderno.
     */
    public static void applyPhotoToButton(JButton button, int employeeId, String fullName, int size) {
        ImageIcon icon = getEmployeeAvatar(employeeId, fullName, size);
        button.setIcon(icon);
        button.setText("");
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText("Clic para cambiar foto de perfil (" + (fullName != null ? fullName : "Usuario") + ")");
    }

    /**
     * Abre un JFileChooser para seleccionar una nueva foto de perfil,
     * la redimensiona y guarda en la carpeta profile_photos/emp_<id>.png.
     */
    public static ImageIcon chooseAndSaveEmployeePhoto(java.awt.Component parent, int employeeId, String fullName, int targetSize) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar Foto de Perfil");
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"));
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                BufferedImage original = ImageIO.read(selectedFile);
                if (original == null) {
                    JOptionPane.showMessageDialog(parent,
                            "El archivo seleccionado no es una imagen válida.",
                            "Error de Imagen",
                            JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                // Guardar en profile_photos/emp_<id>.png
                File dir = getPhotosDirectory();
                File destFile = new File(dir, "emp_" + employeeId + ".png");

                // Redimensionar para optimizar almacenamiento si es muy grande
                int maxDim = 512;
                int w = original.getWidth();
                int h = original.getHeight();
                BufferedImage toSave;
                if (w > maxDim || h > maxDim) {
                    double ratio = Math.min((double) maxDim / w, (double) maxDim / h);
                    int newW = (int) (w * ratio);
                    int newH = (int) (h * ratio);
                    toSave = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = toSave.createGraphics();
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g.drawImage(original, 0, 0, newW, newH, null);
                    g.dispose();
                } else {
                    toSave = original;
                }

                ImageIO.write(toSave, "png", destFile);

                JOptionPane.showMessageDialog(parent,
                        "¡Foto de perfil actualizada con éxito!",
                        "Foto Actualizada",
                        JOptionPane.INFORMATION_MESSAGE);

                return getEmployeeAvatar(employeeId, fullName, targetSize);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        "No se pudo guardar la imagen: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
