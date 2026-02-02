package hsf302.myMovie.repo;

import hsf302.myMovie.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre,Integer> {

}
