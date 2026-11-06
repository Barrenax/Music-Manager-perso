# AUT03_01 — Music Manager

Actividad evaluable individual · DSW · UT03 · 2.º DAW · Curso 2026–2027

## Plazos

- Publicación: **viernes 6 de noviembre de 2026**.
- Entrega ordinaria: **viernes 13 de noviembre de 2026**.
- Entregas posteriores: permitidas y marcadas como fuera de plazo.

## Objetivo

Construir una aplicación MVC con Spring Boot, Thymeleaf, Spring Data JPA, MySQL y validaciones Java para gestionar artistas, discos, canciones asociadas y reseñas.
El comportamiento de la aplicación ha de ser el que se muestra en el siguiente [video](https://drive.google.com/file/d/1J-X5BhZJhUrvGq-stXjjwTYekeCmJB75/view?usp=sharing)

## Arranque

1. Clona **tu repositorio individual** de Classroom 50 en IntelliJ IDEA con Java 21.
2. Abre **`Chinook_MySQL.sql`** desde el paquete que facilita el docente e impórtalo en MySQL Workbench (el script recrea Chinook, por lo que elimina datos anteriores).
3. Ejecuta el archivo **`comprobar_importacion.sql`**, facilitado por el docente en EVAGD, y comprueba que existen `Artist`, `Album` y `Track`.
4. Define las variables de entorno `DB_URL`, `DB_USER` y `DB_PASSWORD` en la configuración Run de IntelliJ; no subas contraseñas al repositorio. Por defecto apunta a MySQL local, BD `Chinook`.
5. Ejecuta `MusicManagerApplication`. Abre `http://localhost:8080/`. Las rutas `/albums` y `/artists` quedarán pendientes hasta que implementes sus controladores.

## Organización

Trabaja con paquetes `models`, `repositories`, `services`, `controllers` y `templates`. Clases y atributos en **inglés**; textos de interfaz en **español**. Conserva la barra `nav` y utiliza Bootstrap de forma coherente.

## Funcionalidades

- Inicio: navegación a Discos, Artistas y Login (en construcción).
- Discos: mostrar 15 por página/consulta, ordenados por `Title` descendente, CRUD completo y detalle con sus canciones.
- Artistas: mostrar 15 ordenados por `Name` descendente, CRUD completo, detalle, lista de discos del artista y alta de disco asociado.
- Reseñas: entidad `Review` asociada a `Artist`, con título entre 3–10, descripción 5–30 y valoración 1–5; mostrar y crear reseñas desde el detalle de artista, sin CRUD independiente. Añadir datos de ejemplo a artistas ID 155 y 212.
- Validar datos desde backend (`@Valid`, `BindingResult`), mostrar errores en el formulario, gestionar IDs inexistentes y evitar eliminar mediante GET.

## Entrega en EVAGD

Adjunta **el enlace al repositorio individual de Classroom 50** y `Informe_MusicManager_ApellidoNombre.pdf` con resumen técnico, problemas encontrados y conclusiones. Para el examen, deja también una copia ZIP del código o un archivo de evidencia del estado entregado en EVAGD, conforme a las instrucciones de la tarea.

El proyecto inicial **no incluye los CRUD resueltos**. No copies código ajeno. Commits propios y frecuentes.

Consulta el PDF del enunciado y la rúbrica publicada en EVAGD: prevalecen si se produjera una discrepancia.
