package es.cifpcesarmanrique.musicmanager.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Track")
public class Track {

    @Id
    @Column(name = "TrackId")
    private Integer trackId;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AlbumId")
    private Album album;

    @Column(name = "Composer", length = 220)
    private String composer;

    @Column(name = "Milliseconds", nullable = false)
    private Integer milliseconds;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    /** Duración en formato m:ss para mostrar en las vistas. */
    public String getFormattedDuration() {
        if (milliseconds == null) {
            return "-";
        }
        long totalSeconds = milliseconds / 1000;
        return String.format("%d:%02d", totalSeconds / 60, totalSeconds % 60);
    }

    public Integer getTrackId() { return trackId; }
    public void setTrackId(Integer trackId) { this.trackId = trackId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }
    public String getComposer() { return composer; }
    public void setComposer(String composer) { this.composer = composer; }
    public Integer getMilliseconds() { return milliseconds; }
    public void setMilliseconds(Integer milliseconds) { this.milliseconds = milliseconds; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}