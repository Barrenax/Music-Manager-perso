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

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final ArtistService artistService;

    public AlbumController(AlbumService albumService, ArtistService artistService) {
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("albums", albumService.findPage(page));
        return "albums/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("album", albumService.getDetail(id));
        return "albums/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Album album = new Album();
        album.setArtist(new Artist());
        model.addAttribute("album", album);
        fillFormModel(model, "Crear", "/albums", "Crear");
        return "albums/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        model.addAttribute("album", albumService.getById(id));
        return "albums/delete";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("album") Album album, BindingResult result,
                         Model model, RedirectAttributes redirectAttributes) {
        validateArtist(album, result);
        if (result.hasErrors()) {
            fillFormModel(model, "Crear", "/albums", "Crear");
            return "albums/form";
        }
        albumService.create(album);
        redirectAttributes.addFlashAttribute("message", "Disco creado correctamente.");
        return "redirect:/albums";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("album", albumService.getById(id));
        fillFormModel(model, "Editar Album", "/albums/" + id + "/edit", "Guardar");
        return "albums/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("album") Album album,
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        validateArtist(album, result);
        if (result.hasErrors()) {
            fillFormModel(model, "Editar Album", "/albums/" + id + "/edit", "Guardar");
            return "albums/form";
        }
        albumService.update(id, album);
        redirectAttributes.addFlashAttribute("message", "Disco actualizado correctamente.");
        return "redirect:/albums";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            albumService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Disco eliminado correctamente.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede eliminar el disco porque tiene datos asociados.");
        }
        return "redirect:/albums";
    }

    private void fillFormModel(Model model, String formTitle, String formAction, String submitLabel) {
        model.addAttribute("artists", artistService.findAllOrderedByName());
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("submitLabel", submitLabel);
    }

    /** El artista llega como artist.artistId desde el desplegable; si viene vacío, error en español. */
    private void validateArtist(Album album, BindingResult result) {
        if (album.getArtist() == null) {
            album.setArtist(new Artist());
        }
        if (album.getArtist().getArtistId() == null) {
            result.rejectValue("artist", "artist.required", "Debes seleccionar un artista.");
        }
    }
}