package hsf302.myMovie.services;

import hsf302.myMovie.models.Comment;
import hsf302.myMovie.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private final CommentRepo commentRepository;

    @Autowired
    public CommentService(CommentRepo commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsByMovie(int movieId) {
        return commentRepository.findByMovieId(movieId);
    }
}
