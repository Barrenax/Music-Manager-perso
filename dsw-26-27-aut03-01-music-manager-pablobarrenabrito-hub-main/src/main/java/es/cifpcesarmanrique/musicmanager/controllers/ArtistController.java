package es.cifpcesarmanrique.musicmanager.controllers;

import es.cifpcesarmanrique.musicmanager.models.Album;
import es.cifpcesarmanrique.musicmanager.models.Artist;
import es.cifpcesarmanrique.musicmanager.services.AlbumService;
import es.cifpcesarmanrique.musicmanager.services.ArtistService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import es.cifpcesarmanrique.musicmanager.models.Review;
import es.cifpcesarmanrique.musicmanager.services.ReviewService;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final ReviewService reviewService;

    public ArtistController(ArtistService artistService, AlbumService albumService, ReviewService reviewService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("artists", artistService.findPage(page));
        return "artists/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("artist", artistService.getById(id));
        model.addAttribute("reviews", reviewService.findByArtist(id));
        return "artists/detail";
    }

    @GetMapping("/{id}/albums")
    public String albums(@PathVariable Integer id, Model model) {
        model.addAttribute("artist", artistService.getWithAlbums(id));
        return "artists/albums";
    }

    // ---------- Crear ----------

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("artist", new Artist());
        fillFormModel(model, "Crear Artista", "/artists", "Crear");
        return "artists/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("artist") Artist artist, BindingResult result,
                         Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            fillFormModel(model, "Crear Artista", "/artists", "Crear");
            return "artists/form";
        }
        artistService.create(artist);
        redirectAttributes.addFlashAttribute("message", "Artista creado correctamente.");
        return "redirect:/artists";
    }

    // ---------- Editar ----------

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("artist", artistService.getById(id));
        fillFormModel(model, "Editar Artista", "/artists/" + id + "/edit", "Guardar");
        return "artists/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("artist") Artist artist,
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            fillFormModel(model, "Editar Artista", "/artists/" + id + "/edit", "Guardar");
            return "artists/form";
        }
        artistService.update(id, artist);
        redirectAttributes.addFlashAttribute("message", "Artista actualizado correctamente.");
        return "redirect:/artists";
    }

    // ---------- Borrar (el GET solo pide confirmación; el borrado es por POST) ----------

    @GetMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        model.addAttribute("artist", artistService.getById(id));
        return "artists/delete";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            artistService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Artista eliminado correctamente.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede eliminar el artista porque tiene datos asociados.");
        }
        return "redirect:/artists";
    }

    // ---------- Crear un disco asociado al artista ----------

    @GetMapping("/{id}/albums/new")
    public String newAlbumForm(@PathVariable Integer id, Model model) {
        model.addAttribute("artist", artistService.getById(id));
        model.addAttribute("album", new Album());
        return "artists/album-form";
    }

    @PostMapping("/{id}/albums/new")
    public String createAlbum(@PathVariable Integer id, @Valid @ModelAttribute("album") Album album,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("artist", artistService.getById(id));
            return "artists/album-form";
        }
        albumService.createForArtist(id, album);
        redirectAttributes.addFlashAttribute("message", "Disco creado correctamente.");
        return "redirect:/artists/" + id + "/albums";
    }

    // ---------- Comentarios (reseñas) del artista ----------

    @GetMapping("/{id}/reviews/new")
    public String newReviewForm(@PathVariable Integer id, Model model) {
        Review review = new Review();
        review.setRating(1);
        model.addAttribute("artist", artistService.getById(id));
        model.addAttribute("review", review);
        return "artists/review-form";
    }

    @PostMapping("/{id}/reviews/new")
    public String createReview(@PathVariable Integer id, @Valid @ModelAttribute("review") Review review,
                               BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("artist", artistService.getById(id));
            return "artists/review-form";
        }
        reviewService.createForArtist(id, review);
        redirectAttributes.addFlashAttribute("message", "Comentario creado correctamente.");
        return "redirect:/artists/" + id;
    }

    private void fillFormModel(Model model, String formTitle, String formAction, String submitLabel) {
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("submitLabel", submitLabel);
    }
}