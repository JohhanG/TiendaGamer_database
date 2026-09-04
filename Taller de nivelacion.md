## Introducción

Este repositorio contiene el desarrollo del Taller de Nivelación de Programación I a Programación II.

El objetivo del taller es reforzar los conocimientos fundamentales de programación en Java, introducir conceptos básicos de Git y Markdown, y desarrollar habilidades de resolución de problemas y pensamiento algorítmico.

## Objetivos

* Comprender los conceptos básicos de Markdown.
* Comprender los fundamentos de Git y el control de versiones.
* Reforzar conceptos fundamentales de programación en Java.
* Introducir conceptos relacionados con la Programación Orientada a Objetos.
* Aplicar estructuras de control para resolver problemas.
* Desarrollar ejercicios básicos utilizando Java.
* Aprender el flujo básico de trabajo con Git y GitHub.

---

# Parte Teórica

## Markdown

Markdown es un lenguaje de marcado ligero que permite dar formato a documentos utilizando una sintaxis sencilla. Permite crear títulos, subtítulos, listas, enlaces, texto en negrita, cursiva y bloques de código, entre otros elementos.

Markdown es utilizado ampliamente en plataformas como GitHub, especialmente para crear archivos README y documentación de proyectos.

---

# Git

## 1. ¿Qué es un repositorio en Git y cómo se diferencia de un proyecto “normal”?

Un repositorio en Git es un espacio donde se almacena y administra un proyecto junto con el historial de los cambios realizados en sus archivos. Al inicializar Git en una carpeta se crea una carpeta oculta llamada `.git`, que contiene la información necesaria para el control de versiones.

La diferencia con un proyecto normal es que un proyecto común puede ser simplemente un conjunto de archivos y carpetas, mientras que un repositorio Git permite registrar diferentes versiones mediante commits, consultar el historial y controlar los cambios realizados.

## 2. ¿Cuáles son las tres áreas principales de Git?

Las tres áreas principales son:

* **Working Directory:** directorio donde se encuentran los archivos del proyecto y donde se realizan las modificaciones.
* **Staging Area o Index:** área donde se preparan los cambios que serán incluidos en el próximo commit. Se utiliza mediante `git add`.
* **Repository:** área donde Git almacena los commits y el historial de versiones. Se utiliza `git commit` para registrar los cambios.

El flujo básico es:

```text
Working Directory
       |
    git add
       v
Staging Area
       |
   git commit
       v
Repository
```

## 3. ¿Cómo representa Git los cambios internamente?

Git utiliza diferentes objetos para representar la información del proyecto:

* **Blob:** almacena el contenido de los archivos.
* **Tree:** representa la estructura de archivos y directorios.
* **Commit:** representa una versión del proyecto y almacena información relacionada con ella.
* **Tag:** permite identificar determinados commits, por ejemplo, para marcar versiones importantes.

## 4. ¿Cómo se crea un commit y qué información almacena?

Primero se agregan los cambios al staging:

```bash
git add .
```

Después se crea el commit:

```bash
git commit -m "Mensaje del commit"
```

El commit almacena información como el autor, fecha, mensaje, estructura del proyecto y relación con commits anteriores.

## 5. ¿Cuál es la diferencia entre `git pull` y `git fetch`?

`git fetch` obtiene información y cambios del repositorio remoto, pero no los integra automáticamente en la rama local actual.

`git pull` obtiene los cambios remotos y posteriormente los integra en la rama local.

## 6. ¿Qué es un branch?

Un branch o rama es una línea independiente de desarrollo dentro de un repositorio Git. Permite realizar cambios sin modificar directamente la rama principal.

Git administra las ramas mediante referencias que apuntan a determinados commits.

## 7. ¿Cómo se realiza un merge y qué conflictos pueden surgir?

Un merge permite integrar los cambios de una rama en otra.

Se puede realizar mediante:

```bash
git merge nombre-rama
```

Un conflicto puede aparecer cuando dos ramas modifican de manera diferente una misma parte de un archivo.

Para solucionarlo se deben revisar las diferencias, elegir o combinar correctamente los cambios y completar el proceso de merge.

## 8. ¿Cómo funciona el área de staging?

El área de staging permite seleccionar los cambios que serán incluidos en el próximo commit.

Por ejemplo:

```bash
git add Calculadora.java
```

También se pueden preparar varios cambios:

```bash
git add .
```

Si se omite `git add`, los cambios que permanecen solamente en el working directory no serán incluidos en el commit.

## 9. ¿Qué es `.gitignore`?

`.gitignore` es un archivo utilizado para indicar qué archivos y carpetas Git debe ignorar.

