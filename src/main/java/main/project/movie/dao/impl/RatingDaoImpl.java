package main.project.movie.dao.impl;

import main.project.movie.dao.RatingDao;
import main.project.movie.dao.converter.impl.ConvertToRating;
import main.project.movie.entity.Rating;
import main.project.movie.exception.DaoException;
import main.project.movie.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.dao.query.DaoQuery.*;

public class RatingDaoImpl implements RatingDao {
    private ConvertToRating convertToRating = new ConvertToRating();
    private static RatingDaoImpl instance = new RatingDaoImpl();

    public static RatingDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Rating rating) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_RATING_QUERY)) {
            statement.setInt(1, rating.getMovie_id());
            statement.setInt(2, rating.getValue());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_INSERTING_RATING_LOG, e);
            throw new DaoException(ERROR_INSERTING_RATING_LOG, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RATING_BY_ID)) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_DELETING_RATING_LOG, e);
            throw new DaoException(ERROR_DELETING_RATING_LOG, e);
        }
    }

    @Override
    public boolean update(Rating rating) throws DaoException {
        int value = rating.getValue();
        int movie_id = rating.getMovie_id();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ratingStatement = connection.prepareStatement(UPDATE_VALUE)) {
            ratingStatement.setInt(1, value);
            ratingStatement.setInt(2, movie_id);
            int result = ratingStatement.executeUpdate();
            if (result > 0) {
                PreparedStatement movieStatement = connection.prepareStatement(UPDATE_MOVIE_RATING);
                movieStatement.setDouble(1, value);
                movieStatement.setLong(2, movie_id);
                int movieUpdate = movieStatement.executeUpdate();
                return movieUpdate > 0;
            } else {
                logger.error(ERROR_UPDATING_RATING_VALUE_LOG);
                return false;
            }
        } catch (SQLException e) {
            logger.error(ERROR_UPDATING_RATING_VALUE_LOG, e);
            throw new DaoException(ERROR_UPDATING_RATING_VALUE_LOG, e);
        }
    }

    @Override
    public Optional<Rating> getRatingByMovieId(Long id) throws DaoException {
        Optional<Rating> optionalRating = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RATING_BY_MOVIE_ID)) {
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()) {
                optionalRating = convertToRating.convert(resultSet);
            }}
            return optionalRating;
        } catch (SQLException e) {
            logger.error(ERROR_UPDATING_RATING_VALUE_LOG, e);
            throw new DaoException(ERROR_UPDATING_RATING_VALUE_LOG, e);
        }
    }

}
