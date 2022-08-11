package main.project.movie.dao;

import main.project.movie.entity.Comment;
import main.project.movie.exception.DaoException;

import java.util.List;

public interface CommentDao extends BaseDao<Comment>{
    List<Comment> findAllCommentByMovieId(long id) throws DaoException;
}
