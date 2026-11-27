# ⏱️ Guion Oficial Calibrado para 12 Minutos — TiendaGamer
**Sustentación Final: Bases de Datos II / Proyecto Integrador (UNIAJC)**  
*Expositores: Johhan González Pajón (6:00 min) & Valentina González Galíndez (6:00 min)*  
*Docente Evaluador: Ing. Oscar Alberto Soto*  
*Tiempo Total Límite: 12:00 minutos exactos*

---

## 📊 Matriz de Cronómetro (12 Minutos: 6:00 Johhan / 6:00 Valentina)

| Marca de Tiempo | Diapositiva / Fase | Tema / Acción | Expositor | Duración |
| :---: | :--- | :--- | :---: | :---: |
| **0:00 – 0:30** | **Slide 1** | Portada Institucional (Saludo protocolario) | **Ambos** (15s c/u) | 30 seg |
| **0:30 – 1:15** | **Slide 2** | Portada del Proyecto y Ficha Técnica | **Johhan** | 45 seg |
| **1:15 – 2:30** | **Slide 3** | 1. Contexto, Problema y Casos de Uso | **Valentina** | 1 min 15s |
| **2:30 – 3:30** | **Slide 4** | 2. Objetivos y Matriz RBAC | **Johhan** | 1 min |
| **3:30 – 5:00** | **Slide 5** | 3. Solución Propuesta (Interfaces reales de NetBeans) | **Valentina** | 1 min 30s |
| **5:00 – 6:45** | **Slide 6** | 4. Diseño 3FN, Stored Procedures y Arquitectura MVC | **Johhan** | 1 min 45s |
| **6:45 – 8:15** | **Slide 7** / Demo | 5. Demostración Parte 1: Cajero POS + Kardex Salida | **Valentina** | 1 min 30s |
| **8:15 – 9:45** | **Slide 7** / Demo | 5. Demostración Parte 2: Admin + Ajuste Merma + Rollback | **Johhan** | 1 min 30s |
| **9:45 – 11:00** | **Slide 8** | 6. Resultados Obtenidos y Conclusiones Técnicas | **Valentina** | 1 min 15s |
| **11:00 – 12:00** | **Slide 9** | 7. Anexos, Commits GitHub y Entrega de Credenciales | **Ambos** (30s c/u) | 1 min |
| **TOTAL** | **9 Diapositivas** | **Exposición Completa y Equitativa** | **50% / 50%** | **12:00 min** |

---

## 🎯 Regla de Oro para Cumplir los 12 Minutos
* **Uno habla, el otro opera el mouse/teclado:** Mientras Johhan expone, Valentina hace los clics en las pestañas o cambia de diapositiva, y viceversa. Esto ahorra hasta 2 minutos de silencios muertos.
* **No leer las diapositivas:** Las diapositivas ya tienen el texto visible para el profesor. Ustedes deben **explicar el porqué técnico**.

---

## 🎬 Guion de Exposición con Marcas de Cronómetro

---

### [0:00 – 0:30] Diapositiva 1: Portada Institucional UNIAJC *(30 seg — Ambos)*

* **Johhan (15s):**
  > *"Muy buenos días profesor Oscar Soto y jurado evaluador. Presentamos la sustentación final de nuestro Proyecto Integrador para la Tecnología en Sistemas de Información de la Institución Universitaria Antonio José Camacho."*

* **Valentina (15s):**
  > *"Nuestro proyecto articula el modelado relacional en Tercera Forma Normal, el control transaccional ACID y el desarrollo de software bajo el patrón MVC/DAO. A continuación, damos apertura a la sustentación."*
*(Pasar a Slide 2)*.

---

### [0:30 – 1:15] Diapositiva 2: Portada del Proyecto Integrador *(45 seg — Johhan)*

* **Johhan (45s):**
  > *"El proyecto se titula: **TiendaGamer: Sistema de Información para la Gestión de Inventarios, Ventas en Punto de Venta (POS) y Kardex Continuo Inmutable**, desarrollado por Valentina González y mi persona, Johhan González.*
  > 
  > *El reto principal que resolvimos fue erradicar los descuadres y fugas de inventario en comercios de hardware gamer, los cuales manejaban operaciones concurrentes sin control transaccional y sin trazabilidad forense."*
*(Pasar a Slide 3)*.

---

### [1:15 – 2:30] Diapositiva 3: 1. Contexto y Problema Identificado *(1 min 15s — Valentina)*

* **Valentina (1 min 15s):**
  > *"El hardware gamer posee un alto costo unitario, donde la pérdida de una sola unidad genera un impacto financiero considerable.*
  > 
  > *Identificamos tres debilidades críticas en el diagnóstico:*
  > 1. * **Desincronización de existencias:** Ventas simultáneas sin bloqueo pesimista permitían vender productos ya agotados.*
  > 2. * **Ajustes manuales vulnerables:** El personal modificaba el stock directamente en pantalla sin comprobante formal ni causa justificada.*
  > 3. * **Falta de roles:** Cajeros con acceso a módulos administrativos.*
  > 
  > *En el **Diagrama de Casos de Uso** a la izquierda, modelamos de forma estricta las fronteras operativas entre los cuatro roles del negocio: Administrador, Cajero, Bodeguero y Cliente."*
