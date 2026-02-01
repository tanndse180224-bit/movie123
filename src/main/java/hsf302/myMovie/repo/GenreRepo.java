package hsf302.myMovie.repo;

import hsf302.myMovie.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepo extends JpaRepository<Genre,Integer> {

}
