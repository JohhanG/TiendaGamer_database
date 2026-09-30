-- ========================================================
-- Base de Datos: tiendagamer_database
-- Proyecto: Sistema de Gestión de Inventarios y Ventas (Tienda Gamer)
-- Generado automáticamente para instalación y evaluación
-- ========================================================

CREATE DATABASE IF NOT EXISTS `tiendagamer_database` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tiendagamer_database`;

SET FOREIGN_KEY_CHECKS = 0;

-- --------------------------------------------------------
-- Estructura de tabla para `activity_log`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `activity_log`;
CREATE TABLE `activity_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `activity_log_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `activity_log`
INSERT INTO `activity_log` VALUES (1, 123, 'Inicio de sesión: JohhanG', '2026-08-26 05:40:46.0');
INSERT INTO `activity_log` VALUES (2, 123, 'Inicio de sesión: JohhanG', '2026-08-26 05:44:19.0');
INSERT INTO `activity_log` VALUES (3, 123, 'Inicio de sesión: JohhanG', '2026-08-28 06:40:52.0');
INSERT INTO `activity_log` VALUES (4, 123, 'Inicio de sesión: JohhanG', '2026-08-28 07:17:01.0');
INSERT INTO `activity_log` VALUES (5, 123, 'Inicio de sesión: JohhanG', '2026-08-28 08:35:57.0');
INSERT INTO `activity_log` VALUES (6, 123, 'Inicio de sesión: JohhanG', '2026-08-28 08:43:20.0');
INSERT INTO `activity_log` VALUES (7, 123, 'Inicio de sesión: JohhanG', '2026-08-28 09:27:46.0');
INSERT INTO `activity_log` VALUES (8, 123, 'Inicio de sesión: JohhanG', '2026-08-28 09:49:40.0');
INSERT INTO `activity_log` VALUES (9, 123, 'Inicio de sesión: JohhanG', '2026-08-28 09:55:46.0');
INSERT INTO `activity_log` VALUES (10, 123, 'Inicio de sesión: JohhanG', '2026-08-28 10:12:46.0');
INSERT INTO `activity_log` VALUES (11, 123, 'Inicio de sesión: JohhanG', '2026-08-28 11:27:18.0');
INSERT INTO `activity_log` VALUES (12, 123, 'Inicio de sesión: JohhanG', '2026-08-28 11:38:10.0');
INSERT INTO `activity_log` VALUES (13, 123, 'Inicio de sesión: JohhanG', '2026-08-28 11:44:04.0');
INSERT INTO `activity_log` VALUES (14, 123, 'Inicio de sesión: JohhanG', '2026-08-28 11:54:25.0');
INSERT INTO `activity_log` VALUES (15, 123, 'Inicio de sesión: JohhanG', '2026-08-28 11:56:48.0');
INSERT INTO `activity_log` VALUES (16, 123, 'Inicio de sesión: JohhanG', '2026-08-28 12:01:39.0');
INSERT INTO `activity_log` VALUES (17, 123, 'Inicio de sesión: JohhanG', '2026-08-29 06:37:28.0');
INSERT INTO `activity_log` VALUES (18, 123, 'Inicio de sesión: JohhanG', '2026-08-29 06:42:23.0');
INSERT INTO `activity_log` VALUES (19, 123, 'Inicio de sesión: JohhanG', '2026-08-29 06:54:54.0');
INSERT INTO `activity_log` VALUES (20, 123, 'Inicio de sesión: JohhanG', '2026-08-29 06:57:22.0');
INSERT INTO `activity_log` VALUES (21, 123, 'Inicio de sesión: JohhanG', '2026-08-29 07:01:20.0');
INSERT INTO `activity_log` VALUES (22, 123, 'Inicio de sesión: JohhanG', '2026-08-29 07:01:46.0');
INSERT INTO `activity_log` VALUES (23, 123, 'Inicio de sesión: JohhanG', '2026-08-29 07:05:40.0');
INSERT INTO `activity_log` VALUES (24, 123, 'Inicio de sesión: JohhanG', '2026-08-29 07:29:43.0');
INSERT INTO `activity_log` VALUES (25, 123, 'Inicio de sesión: JohhanG', '2026-08-29 08:35:09.0');
INSERT INTO `activity_log` VALUES (26, 123, 'Inicio de sesión: JohhanG', '2026-08-31 08:37:58.0');
INSERT INTO `activity_log` VALUES (27, 123, 'Inicio de sesión: JohhanG', '2026-08-31 08:46:53.0');
INSERT INTO `activity_log` VALUES (28, 123, 'Inicio de sesión: JohhanG', '2026-08-31 08:49:14.0');
INSERT INTO `activity_log` VALUES (29, 123, 'Inicio de sesión: JohhanG', '2026-08-31 08:50:23.0');
INSERT INTO `activity_log` VALUES (30, 123, 'Inicio de sesión: JohhanG', '2026-08-31 08:55:56.0');
INSERT INTO `activity_log` VALUES (31, 123, 'Inicio de sesión: JohhanG', '2026-08-31 09:42:10.0');
INSERT INTO `activity_log` VALUES (32, 123, 'Cierre de sesión por inactividad', '2026-08-31 11:31:47.0');
INSERT INTO `activity_log` VALUES (33, 123, 'Inicio de sesión: JohhanG', '2026-08-31 11:32:06.0');
INSERT INTO `activity_log` VALUES (34, 123, 'Cierre de sesión por inactividad', '2026-08-31 12:41:11.0');
INSERT INTO `activity_log` VALUES (35, 123, 'Inicio de sesión: JohhanG', '2026-09-08 08:55:44.0');
INSERT INTO `activity_log` VALUES (36, 123, 'Cierre de sesión por inactividad', '2026-09-08 09:10:56.0');
INSERT INTO `activity_log` VALUES (37, 123, 'Inicio de sesión: JohhanG', '2026-09-12 07:28:16.0');
INSERT INTO `activity_log` VALUES (38, 123, 'Cierre de sesión por inactividad', '2026-09-12 08:25:47.0');
INSERT INTO `activity_log` VALUES (39, 123, 'Inicio de sesión: JohhanG', '2026-09-12 12:21:36.0');
INSERT INTO `activity_log` VALUES (40, 123, 'Inicio de sesión: JohhanG', '2026-09-12 12:22:49.0');
INSERT INTO `activity_log` VALUES (41, 123, 'Inicio de sesión: JohhanG', '2026-09-12 12:27:42.0');
INSERT INTO `activity_log` VALUES (42, 125, 'Inicio de sesión: SantiagoP', '2026-09-12 12:29:36.0');
INSERT INTO `activity_log` VALUES (43, 123, 'Inicio de sesión: JohhanG', '2026-09-12 12:40:27.0');
INSERT INTO `activity_log` VALUES (44, 123, 'Inicio de sesión: JohhanG', '2026-09-12 12:41:41.0');
INSERT INTO `activity_log` VALUES (45, 123, 'Inicio de sesión: JohhanG', '2026-09-12 12:46:10.0');
INSERT INTO `activity_log` VALUES (46, 123, 'Inicio de sesión: JohhanG', '2026-09-12 12:51:30.0');
INSERT INTO `activity_log` VALUES (47, 123, 'Inicio de sesión: JohhanG', '2026-09-12 12:59:35.0');
INSERT INTO `activity_log` VALUES (48, 123, 'Inicio de sesión: JohhanG', '2026-09-12 13:14:23.0');
INSERT INTO `activity_log` VALUES (49, 123, 'Inicio de sesión: JohhanG', '2026-09-12 13:16:02.0');
INSERT INTO `activity_log` VALUES (50, 123, 'Inicio de sesión: JohhanG', '2026-09-12 13:28:41.0');
INSERT INTO `activity_log` VALUES (51, 123, 'Inicio de sesión: JohhanG', '2026-09-12 13:30:57.0');
INSERT INTO `activity_log` VALUES (52, 123, 'Inicio de sesión: JohhanG', '2026-09-12 13:40:27.0');
INSERT INTO `activity_log` VALUES (53, 123, 'Inicio de sesión: JohhanG', '2026-09-12 13:47:24.0');
INSERT INTO `activity_log` VALUES (54, 123, 'Cierre de sesión por inactividad', '2026-09-12 14:53:23.0');
INSERT INTO `activity_log` VALUES (55, 123, 'Inicio de sesión: JohhanG', '2026-09-15 16:59:13.0');
INSERT INTO `activity_log` VALUES (56, 123, 'Inicio de sesión: JohhanG', '2026-09-21 06:39:59.0');
INSERT INTO `activity_log` VALUES (57, 123, 'Inicio de sesión: JohhanG', '2026-09-30 07:38:20.0');
INSERT INTO `activity_log` VALUES (58, 123, 'Inicio de sesión: JohhanG', '2026-09-30 07:45:14.0');
INSERT INTO `activity_log` VALUES (59, 123, 'Inicio de sesión: JohhanG', '2026-09-30 08:02:15.0');
INSERT INTO `activity_log` VALUES (60, 125, 'Inicio de sesión: SantiagoP', '2026-09-30 08:03:20.0');
INSERT INTO `activity_log` VALUES (61, 112, 'Inicio de sesión: juan', '2026-09-30 08:05:37.0');
INSERT INTO `activity_log` VALUES (62, 112, 'Cierre de sesión por inactividad', '2026-09-30 08:41:29.0');
INSERT INTO `activity_log` VALUES (63, 125, 'Cierre de sesión por inactividad', '2026-09-30 08:41:30.0');

-- --------------------------------------------------------
-- Estructura de tabla para `categories`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `categories`
INSERT INTO `categories` VALUES (1, 'Portatil', '2026-05-08T14:13:57', '2026-05-08 10:28:45.0');
INSERT INTO `categories` VALUES (4, 'Pantalla', '2026-05-11T15:26:04', '2026-05-11 10:26:04.0');

-- --------------------------------------------------------
-- Estructura de tabla para `customers`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` int NOT NULL,
  `full_name` varchar(60) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `customers`
