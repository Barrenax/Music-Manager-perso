package es.cifpcesarmanrique.musicmanager.repositories;

import es.cifpcesarmanrique.musicmanager.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Reseñas de un artista, las más recientes primero.
    List<Review> findByArtistArtistIdOrderByReviewIdDesc(Integer artistId);

    boolean existsByArtistArtistId(Integer artistId);
}