package es.cifpcesarmanrique.musicmanager.config;

import es.cifpcesarmanrique.musicmanager.services.ReviewService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** Carga reseñas de ejemplo al arrancar; no duplica si ya existen. */
@Component
public class SampleDataLoader implements ApplicationRunner {

    private final ReviewService reviewService;

    public SampleDataLoader(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public void run(ApplicationArguments args) {
        reviewService.createSampleIfAbsent(155, "Genial", "Un artista muy recomendable", 5);
        reviewService.createSampleIfAbsent(212, "Notable", "Buenas canciones y directos", 4);
    }
}