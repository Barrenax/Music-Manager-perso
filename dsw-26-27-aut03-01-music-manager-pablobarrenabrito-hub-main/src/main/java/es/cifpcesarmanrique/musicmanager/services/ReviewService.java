package es.cifpcesarmanrique.musicmanager.services;

import es.cifpcesarmanrique.musicmanager.models.Review;
import es.cifpcesarmanrique.musicmanager.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ArtistService artistService;

    public ReviewService(ReviewRepository reviewRepository, ArtistService artistService) {
        this.reviewRepository = reviewRepository;
        this.artistService = artistService;
    }

    @Transactional(readOnly = true)
    public List<Review> findByArtist(Integer artistId) {
        return reviewRepository.findByArtistArtistIdOrderByReviewIdDesc(artistId);
    }

    /** Crea una reseña asociada al artista de la URL (lanza ResourceNotFoundException si no existe). */
    @Transactional
    public Review createForArtist(Integer artistId, Review review) {
        review.setReviewId(null);
        review.setArtist(artistService.getById(artistId));
        return reviewRepository.save(review);
    }

    /** Crea una reseña de ejemplo solo si el artista existe y todavía no tiene reseñas. */
    @Transactional
    public void createSampleIfAbsent(Integer artistId, String title, String description, int rating) {
        if (reviewRepository.existsByArtistArtistId(artistId) || !artistService.existsById(artistId)) {
            return;
        }
        Review review = new Review();
        review.setTitle(title);
        review.setDescription(description);
        review.setRating(rating);
        createForArtist(artistId, review);
    }
}