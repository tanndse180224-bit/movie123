package hsf302.myMovie.services;

import hsf302.myMovie.models.FavoriteMovie;
import hsf302.myMovie.models.Movie;
import hsf302.myMovie.models.User;
import hsf302.myMovie.repo.FavoriteMovieRepo;
import hsf302.myMovie.repo.MovieRepo;
import hsf302.myMovie.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteMovieService {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteMovieService.class);
    @Autowired
    private FavoriteMovieRepo favoriteMovieRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MovieRepo movieRepo;

    public void addFavorite(int id, int movieId) {
        Optional<User> userOptional = Optional.ofNullable(userRepo.findById(id));

        if (!userOptional.isPresent()) {
            return;
        }

        User user = userOptional.get();

        Optional<Movie> movieOptional = movieRepo.findById(movieId);

        if (!movieOptional.isPresent()) {
            return;
        }

        Movie movie = movieOptional.get();
        favoriteMovieRepo.save(new FavoriteMovie( user, movie));
    }

    public List<Movie> getFavoriteMoviesByUser(User user) {
        List<FavoriteMovie> favoriteMovies = favoriteMovieRepo.findByUser(user);
        return favoriteMovies.stream().map(FavoriteMovie::getMovie).toList();
    }

}