INSERT INTO `customers` VALUES (1105371567, 'johhan', 'casa 32', '3234156080', 'johhan@gmail.com', '2026-05-11T13:59:22', '2026-05-11 08:59:22.0');

-- --------------------------------------------------------
-- Estructura de tabla para `employees`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
  `id` int NOT NULL,
  `full_name` varchar(60) DEFAULT NULL,
  `username` varchar(60) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `rol` varchar(50) DEFAULT NULL,
  `salary` double DEFAULT '0',
  `created` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `failed_attempts` int DEFAULT '0',
  `is_locked` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `employees`
INSERT INTO `employees` VALUES (112, 'juan esteban', 'juan', 'cra43', '3442344567', 'juan@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'Jefe de Compras', 1800000.0, '2026-05-11T15:31:11', '2026-09-30T12:46:12', 0, 0);
INSERT INTO `employees` VALUES (123, 'Johhan Gonzalez', 'JohhanG', '123', '3234185080', 'johhangonzalez11@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'Administrador', 4000000.0, NULL, '2026-09-12T18:28:57', 0, 0);
INSERT INTO `employees` VALUES (125, 'Santiago Paz', 'SantiagoP', 'casa 32', '3234506080', '', 'e10adc3949ba59abbe56e057f20f883e', 'Vendedor / Cajero', 1500000.0, '2026-05-07T12:05:14', '2026-09-30T13:02:39', 0, 0);

-- --------------------------------------------------------
-- Estructura de tabla para `kardex`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `kardex`;
CREATE TABLE `kardex` (
  `idKardex` int NOT NULL AUTO_INCREMENT,
  `idProducto` int NOT NULL,
  `idTipoMov` int NOT NULL,
  `idEmpleado` int NOT NULL,
  `cantidad` int NOT NULL,
  `efecto` varchar(10) NOT NULL,
  `saldoAnterior` int NOT NULL,
  `saldoResultante` int NOT NULL,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `observacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idKardex`),
  KEY `idProducto` (`idProducto`),
  KEY `idTipoMov` (`idTipoMov`),
  KEY `idEmpleado` (`idEmpleado`),
  CONSTRAINT `kardex_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `products` (`id`),
  CONSTRAINT `kardex_ibfk_2` FOREIGN KEY (`idTipoMov`) REFERENCES `tipos_movimiento` (`idTipoMov`),
  CONSTRAINT `kardex_ibfk_3` FOREIGN KEY (`idEmpleado`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `kardex`
INSERT INTO `kardex` VALUES (5, 2, 1, 123, 20, 'SALIDA', 20, 0, '2026-09-12 12:52:02.0', 'Venta realizada en Factura N° 15');
INSERT INTO `kardex` VALUES (6, 2, 3, 123, 20, 'ENTRADA', 0, 20, '2026-09-12 12:52:14.0', 'Devolución Venta N° 15 - Motivo: prueba');

-- --------------------------------------------------------
-- Estructura de tabla para `products`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` int DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `product_quantity` int NOT NULL DEFAULT '0',
  `crated` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `product_category_idx` (`category_id`),
  CONSTRAINT `product_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `products`
INSERT INTO `products` VALUES (1, 5559, 'Portatil 32 ra', 'portatil gamer', 1000000.0, 30, NULL, '2026-05-11 17:28:16.0', 1, '2026-05-11 08:54:14.0', 'true');
INSERT INTO `products` VALUES (2, 5668, 'pantalla', 'pantalla', 100000.0, 100, NULL, '2026-05-11 10:26:57.0', 4, '2026-05-11 10:26:57.0', 'true');

-- --------------------------------------------------------
-- Estructura de tabla para `purchase_details`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `purchase_details`;
CREATE TABLE `purchase_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `purchase_price` double DEFAULT NULL,
  `purchase_amount` int DEFAULT NULL,
  `purchase_subtotal` double DEFAULT NULL,
  `purchase_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `purchase_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `purchase_purchase_detail_idx` (`purchase_id`),
  KEY `product_purchase_detail_idx` (`product_id`),
  CONSTRAINT `product_purchase_detail` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `purchase_purchase_detail` FOREIGN KEY (`purchase_id`) REFERENCES `purchases` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `purchase_details`
INSERT INTO `purchase_details` VALUES (3, 10000.0, 32, 320000.0, 42, 1, '2026-05-11 08:57:23.0');
INSERT INTO `purchase_details` VALUES (4, 10000.0, 10, 100000.0, 43, 1, '2026-05-11 10:06:33.0');
INSERT INTO `purchase_details` VALUES (5, 10000.0, 100, 1000000.0, 44, 1, '2026-05-11 10:11:38.0');
INSERT INTO `purchase_details` VALUES (6, 1000.0, 22, 22000.0, 45, 1, '2026-05-11 10:29:24.0');
INSERT INTO `purchase_details` VALUES (7, 10000.0, 22, 220000.0, 45, 2, '2026-05-11 10:29:24.0');
INSERT INTO `purchase_details` VALUES (8, 1000000.0, 22, 2.2E7, 56, 1, '2026-05-11 18:28:08.0');
INSERT INTO `purchase_details` VALUES (9, 100000.0, 20, 2000000.0, 57, 2, '2026-05-11 18:28:28.0');
INSERT INTO `purchase_details` VALUES (10, 1000000.0, 22, 2.2E7, 58, 1, '2026-05-11 18:42:03.0');
INSERT INTO `purchase_details` VALUES (11, 1000000.0, 22, 2.2E7, 59, 1, '2026-05-12 18:28:51.0');
INSERT INTO `purchase_details` VALUES (12, 1000000.0, 22, 2.2E7, 60, 1, '2026-05-12 18:29:18.0');
INSERT INTO `purchase_details` VALUES (13, 1000000.0, 22, 2.2E7, 61, 1, '2026-05-12 18:33:44.0');
INSERT INTO `purchase_details` VALUES (14, 1000000.0, 22, 2.2E7, 62, 1, '2026-05-12 18:36:42.0');
INSERT INTO `purchase_details` VALUES (15, 1000000.0, 22, 2.2E7, 63, 1, '2026-05-14 13:35:41.0');
INSERT INTO `purchase_details` VALUES (16, 100000.0, 22, 2200000.0, 63, 2, '2026-05-14 13:35:41.0');
INSERT INTO `purchase_details` VALUES (17, 1000000.0, 10, 1.0E7, 69, 1, '2026-08-29 07:01:57.0');
INSERT INTO `purchase_details` VALUES (18, 1000000.0, 20, 2.0E7, 70, 1, '2026-08-29 07:05:27.0');
INSERT INTO `purchase_details` VALUES (19, 1000000.0, 10, 1.0E7, 71, 1, '2026-08-29 07:30:09.0');
INSERT INTO `purchase_details` VALUES (20, 1000000.0, 10, 1.0E7, 72, 1, '2026-08-31 08:50:05.0');
INSERT INTO `purchase_details` VALUES (21, 1000000.0, 5, 5000000.0, 73, 1, '2026-08-31 08:50:40.0');
INSERT INTO `purchase_details` VALUES (22, 1000000.0, 12, 1.2E7, 74, 1, '2026-08-31 08:56:17.0');
INSERT INTO `purchase_details` VALUES (23, 100000.0, 100, 1.0E7, 75, 2, '2026-09-12 12:46:36.0');

-- --------------------------------------------------------
-- Estructura de tabla para `purchases`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `purchases`;
CREATE TABLE `purchases` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total` double DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `supplier_id` int DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `estado` varchar(20) DEFAULT 'COMPLETADA',
  `motivo_cancelacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `purchase_supplier_idx` (`supplier_id`),
  KEY `purchase_employee_idx` (`employee_id`),
  CONSTRAINT `purchase_employee` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `purchase_sepplier` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `purchases`
INSERT INTO `purchases` VALUES (42, 320000.0, '2026-05-11T13:57:23', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (43, 100000.0, '2026-05-11T15:06:33', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (44, 1000000.0, '2026-05-11T15:11:38', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (45, 242000.0, '2026-05-11T15:29:24', 5, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (53, 10000.0, '2026-05-11T21:52:23', 4, 112, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (54, 3.2E7, '2026-05-11T22:10:25', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (55, 0.0, '2026-05-11T22:28:04', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (56, 2.2E7, '2026-05-11T23:28:08', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (57, 2000000.0, '2026-05-11T23:28:28', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (58, 2.2E7, '2026-05-11T23:42:03', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (59, 2.2E7, '2026-05-12T23:28:51', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (60, 2.2E7, '2026-05-12T23:29:18', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (61, 2.2E7, '2026-05-12T23:33:44', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (62, 2.2E7, '2026-05-12T23:36:42', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (63, 2.42E7, '2026-05-14T18:35:41', 5, 123, NULL, 'CANCELADA', NULL);
INSERT INTO `purchases` VALUES (64, 1.0E7, '2026-08-28T15:13:52', 4, NULL, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (65, 1.0E7, '2026-08-28T16:38:29', 4, NULL, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (67, 1.0E7, '2026-08-29T11:55:10', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (68, 1.0E7, '2026-08-29T11:57:35', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (69, 1.0E7, '2026-08-29T12:01:57', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (70, 2.0E7, '2026-08-29T12:05:27', 4, 123, NULL, 'CANCELADA', 'prueba');
INSERT INTO `purchases` VALUES (71, 1.0E7, '2026-08-29T12:30:09', 4, 123, NULL, 'CANCELADA', NULL);
INSERT INTO `purchases` VALUES (72, 1.0E7, '2026-08-31T13:50:05', 4, 123, NULL, 'COMPLETADA', NULL);
INSERT INTO `purchases` VALUES (73, 5000000.0, '2026-08-31T13:50:40', 4, 123, NULL, 'CANCELADA', NULL);
INSERT INTO `purchases` VALUES (74, 1.2E7, '2026-08-31T13:56:17', 4, 123, NULL, 'CANCELADA', NULL);
INSERT INTO `purchases` VALUES (75, 1.0E7, '2026-09-12T17:46:36', 4, 123, NULL, 'COMPLETADA', NULL);

-- --------------------------------------------------------
-- Estructura de tabla para `sale_details`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `sale_details`;
CREATE TABLE `sale_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sale_quantity` int DEFAULT NULL,
  `sale_price` double DEFAULT NULL,
  `sale_subtotal` double DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `sale_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sale_details_productos_idx` (`product_id`),
  KEY `sale_details_sales_idx` (`sale_id`),
  CONSTRAINT `sale_details_productos` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `sale_details_sales` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `sale_details`
INSERT INTO `sale_details` VALUES (1, 2, 1000000.0, 2000000.0, 1, 8);
INSERT INTO `sale_details` VALUES (2, 2, 1000000.0, 2000000.0, 1, 9);
INSERT INTO `sale_details` VALUES (3, 22, 1000000.0, 2.2E7, 1, 10);
INSERT INTO `sale_details` VALUES (4, 460, 1000000.0, 4.6E8, 1, 11);
INSERT INTO `sale_details` VALUES (5, 238, 100000.0, 2.38E7, 2, 13);
INSERT INTO `sale_details` VALUES (6, 20, 100000.0, 2000000.0, 2, 14);
INSERT INTO `sale_details` VALUES (7, 20, 100000.0, 2000000.0, 2, 15);

-- --------------------------------------------------------
-- Estructura de tabla para `sales`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `sales`;
CREATE TABLE `sales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sale_date` datetime DEFAULT NULL,
  `total` double DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `estado` varchar(50) DEFAULT 'COMPLETADA',
  `motivo_cancelacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sales_customers_idx` (`customer_id`),
  KEY `sales_employees_idx` (`employee_id`),
  CONSTRAINT `sales_customers` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `sales_employees` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `sales`
INSERT INTO `sales` VALUES (6, '2026-05-13T18:50:41', 2000000.0, 1105371567, 123, 'COMPLETADA', NULL);
INSERT INTO `sales` VALUES (7, '2026-05-13T18:51:04', 2000000.0, 1105371567, 123, 'COMPLETADA', NULL);
INSERT INTO `sales` VALUES (8, '2026-05-13T18:53:16', 2000000.0, 1105371567, 123, 'COMPLETADA', NULL);
INSERT INTO `sales` VALUES (9, '2026-05-13T18:55:02', 2000000.0, 1105371567, 123, 'COMPLETADA', NULL);
INSERT INTO `sales` VALUES (10, '2026-05-13T18:57:58', 2.2E7, 1105371567, 123, 'CANCELADA', 'prueba');
INSERT INTO `sales` VALUES (11, '2026-05-14T18:36:21', 4.6E8, 1105371567, 123, 'CANCELADA', NULL);
INSERT INTO `sales` VALUES (13, '2026-09-12T17:41:06', 2.38E7, 1105371567, 123, 'CANCELADA', 'prueba');
INSERT INTO `sales` VALUES (14, '2026-09-12T17:47:01', 2000000.0, 1105371567, 123, 'CANCELADA', 'PRUEBA');
INSERT INTO `sales` VALUES (15, '2026-09-12T17:52:03', 2000000.0, 1105371567, 123, 'CANCELADA', 'prueba');

-- --------------------------------------------------------
-- Estructura de tabla para `suppliers`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE `suppliers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `city` varchar(60) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `suppliers`
INSERT INTO `suppliers` VALUES (4, 'Valentina', 'casa', '233341234', 'cra26', 'Valentina@gmail.com', 'Cali', '2026-05-09T15:11:37', '2026-05-14 15:24:13.0');
INSERT INTO `suppliers` VALUES (5, 'pantallas', 'venta pantallas', '311394065', 'av6', 'pantallas@gmail.com', 'Cali', '2026-05-09T17:04:59', '2026-05-09 12:04:59.0');

-- --------------------------------------------------------
-- Estructura de tabla para `tipos_movimiento`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `tipos_movimiento`;
CREATE TABLE `tipos_movimiento` (
  `idTipoMov` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `efecto` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idTipoMov`),
  CONSTRAINT `tipos_movimiento_chk_1` CHECK ((`efecto` in (_utf8mb4'ENTRADA',_utf8mb4'SALIDA',_utf8mb4'AJUSTE')))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcado de datos para `tipos_movimiento`
INSERT INTO `tipos_movimiento` VALUES (1, 'VENTA', 'SALIDA');
INSERT INTO `tipos_movimiento` VALUES (2, 'COMPRA', 'ENTRADA');
INSERT INTO `tipos_movimiento` VALUES (3, 'DEVOLUCION CLIENTE', 'ENTRADA');
INSERT INTO `tipos_movimiento` VALUES (4, 'DEVOLUCION PROVEEDOR', 'SALIDA');
INSERT INTO `tipos_movimiento` VALUES (5, 'AJUSTE INVENTARIO', 'AJUSTE');

SET FOREIGN_KEY_CHECKS = 1;
