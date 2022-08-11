package main.project.movie.service.impl;

import main.project.movie.dao.impl.CommentDaoImpl;
import main.project.movie.entity.Comment;
import main.project.movie.exception.DaoException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.CommentService;
import static main.project.movie.controller.message.LogMessage.*;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
    private static CommentServiceImpl instance = new CommentServiceImpl();


    public static CommentServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Comment comment) throws ServiceException {
        try {
            return commentDao.insert(comment);
        } catch (DaoException e) {
            logger.error(ERROR_INSERTING_COMMENT_LOG, e);
            throw new ServiceException(ERROR_INSERTING_COMMENT_LOG, e);
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return commentDao.delete(id);
        } catch (DaoException e) {
            logger.error(ERROR_DELETING_COMMENT_LOG, e);
            throw new ServiceException(ERROR_DELETING_COMMENT_LOG, e);
        }
    }

    @Override
    public List<Comment> findAllCommentByMovieId(long id) throws ServiceException {
        try {
            return commentDao.findAllCommentByMovieId(id);
        } catch (DaoException e) {
            logger.error(ERROR_FINDING_ALL_COMMENT_LOG, e);
            throw new ServiceException(ERROR_FINDING_ALL_COMMENT_LOG, e);
        }
    }

    @Override
    public boolean update(Comment comment) throws ServiceException {
        try {
            return commentDao.update(comment);
        } catch (DaoException e) {
            logger.error(ERROR_UPDATING_COMMENT_LOG, e);
            throw new ServiceException(ERROR_UPDATING_COMMENT_LOG, e);
        }
    }
}
