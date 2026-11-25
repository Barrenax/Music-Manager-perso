package es.cifpcesarmanrique.musicmanager.repositories;

import es.cifpcesarmanrique.musicmanager.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    // Artista con sus discos en una sola consulta.
    @Query("select a from Artist a left join fetch a.albums where a.artistId = :id")
    Optional<Artist> findWithAlbumsById(@Param("id") Integer id);
}