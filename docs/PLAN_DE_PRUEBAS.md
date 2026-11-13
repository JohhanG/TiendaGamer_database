# 📋 Plan de Pruebas de Software y Matriz de QA (Quality Assurance)

**Proyecto:** Tienda Gamer - Sistema de Gestión de Inventarios y Ventas  
**Responsable de Pruebas y QA:** Valentina Gonzalez (`valggalindez@gmail.com`)  
**Fecha de Ejecución:** Noviembre 2026  
**Ambiente de Pruebas:** Adoptium OpenJDK 25 / MySQL Server 8.0 / Aiven Cloud  

---

## 1. Alcance y Objetivos de las Pruebas

El presente documento detalla el conjunto de casos de prueba diseñados y ejecutados para verificar el correcto funcionamiento funcional, la integridad de los datos, la seguridad por perfiles (RBAC) y la resiliencia transaccional (RNF-003) del sistema **Tienda Gamer**.

---

## 2. Matriz de Casos de Prueba (CP-001 al CP-008)

| ID Caso | Módulo | Descripción de la Prueba | Datos de Entrada | Resultado Esperado | Resultado Obtenido | Estado |
| :---: | :--- | :--- | :--- | :--- | :--- | :---: |
| **CP-001** | **Seguridad** | Hashing criptográfico MD5 en el inicio de sesión. | `username: admin`, `password: admin` | La contraseña se convierte a hash `21232f297a57a5a743894a0e4a801fc3` antes de consultar MySQL. | Hash generado y comparado con éxito en la base de datos. | **APROBADO** |
| **CP-002** | **Seguridad** | Bloqueo preventivo de cuenta tras 3 intentos fallidos de login. | 3 intentos consecutivos con clave errónea para un usuario. | El campo `is_locked` pasa a `1` y el sistema muestra diálogo modal de cuenta bloqueada. | Cuenta bloqueada automáticamente en el intento #3; deniega accesos posteriores. | **APROBADO** |
| **CP-003** | **Ventas / RNF-003** | **Transaccionalidad Atómica (ACID con Rollback)** al procesar una venta múltiple. | Carrito con 3 productos donde el producto #2 solicita más unidades de las disponibles. | El sistema aborta la transacción completa (`conn.rollback()`), impidiendo crear cabecera huérfana o descontar parcialmente el producto #1. | Rollback ejecutado limpiamente; no se insertó venta ni se alteró el stock. | **APROBADO** |
| **CP-004** | **Kardex** | Cálculo automático de saldos contables en entradas y salidas de almacén. | Saldo inicial = 10. Venta de 4 unidades. Compra posterior de 8 unidades. | Saldo venta: $10 - 4 = 6$. Saldo compra: $6 + 8 = 14$. Registro histórico inmutable. | Movimientos asentados con ID de empleado, tipo de movimiento y saldos exactos. | **APROBADO** |
| **CP-005** | **Inventario Físico** | Regularización por ajuste físico en bodega con justificación de auditoría. | Conteo físico real = 12 unidades (Stock en sistema = 15). | Diferencia calculada = -3. Clasificado como `SALIDA` por merma/avería bajo tipo `5`. | Stock en catálogo actualizado a 12 y asentado movimiento tipo 5 en Kardex. | **APROBADO** |
| **CP-006** | **Seguridad (RBAC)** | Restricción de vistas y acciones según perfil oficial de usuario. | Inicio de sesión con usuario de rol `Vendedor / Cajero`. | Módulos de Empleados, Sueldos, Compras y Kardex restringidos; botones de modificar catálogo bloqueados. | Interfaz ajustada automáticamente; solo permite operar Ventas y Clientes. | **APROBADO** |
| **CP-007** | **Conectividad** | Conmutación por error inteligente (**Dual Failover**) de base de datos. | Servidor en la nube (Aiven) apagado o sin conexión a internet. | El gestor conmuta en < 1.5 s a la base de datos local `localhost:3306` sin congelar el programa. | Failover exitoso; interfaz operativa en base de datos local con indicador visual. | **APROBADO** |
| **CP-008** | **Validación (Regex)** | Validación sintáctica de cédula, teléfono y correo electrónico. | Cédula: `1098765432`, Teléfono: `3154897210`, Email: `cliente@gamer.com`. | Aceptación de datos válidos y rechazo de cadenas con letras o sin formato `@`. | El controlador valida con expresiones regulares antes de persistir en MySQL. | **APROBADO** |

---

## 3. Evidencia de Ejecución de Suites Automatizadas en el Código

En el paquete `test/` del proyecto se encuentran implementadas las clases de prueba ejecutables que respaldan esta matriz:

1. **[`test/PruebasUnitarias.java`](file:///c:/Users/Richa/OneDrive/Documentos/NetBeansProjects/TiendaGamer/test/PruebasUnitarias.java)**:  
   * Hashing MD5 (`testMD5Hashing`).
   * Precisión decimal en subtotales (`testCalculoSubtotalVenta`).
   * Acumulador de total facturado (`testCalculoTotalVenta`).
   * Bloqueo por sobregiro de existencias (`testValidacionStockNegativo`).
   * Umbral de 3 intentos fallidos (`testPoliticaBloqueoTresIntentos`).

2. **[`test/KardexLogicTest.java`](file:///c:/Users/Richa/OneDrive/Documentos/NetBeansProjects/TiendaGamer/test/KardexLogicTest.java)**:  
   * Fórmula contable de Entradas por Compras (`testCalculoSaldosEntrada`).
   * Fórmula contable de Salidas por Ventas (`testCalculoSaldosSalida`).
   * Clasificación de mermas y faltantes (`testCalculoAjusteFisicoMerma`).
   * Clasificación de hallazgos y sobrantes (`testCalculoAjusteFisicoHallazgo`).
   * Rechazo de inventario negativo (`testRestriccionExistenciasNegativas`).

3. **[`test/CustomerValidationTest.java`](file:///c:/Users/Richa/OneDrive/Documentos/NetBeansProjects/TiendaGamer/test/CustomerValidationTest.java)**:  
   * Validación de cédula numérica $\le 10$ dígitos.
   * Rechazo de identificaciones alfanuméricas.
   * Validación de teléfonos de 7 a 10 dígitos.
   * Rechazo de correos sin `@` o con sintaxis inválida.

---

## 4. Conclusión de Calidad

Todas las pruebas planificadas fueron ejecutadas con una tasa de éxito del **100% (Aprobadas)**. El sistema cumple satisfactoriamente los requerimientos funcionales y no funcionales especificados, demostrando alta robustez ante fallas de red, inconsistencias de inventario e intentos de acceso no autorizados.
