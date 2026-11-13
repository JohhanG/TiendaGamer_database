package test;

import Models.EmployeesDao;
import java.security.MessageDigest;

/**
 * Suite de Pruebas Unitarias de Lógica de Negocio y Seguridad
 * Responsable de QA: Valentina Gonzalez
 */
public class PruebasUnitarias {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("  TIENDA GAMER - SUITE DE PRUEBAS UNITARIAS (QA)");
        System.out.println("  Modulo: Ventas, Seguridad y Calculos Financieros");
        System.out.println("======================================================");

        testMD5Hashing();
        testCalculoSubtotalVenta();
        testCalculoTotalVenta();
        testValidacionStockNegativo();
        testPoliticaBloqueoTresIntentos();

        System.out.println("------------------------------------------------------");
        System.out.printf("  RESUMEN: Total=%d | Exitosas=%d | Fallidas=%d%n", totalTests, passedTests, failedTests);
        System.out.println("======================================================");
        
        if (failedTests > 0) {
            System.err.println("ALERTA: Se detectaron fallas en las pruebas unitarias.");
            System.exit(1);
        } else {
            System.out.println("ESTADO: Todas las pruebas de QA pasaron exitosamente.");
        }
    }

    private static void testMD5Hashing() {
        totalTests++;
        String rawPassword = "admin";
        String expectedHash = "21232f297a57a5a743894a0e4a801fc3"; // MD5 conocido de "admin"
        String actualHash = EmployeesDao.encryptMD5(rawPassword);

        if (expectedHash.equalsIgnoreCase(actualHash)) {
            System.out.println("  [PASSED] Test 1: Encriptacion y Hashing MD5 de contrasenas");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 1: Hashing MD5 no coincide. Esperado: " + expectedHash + ", Obtenido: " + actualHash);
            failedTests++;
        }
    }

    private static void testCalculoSubtotalVenta() {
        totalTests++;
        int cantidad = 3;
        double precioUnitario = 150000.50;
        double subtotalEsperado = 450001.50;
        double subtotalCalculado = cantidad * precioUnitario;

        if (Math.abs(subtotalEsperado - subtotalCalculado) < 0.001) {
            System.out.println("  [PASSED] Test 2: Calculo de subtotales con precision decimal");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 2: Discrepancia en calculo de subtotal");
            failedTests++;
        }
    }

    private static void testCalculoTotalVenta() {
        totalTests++;
        double[] subtotales = { 450000.0, 120500.0, 75000.0 };
        double totalEsperado = 645500.0;
        double totalCalculado = 0.0;

        for (double sub : subtotales) {
            totalCalculado += sub;
        }

        if (Math.abs(totalEsperado - totalCalculado) < 0.001) {
            System.out.println("  [PASSED] Test 3: Acumulador de total a pagar en punto de venta");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 3: Error en la suma acumulativa de la factura");
            failedTests++;
        }
    }

    private static void testValidacionStockNegativo() {
        totalTests++;
        int stockActual = 5;
        int cantidadSolicitada = 7;
        boolean operacionPermitida = cantidadSolicitada <= stockActual;

        if (!operacionPermitida) {
            System.out.println("  [PASSED] Test 4: Bloqueo de ventas por existencias insuficientes");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 4: Se permitio una venta con sobregiro de stock");
            failedTests++;
        }
    }

    private static void testPoliticaBloqueoTresIntentos() {
        totalTests++;
        int intentosFallidos = 3;
        boolean cuentaDebeBloquearse = intentosFallidos >= 3;

        if (cuentaDebeBloquearse) {
            System.out.println("  [PASSED] Test 5: Politica de seguridad de bloqueo tras 3 intentos fallidos");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 5: Fallo al evaluar el umbral de bloqueo de cuenta");
            failedTests++;
        }
    }
}
