/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Controllers.LoginController;
import Models.Employees;
import Models.EmployeesDao;

/**
 *
 * @author Richa
 */
public class LoginView extends javax.swing.JFrame {

    // Instanciar Clases
    Employees employee = new Employees();
    EmployeesDao employees_dao = new EmployeesDao();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginView.class.getName());

    /**
     * Creates new form LoginView
     */
    public LoginView() {
        initComponents();
        setupModernLoginUI();
        setSize(930, 420);
        setResizable(false);

        // Controlador Login
        LoginController employee_login =
                new LoginController(employee, employees_dao, this);

        setTitle("Tienda Gamer - Iniciar Sesión");
        setLocationRelativeTo(null);
        this.repaint();
    }

    /**
     * Moderniza el panel de inicio de sesión para que coincida con la estética
     * gamer oscura y sofisticada del panel principal.
     */
    private void setupModernLoginUI() {
        jPanel1.setBackground(new java.awt.Color(24, 24, 38)); // #181826 Gamer Slate

        // Reconfigurar restricciones absolutas en jPanel1
        if (jPanel1.getLayout() instanceof org.netbeans.lib.awtextra.AbsoluteLayout) {
            org.netbeans.lib.awtextra.AbsoluteLayout layout = 
                    (org.netbeans.lib.awtextra.AbsoluteLayout) jPanel1.getLayout();

            int fieldX = 70;
            int fieldWidth = 350;

            // Título
            jLabel1.setText("Iniciar Sesión");
            jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
            jLabel1.setForeground(java.awt.Color.WHITE);
            layout.addLayoutComponent(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(fieldX, 26, fieldWidth, 34));

            // Subtítulo
            javax.swing.JLabel lblSubtitle = new javax.swing.JLabel("Ingresa tus credenciales para acceder al sistema");
            lblSubtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
            lblSubtitle.setForeground(new java.awt.Color(148, 163, 184));
            jPanel1.add(lblSubtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(fieldX, 62, fieldWidth, 20));

            // Label Usuario
            jLabel2.setText("Usuario");
            jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            jLabel2.setForeground(new java.awt.Color(226, 232, 240));
            layout.addLayoutComponent(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(fieldX, 102, fieldWidth, 20));

            // Input Usuario
            txt_userName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            txt_userName.setBackground(new java.awt.Color(36, 36, 56));
            txt_userName.setForeground(java.awt.Color.WHITE);
            txt_userName.setCaretColor(java.awt.Color.WHITE);
            txt_userName.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    new javax.swing.border.LineBorder(new java.awt.Color(79, 70, 229, 180), 1, true),
                    javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)
            ));
            layout.addLayoutComponent(txt_userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(fieldX, 125, fieldWidth, 36));

            // Label Contraseña
            jLabel3.setText("Contraseña");
            jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            jLabel3.setForeground(new java.awt.Color(226, 232, 240));
            layout.addLayoutComponent(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(fieldX, 175, fieldWidth, 20));

            // Input Contraseña
            txt_password.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            txt_password.setBackground(new java.awt.Color(36, 36, 56));
            txt_password.setForeground(java.awt.Color.WHITE);
            txt_password.setCaretColor(java.awt.Color.WHITE);
            txt_password.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    new javax.swing.border.LineBorder(new java.awt.Color(79, 70, 229, 180), 1, true),
                    javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)
            ));
            layout.addLayoutComponent(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(fieldX, 198, fieldWidth, 36));

            // Botón Ingresar
            btn_enter.setText("Iniciar Sesión");
            btn_enter.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
            btn_enter.setBackground(new java.awt.Color(79, 70, 229));
            btn_enter.setForeground(java.awt.Color.WHITE);
            btn_enter.setFocusPainted(false);
            btn_enter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn_enter.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(129, 140, 248), 1, true));
            layout.addLayoutComponent(btn_enter, new org.netbeans.lib.awtextra.AbsoluteConstraints(fieldX, 260, fieldWidth, 42));

            btn_enter.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn_enter.setBackground(new java.awt.Color(99, 102, 241));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn_enter.setBackground(new java.awt.Color(79, 70, 229));
                }
            });

            // Enter key para avanzar o ingresar
            txt_userName.addActionListener(e -> txt_password.requestFocusInWindow());
            txt_password.addActionListener(e -> btn_enter.doClick());

            // Pie de página
            javax.swing.JLabel lblFooter = new javax.swing.JLabel("Tienda Gamer © 2026 • Sistema de Gestión", javax.swing.SwingConstants.CENTER);
            lblFooter.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
            lblFooter.setForeground(new java.awt.Color(100, 116, 139));
            jPanel1.add(lblFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(fieldX, 335, fieldWidth, 20));
        }

        jPanel1.revalidate();
        jPanel1.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_userName = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        btn_enter = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        WallPaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Spline Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Iniciar Sesion");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 280, 60));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 2, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Usuario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, 30));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Contraseña");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        txt_userName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_userNameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 210, -1));

        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        jPanel1.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 210, 30));

        btn_enter.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_enter.setText("Ingresar");
        btn_enter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enterActionPerformed(evt);
            }
        });
        jPanel1.add(btn_enter, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 210, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 490, 420));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        WallPaper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ChatGPT Image 11 mar 2026, 15_12_33.png"))); // NOI18N
        jPanel2.add(WallPaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 420));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void txt_userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_userNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_userNameActionPerformed

    private void btn_enterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_enterActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new LoginView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel WallPaper;
    public javax.swing.JButton btn_enter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JPasswordField txt_password;
    public javax.swing.JTextField txt_userName;
    // End of variables declaration//GEN-END:variables
}
