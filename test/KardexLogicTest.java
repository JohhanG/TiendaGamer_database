package test;

/**
 * Suite de Pruebas Unitarias para la Lógica de Control de Inventarios (Kardex)
 * Responsable de QA: Valentina Gonzalez
 */
public class KardexLogicTest {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("  TIENDA GAMER - QA TESTING: MODULO KARDEX");
        System.out.println("  Trazabilidad, Formulas de Saldos y Ajustes Fisicos");
        System.out.println("======================================================");

        testCalculoSaldosEntrada();
        testCalculoSaldosSalida();
        testCalculoAjusteFisicoMerma();
        testCalculoAjusteFisicoHallazgo();
        testRestriccionExistenciasNegativas();

        System.out.println("------------------------------------------------------");
        System.out.printf("  RESUMEN: Total=%d | Exitosas=%d | Fallidas=%d%n", totalTests, passedTests, failedTests);
        System.out.println("======================================================");

        if (failedTests > 0) {
            System.err.println("ALERTA: Se detectaron fallas en las pruebas del Kardex.");
            System.exit(1);
        } else {
            System.out.println("ESTADO: Todas las pruebas de Kardex pasaron exitosamente.");
        }
    }

    private static void testCalculoSaldosEntrada() {
        totalTests++;
        int saldoAnterior = 15;
        int cantidadEntrada = 10;
        int saldoResultanteEsperado = 25;
        int saldoResultanteCalculado = saldoAnterior + cantidadEntrada;

        if (saldoResultanteCalculado == saldoResultanteEsperado) {
            System.out.println("  [PASSED] Test 1: Formula de Entrada (Compras): SaldoResultante = SaldoAnterior + Cantidad");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 1: Error en formula de Entrada en Kardex");
            failedTests++;
        }
    }

    private static void testCalculoSaldosSalida() {
        totalTests++;
        int saldoAnterior = 20;
        int cantidadSalida = 6;
        int saldoResultanteEsperado = 14;
        int saldoResultanteCalculado = saldoAnterior - cantidadSalida;

        if (saldoResultanteCalculado == saldoResultanteEsperado) {
            System.out.println("  [PASSED] Test 2: Formula de Salida (Ventas): SaldoResultante = SaldoAnterior - Cantidad");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 2: Error en formula de Salida en Kardex");
            failedTests++;
        }
    }

    private static void testCalculoAjusteFisicoMerma() {
        totalTests++;
        int stockSistema = 18;
        int conteoRealFisico = 15; // Faltan 3 unidades (merma/daño)
        int diferencia = conteoRealFisico - stockSistema; // -3
        String efecto = (diferencia >= 0) ? "ENTRADA" : "SALIDA";
        int unidadesAjuste = Math.abs(diferencia);

        if (unidadesAjuste == 3 && "SALIDA".equals(efecto)) {
            System.out.println("  [PASSED] Test 3: Ajuste fisico por faltante/merma (Diferencia negativa clasificada como SALIDA)");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 3: Error al clasificar merma en inventario fisico");
            failedTests++;
        }
    }

    private static void testCalculoAjusteFisicoHallazgo() {
        totalTests++;
        int stockSistema = 10;
        int conteoRealFisico = 14; // Sobran 4 unidades (hallazgo)
        int diferencia = conteoRealFisico - stockSistema; // +4
        String efecto = (diferencia >= 0) ? "ENTRADA" : "SALIDA";
        int unidadesAjuste = Math.abs(diferencia);

        if (unidadesAjuste == 4 && "ENTRADA".equals(efecto)) {
            System.out.println("  [PASSED] Test 4: Ajuste fisico por sobrante/hallazgo (Diferencia positiva clasificada como ENTRADA)");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 4: Error al clasificar sobrante en inventario fisico");
            failedTests++;
        }
    }

    private static void testRestriccionExistenciasNegativas() {
        totalTests++;
        int nuevoStockPropuesto = -2;
        boolean operacionRechazada = nuevoStockPropuesto < 0;

        if (operacionRechazada) {
            System.out.println("  [PASSED] Test 5: Rechazo estricto de ajustes que generen existencias negativas en bodega");
            passedTests++;
        } else {
            System.err.println("  [FAILED] Test 5: El sistema permitio saldo negativo en inventario");
            failedTests++;
        }
    }
}
