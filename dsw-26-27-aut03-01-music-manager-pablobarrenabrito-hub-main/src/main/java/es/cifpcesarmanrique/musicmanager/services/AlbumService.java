package es.cifpcesarmanrique.musicmanager.services;

import es.cifpcesarmanrique.musicmanager.exceptions.ResourceNotFoundException;
import es.cifpcesarmanrique.musicmanager.models.Album;
import es.cifpcesarmanrique.musicmanager.repositories.AlbumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbumService {

    private static final int PAGE_SIZE = 15;

    private final AlbumRepository albumRepository;
    private final ArtistService artistService;

    public AlbumService(AlbumRepository albumRepository, ArtistService artistService) {
        this.albumRepository = albumRepository;
        this.artistService = artistService;
    }

    /** Página de 15 discos ordenados por título descendente (el id desempata para paginar de forma estable). */
    @Transactional(readOnly = true)
    public Page<Album> findPage(int page) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), PAGE_SIZE,
                Sort.by(Sort.Order.desc("title"), Sort.Order.asc("albumId")));
        return albumRepository.findAll(pageable);
    }

    /** Disco con su artista y sus canciones. */
    @Transactional(readOnly = true)
    public Album getDetail(Integer id) {
        return albumRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el disco con id " + id));
    }

    /** Disco con su artista (para el formulario de edición). */
    @Transactional(readOnly = true)
    public Album getById(Integer id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el disco con id " + id));
    }

    @Transactional
    public Album create(Album album) {
        album.setAlbumId(null);
        album.setArtist(artistService.getById(album.getArtist().getArtistId()));
        return albumRepository.save(album);
    }

    @Transactional
    public Album update(Integer id, Album data) {
        Album existing = getById(id);
        existing.setTitle(data.getTitle());
        existing.setArtist(artistService.getById(data.getArtist().getArtistId()));
        return albumRepository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Album album = getById(id);
        albumRepository.delete(album);
        albumRepository.flush(); // fuerza aquí el error si la base de datos impide el borrado
    }

    /** Crea un disco asociado a un artista concreto (el de la URL, no el del formulario). */
    @Transactional
    public Album createForArtist(Integer artistId, Album album) {
        album.setAlbumId(null);
        album.setArtist(artistService.getById(artistId));
        return albumRepository.save(album);
    }
}