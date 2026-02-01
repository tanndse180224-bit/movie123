package hsf302.myMovie.controllers;

import hsf302.myMovie.models.Genre;
import hsf302.myMovie.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String getAllGenres(Model model) {
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genres/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "genres/create";
    }

    @PostMapping("/save")
    public String saveGenre(@ModelAttribute Genre genre) {
        genreService.saveGenre(genre);
        return "redirect:/genres";
    }


    @GetMapping("/delete/{id}")
    public String deleteGenre(@PathVariable int id) {
        genreService.deleteGenre(id);
        return "redirect:/genres";
    }
}
