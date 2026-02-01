package hsf302.myMovie.repo;

import hsf302.myMovie.models.FavoriteMovie;
import hsf302.myMovie.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteMovieRepo extends JpaRepository<FavoriteMovie,Integer> {
    List<FavoriteMovie> findByUser(User user);

}
