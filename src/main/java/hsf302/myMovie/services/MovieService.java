package hsf302.myMovie.services;

import hsf302.myMovie.models.Country;
import hsf302.myMovie.models.Movie;
import hsf302.myMovie.repo.CountryRepo;
import hsf302.myMovie.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepository;
    @Autowired
    private CountryRepo countryRepo;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public List<Movie> getMovieByName(String name) {
        return movieRepository.findByMovieNameContainingIgnoreCase(name);
    }

    public Optional<Movie> getMovieById(int id) {
        return movieRepository.findById(id);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }


    public List<Movie> getMoviesByCountryId(int id) {
        Optional<Country> countryOptional = countryRepo.findById(id);
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            return movieRepository.findByCountry(country);
        }
        return null;
    }
}
