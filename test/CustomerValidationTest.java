package test;

/**
 * Suite de Pruebas Unitarias para Validación de Formatos y Expresiones Regulares
 * Módulo: Clientes y Proveedores
 * Responsable de QA: Valentina Gonzalez
 */
public class CustomerValidationTest {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("  TIENDA GAMER - QA TESTING: VALIDACION DE FORMATOS");
        System.out.println("  Modulo: Expresiones Regulares (Cedula, Telefono, Email)");
        System.out.println("======================================================");

        testCedulaNumericaValida();
        testCedulaInvalidaConLetras();
        testCedulaExcedeLongitud();
        testTelefonoValido();
        testTelefonoInvalidoConLetras();
        testEmailValido();
        testEmailInvalidoSinArroba();

        System.out.println("------------------------------------------------------");
        System.out.printf("  RESUMEN: Total=%d | Exitosas=%d | Fallidas=%d%n", totalTests, passedTests, failedTests);
        System.out.println("======================================================");

        if (failedTests > 0) {
            System.err.println("ALERTA: Se detectaron fallas en las validaciones de clientes.");
            System.exit(1);
        } else {
            System.out.println("ESTADO: Todas las pruebas de validación pasaron exitosamente.");
        }
    }

    private static void testCedulaNumericaValida() {
        totalTests++;
        String cedulaValida = "1098765432";
        boolean esValida = cedulaValida.matches("\\d+") && cedulaValida.length() <= 10;

        if (esValida) {
            System.out.println("  [PASSED] Test 1: Cedula numerica valida (10 digitos)");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 1: Rechazo incorrecto de cedula valida");
            failedTests++;
        }
    }

    private static void testCedulaInvalidaConLetras() {
        totalTests++;
        String cedulaConLetras = "1098765A32";
        boolean esValida = cedulaConLetras.matches("\\d+");

        if (!esValida) {
            System.out.println("  [PASSED] Test 2: Bloqueo de cedula con caracteres alfanumericos");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 2: Se acepto una cedula con letras");
            failedTests++;
        }
    }

    private static void testCedulaExcedeLongitud() {
        totalTests++;
        String cedulaLarga = "1234567890123";
        boolean esValida = cedulaLarga.length() <= 10;

        if (!esValida) {
            System.out.println("  [PASSED] Test 3: Bloqueo de identificacion mayor a 10 digitos");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 3: Se permitio cedula que excede 10 digitos");
            failedTests++;
        }
    }

    private static void testTelefonoValido() {
        totalTests++;
        String telefonoValido = "3154897210";
        boolean esValido = telefonoValido.matches("\\d{7,10}");

        if (esValido) {
            System.out.println("  [PASSED] Test 4: Telefono celular valido (10 digitos)");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 4: Rechazo de telefono valido");
            failedTests++;
        }
    }

    private static void testTelefonoInvalidoConLetras() {
        totalTests++;
        String telefonoErroneo = "31548ABC10";
        boolean esValido = telefonoErroneo.matches("\\d{7,10}");

        if (!esValido) {
            System.out.println("  [PASSED] Test 5: Bloqueo de telefono que contiene letras");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 5: Se acepto telefono con caracteres no numericos");
            failedTests++;
        }
    }

    private static void testEmailValido() {
        totalTests++;
        String emailValido = "cliente.gamer@gmail.com";
        String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        boolean esValido = emailValido.matches(regexEmail);

        if (esValido) {
            System.out.println("  [PASSED] Test 6: Formato de correo electronico valido");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 6: Rechazo de correo valido");
            failedTests++;
        }
    }

    private static void testEmailInvalidoSinArroba() {
        totalTests++;
        String emailErroneo = "cliente.sin_arroba.gmail.com";
        String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        boolean esValido = emailErroneo.matches(regexEmail);

        if (!esValido) {
            System.out.println("  [PASSED] Test 7: Rechazo de correo electronico sin '@'");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 7: Se acepto correo mal estructurado");
            failedTests++;
        }
    }
}
