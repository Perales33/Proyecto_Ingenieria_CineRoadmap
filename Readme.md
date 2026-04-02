# 🎬 CineRoadmap – El reto final para los cinéfilos

**CineRoadmap** es una plataforma web gamificada que permite a los amantes del cine registrar las películas que han visto, desbloquear insignias y completar desafíos cinematográficos. Inspirada en los logros de videojuegos, promueve el descubrimiento fílmico y el espíritu comunitario entre cinéfilos.

---

## 📌 Tabla de Contenidos

* [Características principales](#caracteristicas_principales)
* [Perfil y comunidad](#perfil_y_comunidad)
* [Tecnologías utilizadas](#tecnologias_utilizadas)
* [Instalación y ejecución](#instalacion_y_ejecucion)
* [Estructura del proyecto](#estructura_del_proyecto)
* [Próximos pasos](#proximos_pasos)
* [Licencia](#licencia)

---

## 🚀 Características principales

* Registro y consulta de películas vistas
* Sistema de insignias por logros cinematográficos
* Desafíos diarios/semanales
* Perfil con estadísticas, rachas y horas de visualización
* Comparación con amigos y comunidad

---

## 👤 Perfil y comunidad

* Lista de películas vistas
* Insignias desbloqueadas
* Estadísticas cinéfilas:

  * Películas totales
  * Racha más larga
  * Mes más productivo
  * Total de horas
* Compartir progreso con otros usuarios

---

## 🛠️ Tecnologías utilizadas

### 🌐 Desarrollo Web (principal)

* **Backend**: PHP
* **Frontend**:

  * HTML5
  * CSS3
  * JavaScript
* **Servidor local**: MAMP
* **Base de datos**: MySQL
* **Configuración del servidor**:

  * `.htaccess`

### ☕ Aplicación complementaria en Java

* Ubicación: `/gui/src/main/app/`
* Arquitectura MVC:

  * `Modelo`
  * `Vista`
  * `Controlador`
* IDE recomendado:

  * IntelliJ IDEA / Eclipse
* Diagramas:

  * PlantUML

---

## ⚙️ Instalación y ejecución

### 🔧 Requisitos

* MAMP
* PHP 7.4+
* MySQL
* Navegador web
* Java 11+ (opcional)

---

### 📦 Instalación

1. Clona el repositorio:

```bash
git clone git@github.com:Perales33/Proyecto_Ingenieria_CineRoadmap.git
cd Proyecto_Ingenieria_CineRoadmap
```

---

2. Copia el proyecto al servidor local:

```bash
/Applications/MAMP/htdocs/
```

---

3. Configura la base de datos:

* Accede a **phpMyAdmin**
* Crea una base de datos (ej: `cineroadmap`)
* Importa los scripts desde:

```
/app/sql/
```

---

4. Configura la conexión a la base de datos:

Archivo:

```
/app/initdb.php
```

Ejemplo:

```php
$host = "localhost";
$user = "root";
$password = "root";
$database = "cineroadmap";
```

---

5. Poblar la base de datos con películas (API TMDB)

Para cargar datos iniciales de películas, ejecuta el script:

```
/app/prueba.php
```

Este archivo realiza una conexión con la API de TMDb y almacena las películas en la base de datos automáticamente.

---

6. Ejecuta la aplicación web:

```
http://localhost/Proyecto_Ingenieria_CineRoadmap/app/
```

---

## 📁 Estructura del proyecto

```bash
Proyecto_Ingenieria_CineRoadmap/
├── app/                            # --- Aplicación Web (PHP) ---
│   ├── actions/                    # Acciones (loginPost, logoutPost, etc.)
│   ├── components/                 # Componentes reutilizables de UI
│   ├── css/                        # Estilos (CSS)
│   ├── js/                         # Scripts (JavaScript)
│   ├── img/                        # Recursos gráficos
│   ├── fonts/                      # Fuentes tipográficas (ej: Montserrat)
│   ├── includes/                   # Componentes reutilizables (header, footer, menús)
│   ├── error/                      # Páginas de error (404, acceso denegado, etc.)
│   ├── fncdocs/                    # Documentos de prueba de conexión a la BBDD
│   ├── sql/                        # Scripts y backups de la base de datos
│   ├── initdb.php                  # Conexión a la base de datos
│   ├── prueba.php                  # Script de carga de películas desde TMDB
│   ├── *.php                       # Vistas (login, perfil, películas, etc.)
│   └── .htaccess                   # Configuración del servidor
│
├── gui/                            # --- Aplicación Java ---
│   └── src/main/app/
│       ├── Controlador/            # Lógica de control
│       ├── Modelo/                 # Entidades y datos
│       ├── Vista/                  # Interfaces gráficas
│       └── Main.java               # Clase principal
│
├── LICENSE
└── Readme.md
```

---

## 🔮 Próximos pasos

* Migración completa del backend a Java
* Desarrollo de aplicación móvil (Android / iOS)
* Integración avanzada con APIs externas
* Sistema de recomendaciones inteligentes
* Notificaciones en tiempo real
* Sincronización entre dispositivos

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.