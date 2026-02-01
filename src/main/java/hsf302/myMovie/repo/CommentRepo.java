package hsf302.myMovie.repo;

import hsf302.myMovie.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByMovieId(int movieId);
}
