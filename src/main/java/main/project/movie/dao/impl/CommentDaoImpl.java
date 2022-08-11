package main.project.movie.dao.impl;

import main.project.movie.dao.CommentDao;
import main.project.movie.dao.converter.impl.ConvertToComment;
import main.project.movie.entity.Comment;
import main.project.movie.exception.DaoException;
import main.project.movie.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.dao.query.DaoQuery.*;

public class CommentDaoImpl implements CommentDao {
    private static final CommentDaoImpl instance = new CommentDaoImpl();
    private final ConvertToComment convertToComment = new ConvertToComment();

    private CommentDaoImpl() {
    }

    public static CommentDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_DELETING_COMMENT_LOG,e);
            throw new DaoException(ERROR_DELETING_COMMENT_LOG, e);
        }
    }

    @Override
    public boolean insert(Comment comment) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENT)) {
            preparedStatement.setString(1, comment.getComment_text());
            preparedStatement.setInt(2, comment.getMovie_id());
            preparedStatement.setInt(3, comment.getUser_id());
            preparedStatement.setString(4, comment.getUsername());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_INSERTING_COMMENT_LOG, e);
            throw new DaoException(ERROR_INSERTING_COMMENT_LOG, e);
        }
    }


    @Override
    public List<Comment> findAllCommentByMovieId(long id) throws DaoException {
        List<Comment> commentList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_COMMENT_BY_MOVIE_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                Optional<Comment> optionalComment = convertToComment.convert(resultSet);
                optionalComment.ifPresent(commentList::add);
            }}
            return commentList;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_ALL_COMMENT_LOG, e);
            throw new DaoException(ERROR_FINDING_ALL_COMMENT_LOG, e);
        }
    }

    @Override
    public boolean update(Comment comment) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMMENT)) {
            preparedStatement.setString(1, comment.getComment_text());
            preparedStatement.setInt(2, comment.getId());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_UPDATING_COMMENT_LOG, e);
            throw new DaoException(ERROR_UPDATING_COMMENT_LOG, e);
        }
    }
}
