# Taller de Nivelación PI a PII

> Introducción
 Este repositorio contiene el desarrollo del Taller de Nivelación de Programación I a Programación II.
 El objetivo del taller es reforzar los conocimientos fundamentales de programación en Java,
introducir conceptos básicos de Git y Markdown, y desarrollar habilidades de resolución de problemas y pensamiento algorítmico.

---

## 📋 Descripción

Tienda Gamer permite gestionar de forma integral una tienda de videojuegos:

- 🛒 Productos (CRUD completo con borrado lógico)
- 💰 Ventas (carrito, validación de stock, registro en BD)
- 📦 Compras (entrada de mercancía, actualización de stock)
- 👥 Clientes, Empleados, Proveedores, Categorías
- 🔐 Control de roles: **Administrador** y **Auxiliar**

---

## 🏗️ Arquitectura MVC

```
src/
├── Controllers/          ← Lógica de eventos (ActionListener, KeyListener, MouseListener)
│   ├── LoginController.java
│   ├── SettingsControllers.java
│   ├── ProductsController.java
│   ├── SalesController.java
│   ├── PurchasesController.java
│   ├── EmployeesController.java
│   ├── SuppliersController.java
│   ├── CategoriesController.java
│   └── CustomersController.java
│
├── Models/               ← Entidades + DAOs (lógica de base de datos)
│   ├── ConnectionMySQL.java
│   ├── DynamicComboBox.java
│   ├── Products.java     / ProductsDao.java
│   ├── Employees.java    / EmployeesDao.java
│   ├── Sales.java        / SalesDao.java
│   ├── Purchases.java    / PurchasesDao.java
│   ├── Customers.java    / CustomersDao.java
│   ├── Suppliers.java    / SuppliersDao.java
│   └── Categories.java   / CategoriesDao.java
│
├── Views/                ← Interfaces gráficas (Swing / NetBeans GUI)
│   ├── LoginView.java
│   └── SystemView.java
│
└── Main/
    └── main.java
```

---

## 🔐 Roles del sistema

| Rol | Acceso |
|-----|--------|
| `administrador` | Todos los módulos |
| `auxiliar` | Productos, Ventas, Compras, Clientes, Perfil |

> Los módulos **Empleados**, **Proveedores** y **Categorías** están bloqueados para el rol auxiliar.  
> El control se realiza en `SettingsControllers.java` mediante el método `isAuxiliar()`.

---

## 🗄️ Base de datos — Tablas principales

| Tabla | Descripción |
|-------|-------------|
| `employees` | Usuarios del sistema con rol |
| `products` | Inventario con stock y categoría |
| `categories` | Categorías de productos |
| `customers` | Clientes registrados |
| `suppliers` | Proveedores |
| `sales` | Cabecera de ventas |
| `sale_details` | Detalle de productos por venta |
| `purchases` | Cabecera de compras |
| `purchase_details` | Detalle de productos por compra |

---

## ⚙️ Flujo de login

```
LoginView → LoginController → EmployeesDao.loginQuery() → MySQL
                                                              ↓
                                                    SystemView(loggedEmployee)
                                                              ↓
                                                    SettingsControllers(rol)
```

---

## 🛒 Flujo de una venta

```
Código + Enter → searchCode() → llenar campos
      ↓
Ingresar cantidad → calcular subtotal (cantidad × precio)
      ↓
Agregar → validar stock suficiente → agregar fila a tabla
      ↓
Confirmar → INSERT sales → INSERT sale_details → UPDATE products (stock - cantidad)
```

---

## 🔑 Conceptos técnicos clave

| Concepto | Uso en el proyecto |
|----------|--------------------|
| `PreparedStatement` | Todas las consultas SQL — previene inyección SQL |
| `Encapsulamiento` | Atributos `private` con `getters/setters` en todas las entidades |
| `Borrado lógico` | `UPDATE status = 0` en lugar de `DELETE` — preserva historial |
| `DynamicComboBox` | Muestra nombre en JComboBox pero guarda el ID numérico |
| `DefaultTableModel` | Manejo dinámico de filas en JTable |
| `ActionListener` | Responde a clics en botones |
| `KeyListener` | Búsqueda en tiempo real y Enter en campos de código |
| `MouseListener` | Clic en tabla y navegación lateral |
| `AbstractBorder` | Efecto visual hover/activo en barra lateral con `Graphics2D` |

---

## 🚀 Requisitos para ejecutar

- **JDK** 11 o superior
- **NetBeans IDE** 17 o superior
- **MySQL Server** 8.0 o superior
- **mysql-connector-java.jar** (agregar a Libraries del proyecto)
- **AbsoluteLayout.jar** (incluido en `Libraries/`)

### Pasos

1. Crear la base de datos en MySQL y ejecutar el script SQL
2. Abrir el proyecto en NetBeans
3. Verificar la conexión en `ConnectionMySQL.java`:
   ```java
   String host = "localhost";
   String port = "3306";
   String db   = "tienda_gamer";  // nombre de tu BD
   String user = "root";
   String pass = "tu_contraseña";
   ```
4. Agregar `mysql-connector-java.jar` a las librerías del proyecto
5. Ejecutar con `F6` o desde `main.java`

---

## 🧠 Decisiones de diseño

- **AbsoluteLayout**: control total sobre posición y tamaño de componentes
- **JTabbedPane**: los 9 módulos viven como pestañas; la barra lateral cambia el tab activo con `setSelectedIndex(n)`
- **JLabel como navegación**: más flexible que JButton para efectos visuales personalizados
- **Borrado lógico**: `status = 0` protege la integridad referencial del historial de ventas
- **DynamicComboBox**: desacopla lo que ve el usuario (nombre) de lo que usa la BD (ID)

---

## 📁 Entregables del proyecto

- [x] Código fuente completo (MVC)
- [x] Diapositivas de exposición
- [x] README / documentación técnica
- [ ] Diagrama de clases
- [ ] Modelo ER (base de datos)
- [ ] Manual de usuario

---

## 👨‍💻 Tecnologías

![Java](https://img.shields.io/badge/Java-SE-orange?style=flat&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat&logo=mysql)
![NetBeans](https://img.shields.io/badge/NetBeans-IDE-green?style=flat)
![MVC](https://img.shields.io/badge/Patr%C3%B3n-MVC-purple?style=flat)