Puede utilizarse para evitar subir archivos temporales, archivos generados automáticamente, configuraciones locales u otros elementos que no deben formar parte del repositorio.

## 10. ¿Cuál es la diferencia entre `git commit --amend` y un nuevo commit?

`git commit --amend` permite modificar el commit más reciente, por ejemplo agregando archivos olvidados o modificando su mensaje.

Un nuevo `git commit` crea un nuevo commit independiente dentro del historial.

## 11. ¿Cómo funciona `git stash`?

`git stash` permite guardar temporalmente cambios que todavía no se quieren convertir en un commit.

Es útil cuando se necesita cambiar de rama o realizar otra tarea antes de terminar el trabajo actual.

Los cambios pueden recuperarse posteriormente mediante:

```bash
git stash pop
```

## 12. ¿Cómo se pueden deshacer cambios en Git?

Git proporciona diferentes comandos:

* `git reset`: permite modificar el estado del staging o mover la referencia de una rama según la opción utilizada.
* `git revert`: crea un nuevo commit que deshace los cambios realizados por un commit anterior.
* `git checkout`: tradicionalmente permite cambiar de rama y recuperar versiones de archivos.

Actualmente existen comandos como `git switch` y `git restore` que separan algunas de las funciones que anteriormente se realizaban con `checkout`.

## 13. ¿Cómo funcionan `origin` y `upstream`?

Los remotos permiten conectar el repositorio local con repositorios alojados en servidores como GitHub.

`origin` suele ser el nombre asignado al repositorio remoto principal.

Cuando se trabaja con un fork, `upstream` suele utilizarse para identificar el repositorio original.

Los remotos pueden consultarse mediante:

```bash
git remote -v
```

Y se puede agregar un remoto mediante:

```bash
git remote add upstream URL
```

## 14. ¿Cómo inspeccionar el historial de commits?

Git proporciona diferentes comandos:

```bash
git log
```

Permite consultar el historial de commits.

```bash
git diff
```

Permite observar diferencias entre cambios.

```bash
git show
```

Permite consultar información detallada de un commit y sus cambios.

---

## 15. ¿Cuáles son los tipos de datos primitivos en Java?

Los ocho tipos de datos primitivos son:

* `byte`
* `short`
* `int`
* `long`
* `float`
* `double`
* `char`
* `boolean`

Los tipos enteros almacenan números sin decimales, `float` y `double` almacenan números decimales, `char` representa un carácter y `boolean` representa valores verdaderos o falsos.

## 16. ¿Cómo funcionan las estructuras de control?

Las estructuras de control permiten determinar cómo se ejecutan las instrucciones.

* `if` permite ejecutar código cuando se cumple una condición.
* `else` permite ejecutar una alternativa cuando la condición anterior no se cumple.
* `switch` permite seleccionar entre diferentes casos.
* `for`, `while` y `do-while` permiten repetir instrucciones.

## 17. ¿Por qué es importante utilizar nombres significativos?

Los nombres significativos permiten comprender qué representa cada variable o método.

Esto mejora la legibilidad, facilita el mantenimiento y ayuda a que otros programadores comprendan el código.

Por ejemplo:

```java
int cantidadProductos;
```

es más claro que:

```java
int x;
```

## 18. ¿Qué es la Programación Orientada a Objetos?

La Programación Orientada a Objetos (POO) es un paradigma de programación que organiza el software mediante clases y objetos.

Una clase define características y comportamientos, mientras que un objeto es una instancia de una clase.

La POO facilita la organización, reutilización y mantenimiento del código.

## 19. ¿Cuáles son los cuatro pilares de la POO?

Los cuatro pilares son:

### Encapsulamiento

Permite proteger y controlar el acceso a los datos internos de una clase.

### Herencia

Permite que una clase adquiera características y comportamientos de otra.

### Polimorfismo

Permite que un mismo método o comportamiento tenga diferentes implementaciones.

### Abstracción

Permite representar las características esenciales de un objeto ocultando detalles innecesarios.

## 20. ¿Qué es la herencia?

La herencia permite que una clase adquiera características y comportamientos de otra clase.

En Java se utiliza principalmente mediante:

```java
extends
```

Por ejemplo:

```java
class Animal {
    void comer() {
        System.out.println("Comiendo");
    }
}

class Perro extends Animal {
}
```

La clase `Perro` hereda características de `Animal`.

## 21. ¿Qué son los modificadores de acceso?

Los modificadores de acceso controlan la visibilidad de clases, atributos y métodos.

Los principales son:

* `public`: permite un acceso amplio.
* `private`: restringe el acceso directamente a la propia clase.
* `protected`: permite acceso dentro del mismo paquete y mediante relaciones de herencia bajo las reglas de Java.
* Acceso por defecto: cuando no se especifica un modificador, el acceso se limita al paquete.

