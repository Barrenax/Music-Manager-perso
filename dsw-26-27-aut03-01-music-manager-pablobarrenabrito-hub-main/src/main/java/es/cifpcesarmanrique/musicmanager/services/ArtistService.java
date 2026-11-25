package es.cifpcesarmanrique.musicmanager.services;

import es.cifpcesarmanrique.musicmanager.exceptions.ResourceNotFoundException;
import es.cifpcesarmanrique.musicmanager.models.Artist;
import es.cifpcesarmanrique.musicmanager.repositories.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public List<Artist> findAllOrderedByName() {
        return artistRepository.findAll(Sort.by("name"));
    }

    @Transactional(readOnly = true)
    public Artist getById(Integer id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el artista con id " + id));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return artistRepository.existsById(id);
    }

    private static final int PAGE_SIZE = 15;

    /** Página de 15 artistas ordenados por nombre descendente (el id desempata). */
    @Transactional(readOnly = true)
    public Page<Artist> findPage(int page) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), PAGE_SIZE,
                Sort.by(Sort.Order.desc("name"), Sort.Order.asc("artistId")));
        return artistRepository.findAll(pageable);
    }

    /** Artista con la lista de sus discos. */
    @Transactional(readOnly = true)
    public Artist getWithAlbums(Integer id) {
        return artistRepository.findWithAlbumsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el artista con id " + id));
    }

    @Transactional
    public Artist create(Artist artist) {
        artist.setArtistId(null);
        return artistRepository.save(artist);
    }

    @Transactional
    public Artist update(Integer id, Artist data) {
        Artist existing = getById(id);
        existing.setName(data.getName());
        return artistRepository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Artist artist = getById(id);
        artistRepository.delete(artist);
        artistRepository.flush(); // fuerza aquí el error si la base de datos impide el borrado
    }
}


