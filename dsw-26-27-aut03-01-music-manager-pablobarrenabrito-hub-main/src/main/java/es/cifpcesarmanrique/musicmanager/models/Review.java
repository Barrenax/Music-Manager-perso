package es.cifpcesarmanrique.musicmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewId")
    private Integer reviewId;

    @NotBlank(message = "El título es obligatorio.")
    @Size(min = 5, max = 30, message = "El título debe tener entre 5 y 30 caracteres.")
    @Column(name = "Title", nullable = false, length = 10)
    private String title;

    @NotBlank(message = "El comentario es obligatorio.")
    @Size(min = 5, max = 30, message = "El comentario debe tener entre 5 y 30 caracteres.")
    @Column(name = "Description", nullable = false, length = 30)
    private String description;

    @NotNull(message = "La valoración es obligatoria.")
    @Min(value = 1, message = "La valoración mínima es 1.")
    @Max(value = 5, message = "La valoración máxima es 5.")
    @Column(name = "Rating", nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ArtistId", nullable = false)
    private Artist artist;

    public Integer getReviewId() { return reviewId; }
    public void setReviewId(Integer reviewId) { this.reviewId = reviewId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }
}