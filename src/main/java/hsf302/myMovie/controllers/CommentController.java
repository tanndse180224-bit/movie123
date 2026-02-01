package hsf302.myMovie.controllers;

import hsf302.myMovie.models.Comment;
import hsf302.myMovie.models.Movie;
import hsf302.myMovie.models.User;
import hsf302.myMovie.services.CommentService;
import hsf302.myMovie.services.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final MovieService movieService;

    @Autowired
    public CommentController(CommentService commentService, MovieService movieService) {
        this.commentService = commentService;
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public String addComment(@RequestParam int movieId, @RequestParam String content, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("acc");
        if (user == null) {
            return "redirect:/login";
        }

        if (content == null || content.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Comment cannot be empty.");
            return "redirect:/movies/details?id=" + movieId;
        }

        Movie movie = movieService.getMovieById(movieId).orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Comment comment = new Comment();
        comment.setFullName(user.getFullName());
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setMovie(movie);

        commentService.addComment(comment);
        redirectAttributes.addFlashAttribute("success", "Comment added successfully.");

        return "redirect:/movies/details?id=" + movieId;
    }

}
