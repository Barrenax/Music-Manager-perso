package es.cifpcesarmanrique.musicmanager.repositories;

import es.cifpcesarmanrique.musicmanager.models.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    // Carga el artista junto con cada disco para evitar errores de carga perezosa en la vista.
    @Override
    @EntityGraph(attributePaths = "artist")
    Page<Album> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "artist")
    Optional<Album> findById(Integer id);

    // Detalle: disco con su artista y sus canciones en una sola consulta.
    @Query("select a from Album a join fetch a.artist left join fetch a.tracks where a.albumId = :id")
    Optional<Album> findDetailById(@Param("id") Integer id);
}