## 22. ¿Qué es una variable de entorno?

Una variable de entorno es un valor almacenado por el sistema operativo que puede ser utilizado por diferentes programas y procesos.

En Java son importantes variables como:

```text
JAVA_HOME
```

que puede indicar la ubicación del JDK, y:

```text
PATH
```

que permite acceder a diferentes herramientas desde la terminal.

---

# Parte Práctica

## Ejercicio 1 — Calculadora básica

### Descripción

Programa que permite realizar operaciones de suma, resta, multiplicación y división.

### Código

```java
import java.util.Scanner;

public class Calculadora {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el primer número: ");
        double numero1 = scanner.nextDouble();

        System.out.print("Ingrese el segundo número: ");
        double numero2 = scanner.nextDouble();

        System.out.println("Seleccione una operación:");
        System.out.println("1. Suma");
        System.out.println("2. Resta");
        System.out.println("3. Multiplicación");
        System.out.println("4. División");

        System.out.print("Opción: ");
        int opcion = scanner.nextInt();

        double resultado;

        switch (opcion) {

            case 1:
                resultado = numero1 + numero2;
                System.out.println("Resultado: " + resultado);
                break;

            case 2:
                resultado = numero1 - numero2;
                System.out.println("Resultado: " + resultado);
                break;

            case 3:
                resultado = numero1 * numero2;
                System.out.println("Resultado: " + resultado);
                break;

            case 4:
                if (numero2 != 0) {
                    resultado = numero1 / numero2;
                    System.out.println("Resultado: " + resultado);
                } else {
                    System.out.println("No se puede dividir entre cero.");
                }
                break;

            default:
                System.out.println("Opción no válida.");
        }

        scanner.close();
    }
}
```

---

## Ejercicio 2 — Vocales y consonantes

### Descripción

Programa que recibe una palabra y cuenta el número de vocales y consonantes.

### Código

```java
import java.util.Scanner;

public class VocalesConsonantes {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese una palabra: ");
        String palabra = scanner.nextLine();

        int vocales = 0;
        int consonantes = 0;

        for (int i = 0; i < palabra.length(); i++) {

            char letra = palabra.charAt(i);

            if (letra == 'a' ||
                letra == 'e' ||
                letra == 'i' ||
                letra == 'o' ||
                letra == 'u') {

                vocales++;

            } else {
                consonantes++;
            }
        }

        System.out.println("Vocales: " + vocales);
        System.out.println("Consonantes: " + consonantes);

        scanner.close();
    }
}
```

---

## Ejercicio 3 — Invertir una cadena

### Descripción

Programa que recibe una cadena de texto y muestra la cadena invertida.

### Código

```java
import java.util.Scanner;

public class InvertirCadena {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese una cadena: ");
        String texto = scanner.nextLine();

        String invertida = "";

        for (int i = texto.length() - 1; i >= 0; i--) {
            invertida += texto.charAt(i);
        }

        System.out.println("Cadena invertida: " + invertida);

        scanner.close();
    }
}
```

---

# Estructura del proyecto

```text
Taller-Nivelacion-PI-a-PII/
│
├── README.md
│
├── ejercicios/
│   │
│   ├── calculadora/
│   │   └── Calculadora.java
│   │
│   ├── vocales-consonantes/
│   │   └── VocalesConsonantes.java
│   │
│   └── invertir-cadena/
│       └── InvertirCadena.java
│
└── .gitignore
```

# Comandos utilizados

Inicializar Git:

```bash
git init
```

Consultar el estado:

```bash
git status
```

Agregar archivos al staging:

```bash
git add .
```

Crear un commit:

```bash
git commit -m "Agregar ejercicios del taller"
```

Conectar con GitHub:

```bash
git remote add origin URL_DEL_REPOSITORIO
```

Subir los cambios:

```bash
git push origin main
```

# Conclusión

El desarrollo del taller permite reforzar los fundamentos de programación en Java y comprender el funcionamiento básico de Git y GitHub. Los ejercicios prácticos permiten aplicar estructuras de control, variables, cadenas de texto, ciclos y condiciones para resolver problemas sencillos.

Además, el uso de Git permite llevar un control de las diferentes versiones del proyecto y GitHub permite almacenar y compartir el repositorio de forma remota.

![Java](https://img.shields.io/badge/Java-SE-orange?style=flat&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat&logo=mysql)
![NetBeans](https://img.shields.io/badge/NetBeans-IDE-green?style=flat)
![MVC](https://img.shields.io/badge/Patr%C3%B3n-MVC-purple?style=flat)
