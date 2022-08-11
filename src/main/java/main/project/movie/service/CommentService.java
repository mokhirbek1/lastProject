package main.project.movie.service;

import main.project.movie.entity.Comment;
import main.project.movie.exception.ServiceException;

import java.util.List;

public interface CommentService extends BaseService<Comment> {
    List<Comment> findAllCommentByMovieId(long id) throws ServiceException;

}
