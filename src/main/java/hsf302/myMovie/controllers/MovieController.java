package hsf302.myMovie.controllers;

import hsf302.myMovie.models.*;
import hsf302.myMovie.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieGenreService movieGenreService;
    @Autowired
    private GenreService genreService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private FavoriteMovieService favoriteMovieService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/home")
    public String getAllMovies(Model model , HttpSession session) {
        List<Movie> movies = movieService.getAllMovies();
        List<Genre> genres = genreService.getAllGenres();
        List<Country> countries = countryService.getAllCountries();


        model.addAttribute("countries", countries);
        model.addAttribute("genres", genres);
        model.addAttribute("movies", movies);
//        User loggedAcc = (User) session.getAttribute("acc");
//        model.addAttribute("role", loggedAcc.getRole());
        return "home";
    }

    @GetMapping("/manage-movies")
    public String getAllMoviesList(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    @GetMapping("/getmoviebyid")
    public String getMovieById(@RequestParam(name = "id") int id, Model model) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);

        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            model.addAttribute("movie", movie);

            List<MovieGenre> movieGenres = movieGenreService.getMovieGenresByMovieId(movie.getId());
            model.addAttribute("movieGenres", movieGenres);
            List<Comment> comments = commentService.getAllCommentsByMovie(movieOptional.get().getId());
            model.addAttribute("comments", comments);
                return "detail";
        }

        return "redirect:/movies"; // Nếu không tìm thấy phim, chuyển hướng về danh sách phim
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Movie movieEmpty = new Movie();
        model.addAttribute("movie", movieEmpty);
        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);
        return "movie-form";
    }

    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/movies/manage-movies";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Movie> movie = movieService.getMovieById(id);
        movie.ifPresent(value -> model.addAttribute("movie", value));
        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);
        return "movie-form";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable int id, @ModelAttribute Movie movie) {
        movie.setId(id);
        movieService.saveMovie(movie);
        return "redirect:/movies/manage-movies";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
        return "redirect:/movies/manage-movies";
    }
    @GetMapping
    public String getMoviesByName(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Movie> movies;
        if (keyword != null && !keyword.isEmpty()) {
            movies = movieService.getMovieByName(keyword);
        } else {
            movies = movieService.getAllMovies();
        }

        List<Genre> genres = genreService.getAllGenres();
        List<Country> countries = countryService.getAllCountries();

        model.addAttribute("countries", countries);
        model.addAttribute("genres", genres);



        model.addAttribute("movies", movies);
        model.addAttribute("keyword", keyword);
        return "home";
    }

    @GetMapping("/watch")
    public String watchMovie(@RequestParam(name = "id") int id, Model model) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);

        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            model.addAttribute("movie", movie);
            return "watch"; // Chuyển hướng đến trang xem phim
        }

        return "redirect:/movies"; // Nếu không tìm thấy phim, quay về danh sách phim
    }

    @PostMapping("/addToFavorites")
    public String addToFavorites(@RequestParam("movieId") int movieId, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("acc");

        if (user == null) { // Kiểm tra nếu chưa đăng nhập
            redirectAttributes.addFlashAttribute("error", "Please log in to add favorites.");
            return "redirect:/login";
        }

        favoriteMovieService.addFavorite(user.getId(), movieId); // Sử dụng user.getId() thay vì userId chưa khai báo
        redirectAttributes.addFlashAttribute("success", "Movie added to favorites!");

        return "redirect:/movies/getmoviebyid?id=" + movieId;
    }

    @GetMapping("/favorites")
    public String viewFavoriteMovies(HttpSession session, Model model) {
        User user = (User) session.getAttribute("acc");



        if (user == null) {
            return "redirect:/login";
        }

        List<Movie> favoriteMovies = favoriteMovieService.getFavoriteMoviesByUser(user);
        model.addAttribute("movies", favoriteMovies);
        return "favorite_movies"; // Trả về trang hiển thị phim yêu thích
    }
    @GetMapping("/details")
    public String movieDetails(@RequestParam int id, Model model) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        if (movieOptional.isEmpty()) {
            return "redirect:/movies/home";
        }

        Movie movie = movieOptional.get();
        List<Comment> comments = commentService.getAllCommentsByMovie(id);

        model.addAttribute("movie", movie);
        model.addAttribute("comments", comments);
        return "detail"; // Trả về trang detail.html
    }
}
