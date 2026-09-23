package scratch;

import Models.Employees;
import Views.SystemView;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TestHeaderRender {
    public static void main(String[] args) {
        try {
            Employees emp = new Employees();
            emp.setId(1);
            emp.setFull_name("Johhan Gonzalez");
            emp.setRol("Administrador");

            SystemView view = new SystemView(emp);
            view.setSize(1208, 680);
            view.doLayout();
            view.validate();

            // Encontrar el panel Cabecera
            JPanel cabecera = null;
            for (Component c : view.getContentPane().getComponents()) {
                if (c instanceof JPanel && "Cabecera".equals(c.getName())) {
                    cabecera = (JPanel) c;
                    break;
                }
            }

            if (cabecera != null) {
                cabecera.doLayout();
                cabecera.validate();
                int w = cabecera.getWidth();
                int h = cabecera.getHeight();
                if (w <= 0) w = 1010;
                if (h <= 0) h = 120;

                BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = img.createGraphics();
                cabecera.printAll(g);
                g.dispose();

                File out = new File("C:\\Users\\Richa\\.gemini\\antigravity\\brain\\0173e333-0acb-47fc-971c-ff0897d1ee79\\header_preview.png");
                ImageIO.write(img, "png", out);
                System.out.println("RENDER_SUCCESS: " + out.getAbsolutePath());
            } else {
                System.out.println("Cabecera no encontrada");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
