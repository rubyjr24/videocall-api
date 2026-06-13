# 🚀 CALLIO

Breve descripción del proyecto: qué hace, cuál es su propósito principal y qué tecnología utiliza (Spring Boot, Java 25).

---

## 🛠️ Requisitos Previos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

* **IntelliJ IDEA** (Versión 2025.1 o superior recomendada para soporte nativo de Java 25).
* **Git** instalado en tu sistema.
* **Maven** instalado en tu sistema.

---

## 🗄️ Configuración de la Base de Datos

Para que la API funcione correctamente, es necesario inicializar el esquema en tu servidor de base de datos local:

1. Asegúrate de que tu servicio de base de datos esté activo.
2. Ejecuta el archivo `structure.sql` localizado en la carpeta `/src/main/resources/sql/` para crear el esquema, todas las tablas y relaciones necesarias.

> **Nota:** No olvides actualizar las credenciales de conexión (usuario, contraseña y URL) en el archivo `db.properties` antes de ejecutar la aplicación.

---

## ☕ Instalación de JDK 25 en IntelliJ IDEA

Para trabajar con Java 25, es necesario configurarlo correctamente en tu IDE:

1. Abre **IntelliJ IDEA**.
2. Ve a `File` > `Project Structure` (o presiona `Ctrl + Alt + Shift + S`).
3. En la columna de la izquierda, selecciona **SDKs**.
4. Haz clic en el botón **+** y selecciona **Download JDK...**.
5. En la ventana emergente:
    * **Version:** Selecciona `25`.
    * **Vendor:** Elige el de tu preferencia (ej. Eclipse Temurin o Microsoft).
    * **Location:** Deja la ruta por defecto.
6. Haz clic en **Download**.
7. Una vez finalizado, ve a **Project** (en la misma ventana de *Project Structure*) y asegúrate de que el **SDK** esté configurado en `25` y el **Language level** en `25`.

---

## 📦 Descarga de Dependencias y Ejecución

Para levantar la API, sigue estos pasos desde la terminal de IntelliJ o tu terminal favorita en la raíz del proyecto:

### 1. Descarga de dependencias
El proyecto gestionará automáticamente las dependencias. Ejecuta el siguiente comando según tu gestor:

    ```bash
    mvn clean install
    ```

### 2. Ejecución de la API
Una vez compilado, inicia el servidor con:

    ```bash
    mvn spring-boot:run


> **Nota:** Por defecto, la API estará disponible en `http://localhost:5050`. Puedes verificar que está activa accediendo a esa dirección desde tu navegador o usando herramientas como Postman.

---