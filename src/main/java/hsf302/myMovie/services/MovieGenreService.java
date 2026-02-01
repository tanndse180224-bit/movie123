package hsf302.myMovie.services;

import hsf302.myMovie.models.MovieGenre;
import hsf302.myMovie.repo.MovieGenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieGenreService {

    @Autowired
    private MovieGenreRepo movieGenreRepo;

    public List<MovieGenre> getMovieGenresByMovieId(int movieId) {
        return movieGenreRepo.findByMovieId(movieId);
    }


}