*(Pasar a Slide 4)*.

---

### [2:30 – 3:30] Diapositiva 4: 2. Objetivos del Proyecto y Matriz RBAC *(1 min — Johhan)*

* **Johhan (1 min):**
  > *"Formulamos como **Objetivo General**: Desarrollar e implementar un sistema transaccional en **Java 17 Swing y MySQL 8.0**, que automatice facturación POS y compras mediante un Kardex continuo con transacciones ACID.*
  > 
  > *Trazamos cuatro objetivos específicos:*
  > 1. * **Modelado en 3FN:** 10 tablas normalizadas con integridad referencial estricta.*
  > 2. * **Stored Procedures Defensivos:** Lógica crítica centralizada con bloqueo pesimista `SELECT ... FOR UPDATE` y rollback automático.*
  > 3. * **Frontend Swing Ergonómico:** Patrón MVC/DAO desacoplado y semáforo de existencias.*
  > 4. * **Seguridad RBAC y Failover:** Segregación de perfiles y conexión dual tolerante a desconexiones de red.*
  > 
  > *A la derecha observan la **Matriz RBAC**, la cual restringe a nivel de controladores las acciones de cada rol."*
*(Pasar a Slide 5)*.

---

### [3:30 – 5:00] Diapositiva 5: 3. Solución Propuesta y Funcionalidades *(1 min 30s — Valentina)*
*(Valentina hace clics breves en los botones laterales para alternar las 5 interfaces reales)*:

* **Valentina (1 min 30s):**
  > *"En esta galería observan las interfaces desarrolladas en NetBeans con FlatLaf:*
  > * *(Clic 1: Login)* **Autenticación Segura (RF-001):** Valida claves cifradas con MD5 y habilita solo los módulos autorizados por rol.
  > * *(Clic 2: Catálogo)* **Catálogo Multimedia (RF-002):** Permite asociar la fotografía real del producto y gestiona un semáforo dinámico de existencias: verde en óptimo, amarillo en límite y rojo para reposición.
  > * *(Clic 3: POS)* **Facturación POS (RF-005):** Módulo de alta velocidad que liquida subtotales, valida stock y ejecuta la venta atómica en milisegundos.
  > * *(Clic 4: Compras)* **Compras y Abastecimiento (RF-004):** Ingreso formal de proveedores mayoristas, recalculando costos y sumando inventario físico.
  > * *(Clic 5: Kardex)* **Kardex Continuo Inmutable (RF-006):** Núcleo contable del sistema. Cada movimiento queda grabado con fecha, responsable, cantidad, saldo anterior y nuevo saldo. Es un registro de solo inserción, garantizando auditoría forense."*
*(Pasar a Slide 6)*.

---

### [5:00 – 6:45] Diapositiva 6: 4. Diseño y Desarrollo de la Solución *(1 min 45s — Johhan)*
*(Johhan o Valentina hace clic en las 4 pestañas superiores según avance la explicación)*:

* **Johhan (1 min 45s):**
  > *(Clic Pestaña 1: MEER 3FN)*
  > *"En la base de datos alcanzamos la **Tercera Forma Normal (3FN)**. Las 10 entidades bajo motor InnoDB eliminan redundancias mediante claves foráneas gobernadas por `ON DELETE RESTRICT`.*
  > 
  > *(Clic Pestaña 2: Stored Procedures)*
  > *Nuestra premisa de ingeniería es que **la lógica crítica pertenece al motor de base de datos**:*
  > * `sp_registrar_venta_pos`: Ejecuta `SELECT ... FOR UPDATE` sobre la fila del producto gamer. Bloquea pesimistamente el registro en memoria, valida existencias, rebaja inventario y asienta la **SALIDA** en Kardex en una sola transacción ACID.
  > * `sp_registrar_compra`: Asienta la **ENTRADA** con su costo de adquisición.
  > * `sp_ajuste_inventario_kardex`: Permite regularizar diferencias físicas exigiendo obligatoriamente un motivo formal superior a 10 caracteres.
  > 
  > *(Clic Pestaña 3: Tecnologías)*
  > *Utilizamos **Java 17 LTS**, **MySQL 8.0** y en `ConnectionMySQL.java` desarrollamos un **Failover Dinámico** que conmuta de forma automática entre la nube y el servidor local ante caídas de red.*
  > 
  > *(Clic Pestaña 4: Arquitectura MVC/DAO)*
  > *A nivel de código, el patrón **MVC/DAO** desacopla las Vistas de las sentencias SQL. Los DAOs gestionan transacciones con `conn.setAutoCommit(false)` y ejecutan `conn.rollback()` ante cualquier excepción."*
*(Pasar a Slide 7)*.

---

### [6:45 – 8:15] Diapositiva 7 / Demo en Vivo: Parte 1 — Cajero POS y Kardex *(1 min 30s — Valentina)*
*(Si tienen el software abierto en NetBeans, conmutan a la ventana; si no, usan la Diapositiva 7)*:

* **Valentina (1 min 30s):**
  > *"Procedemos con la demostración operativa del sistema:*
  > 1. * **Inicio como Cajero:** Ingresamos con el usuario `cajero1` y clave `cajero123`. Como pueden ver, los menús de Empleados, Proveedores y el botón de Ajuste de Kardex están inhabilitados por la matriz RBAC.
  > 2. * **Venta POS en vivo:** Seleccionamos 2 unidades de un producto gamer; la interfaz calcula el total y ejecuta la venta de forma instantánea.
  > 3. * **Auditoría inmediata en Kardex:** Pasamos a la pestaña de Kardex y observamos cómo se generó automáticamente el registro de **SALIDA** con saldo anterior y saldo resultante exactos."*

---

### [8:15 – 9:45] Diapositiva 7 / Demo en Vivo: Parte 2 — Admin, Mermas y Rollback ACID *(1 min 30s — Johhan)*

* **Johhan (1 min 30s):**
  > *"Continuamos con la auditoría administrativa y pruebas transaccionales:*
  > 1. * **Ajuste Físico como Admin:** Cerramos sesión e ingresamos con `admin` / `admin123`. Al ir a Kardex, ahora el botón 'Ajuste de Inventario' sí está habilitado. Registramos una **MERMA** por 1 unidad justificando: 'Avería de caja en transporte'. El Stored Procedure valida el motivo (>10 caracteres), descuenta el stock y asienta el ajuste.
  > 2. * **Prueba de Rollback Transaccional (RNF-003):** En la tabla de pruebas resumimos el test de estrés: simulamos un fallo intencional de red en mitad de una venta tras insertar la cabecera. El bloque `catch` en Java interceptó el error y ejecutó `conn.rollback()`. Resultado: **cero facturas huérfanas y stock íntegro**.
  > 3. * **Prueba de Concurrencia con FOR UPDATE:** Con 1 unidad en stock, dos compras simultáneas fueron procesadas: la primera confirmó y la segunda fue rechazada limpiamente con Error 409, evitando saldos negativos."*
*(Pasar a Slide 8)*.

---

### [9:45 – 11:00] Diapositiva 8: 6. Resultados y Conclusiones Técnicas *(1 min 15s — Valentina)*

* **Valentina (1 min 15s):**
  > *"Como **Resultados Obtenidos**, alcanzamos:*
  > 1. * **Cero Descuadres:** El 100% de los movimientos de inventario cuenta con un comprobante formal y trazabilidad en Kardex.
  > 2. * **Alta Velocidad:** Tiempos de respuesta inferiores a **50 milisegundos** por transacción gracias a los Stored Procedures precompilados.
  > 3. * **Seguridad:** Segregación efectiva donde ningún cajero puede modificar precios ni inventarios.
  > 
  > *Nuestras **Conclusiones Técnicas** son:*
  > * *La lógica crítica debe residir en la base de datos para no depender de la estabilidad del cliente.*
  > * *El Kardex continuo es indispensable para garantizar auditoría forense total.*
  > * *El patrón MVC/DAO deja la arquitectura modular para integrar en trabajo futuro facturación electrónica DIAN y lectores ópticos USB de código de barras."*
*(Pasar a Slide 9)*.

---

### [11:00 – 12:00] Diapositiva 9: 7. Anexos, Commits GitHub y Entrega de Credenciales *(1 min — Ambos)*

* **Johhan (30s):**
  > *"En los anexos compartimos el **Repositorio Oficial en GitHub** (`JohhanG/TiendaGamer_database`), donde constan los commits colaborativos de ambos integrantes: arquitectura DAO, procedimientos SQL y failover por mi parte, y lógica POS, interfaz, Kardex y pruebas ACID por parte de Valentina.*
  > 
  > *Entregamos al profesor Oscar Soto la tabla con las **3 credenciales de prueba** activas en la base de datos: `admin` / `admin123`, `cajero1` / `cajero123` y `bodega1` / `bodega123`."*

* **Valentina (30s):**
  > *"Profesor Oscar y jurado evaluador, agradecemos mucho su tiempo y atención. Concluimos exactamente dentro de los 12 minutos reglamentarios y quedamos a su entera disposición para responder sus preguntas o realizar cualquier prueba técnica sobre el código."*

---

## ⏱️ Consejos para Clavar el Tiempo en 12:00 Minutos

1. **Usa un celular con cronómetro visible en la mesa:** Al llegar al minuto 5:00 deben estar terminando la diapositiva 5. Al minuto 6:45 deben entrar a la demostración de la diapositiva 7.
2. **Si el profesor los interrumpe:** No se pongan nerviosos. Respondan la duda en 30 segundos y retomen en la diapositiva donde iban.
3. **Atajos rápidos de teclado:**
   - Teclas `1` a `9`: Salto instantáneo a la diapositiva.
   - Tecla `Espacio` o `Flecha Derecha`: Siguiente.
