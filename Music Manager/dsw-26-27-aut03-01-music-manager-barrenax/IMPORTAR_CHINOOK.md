# Importación de Chinook

Archivo incluido en la raíz: `Chinook_MySQL.sql`. Importar en Workbench antes de arrancar la aplicación. ¡ATENCIÓN! Recrea las tablas y puede eliminar registros existentes.

SQL de comprobación:

```sql
USE Chinook;
SELECT COUNT(*) FROM Artist;
SELECT COUNT(*) FROM Album;
SELECT COUNT(*) FROM Track;
SELECT ArtistId, Name FROM Artist WHERE ArtistId IN (155,212);
```

No utilices el script de SQL Server aportado en cursos anteriores. No publiques contraseñas reales.
