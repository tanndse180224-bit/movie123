package hsf302.myMovie.repo;

import hsf302.myMovie.models.Country;
import hsf302.myMovie.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie ,Integer> {
    List<Movie> findByMovieNameContainingIgnoreCase(String movieName);


    List<Movie> findByCountry(Country country);

    
}
