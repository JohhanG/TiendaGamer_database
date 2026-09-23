package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import Views.LoginView;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener {

    private Employees employee;
    private EmployeesDao employeeDao;
    private LoginView login;

    public LoginController(Employees employee,
            EmployeesDao employeeDao,
            LoginView login) {
        this.employee    = employee;
        this.employeeDao = employeeDao;
        this.login       = login;
        login.btn_enter.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login.btn_enter) {
            loginSystem();
        }
    }

    private void loginSystem() {
        String username = login.txt_userName.getText().trim();
        String password = String.valueOf(
                login.txt_password.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Complete todos los campos");
            return;
        }

        // ✅ Verificar si cuenta está bloqueada
        if (employeeDao.isAccountLocked(username)) {
            JOptionPane.showMessageDialog(null,
                    "Tu cuenta está bloqueada por demasiados intentos fallidos.\n"
                    + "Contacta al administrador.",
                    "Cuenta bloqueada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employees access = employeeDao.loginQuery(username, password);

        if (access.getId() > 0) {
            // ✅ Login exitoso — resetear intentos
            employeeDao.resetFailedAttempts(username);

            // ✅ Registrar actividad
            employeeDao.logActivity(access.getId(),
                    "Inicio de sesión: " + username);

            login.dispose();
            SystemView system = new SystemView(access);
            system.setLocationRelativeTo(null);
            system.setVisible(true);

        } else {
            // ✅ Login fallido — incrementar intentos
            employeeDao.incrementFailedAttempts(username);
            employeeDao.checkAndLockAccount(username);

            // Verificar si se bloqueó en este intento
            if (employeeDao.isAccountLocked(username)) {
                JOptionPane.showMessageDialog(null,
                        "Has excedido el número de intentos.\n"
                        + "Tu cuenta ha sido bloqueada.\n"
                        + "Contacta al administrador.",
                        "Cuenta bloqueada",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Obtener intentos restantes
                JOptionPane.showMessageDialog(null,
                        "Usuario o contraseña incorrectos.\n"
                        + "Tu cuenta se bloqueará después de 3 intentos fallidos.",
                        "Error de acceso",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}