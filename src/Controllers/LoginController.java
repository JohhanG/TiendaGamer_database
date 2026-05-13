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

        this.employee = employee;
        this.employeeDao = employeeDao;
        this.login = login;

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
        String password = String.valueOf(login.txt_password.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
            return;
        }

        Employees access = employeeDao.loginQuery(username, password);

        if (access.getId() > 0) {
            login.dispose();

            // Se pasa el empleado logueado al SystemView para manejar rol y perfil
            SystemView system = new SystemView(access);
            system.setLocationRelativeTo(null);
            system.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
        }
    }
}