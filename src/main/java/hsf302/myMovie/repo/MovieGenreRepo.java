package hsf302.myMovie.repo;

import hsf302.myMovie.models.Movie;
import hsf302.myMovie.models.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieGenreRepo extends JpaRepository<MovieGenre ,Integer> {

    List<MovieGenre> findByMovieId(int movieId);

}
