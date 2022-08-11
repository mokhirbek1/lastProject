package main.project.movie.dao.impl;

import main.project.movie.dao.MovieDao;
import main.project.movie.dao.converter.impl.ConvertToMovie;
import main.project.movie.entity.Movie;
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
import static main.project.movie.dao.attribute.DaoAttributeName.*;
import static main.project.movie.dao.query.DaoQuery.*;

public class MovieDaoImpl implements MovieDao {
    private final ConvertToMovie convertToMovie = new ConvertToMovie();
    private static final MovieDaoImpl instance = new MovieDaoImpl();

    private MovieDaoImpl() {
    }

    public static MovieDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Movie> findByCategory(String movieCategory) throws DaoException {
        List<Movie> movieList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CATEGORY)) {
            preparedStatement.setString(1, movieCategory);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Optional<Movie> movieOptional = convertToMovie.convert(resultSet);
                movieOptional.ifPresent(movieList::add);
            }
            return movieList;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_MOVIE_BY_CATEGORY_LOG, e);
            throw new DaoException(ERROR_FINDING_MOVIE_BY_CATEGORY_LOG, e);
        }
    }

    @Override
    public boolean insert(Movie movie) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_MOVIE_QUERY)) {
            statement.setString(1, movie.getName());
            statement.setString(2, movie.getCountry());
            statement.setInt(3, movie.getCreated_year());
            statement.setString(4, String.valueOf(movie.getCategory()));
            statement.setInt(5, movie.getAge_limit());
            statement.setString(6, movie.getDescription());
            statement.setString(7, movie.getImage_path());
            statement.setString(8, String.valueOf(movie.getLanguage()));
            int result = statement.executeUpdate();
            return result > 0 ;
        } catch (SQLException e) {
            logger.error(ERROR_INSERTING_MOVIE_LOG, e);
            throw new DaoException(ERROR_INSERTING_MOVIE_LOG, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_MOVIE_BY_ID)) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_DELETING_MOVIE_LOG, e);
            throw new DaoException(ERROR_DELETING_MOVIE_LOG, e);
        }
    }

    @Override
    public List<Movie> findAll() throws DaoException {
        List<Movie> movieList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MOVIE)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Optional<Movie> optionalMovie = convertToMovie.convert(resultSet);
                optionalMovie.ifPresent(movieList::add);
            }
            return movieList;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_ALL_MOVIE_LOG, e);
            throw new DaoException(ERROR_FINDING_ALL_MOVIE_LOG, e);
        }
    }

    @Override
    public Optional<Movie> getById(Long id) throws DaoException {
        Optional<Movie> movie = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RATING_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                movie = convertToMovie.convert(resultSet);
            }
            return movie;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_MOVIE_BY_ID_LOG, e);
            throw new DaoException(ERROR_FINDING_MOVIE_BY_ID_LOG, e);
        }
    }


    @Override
    public boolean update(Movie movie) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(EDIT_MOVIE)) {
            statement.setString(1, movie.getName());
            statement.setString(2, movie.getCountry());
            statement.setInt(3, movie.getCreated_year());
            statement.setString(4, String.valueOf(movie.getCategory()));
            statement.setString(5, String.valueOf(movie.getLanguage()));
            statement.setInt(6, movie.getAge_limit());
            statement.setString(7, movie.getDescription());
            statement.setString(8, movie.getImage_path());
            statement.setDouble(9, movie.getId());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_EDITING_MOVIE_LOG, e);
            throw new DaoException(ERROR_EDITING_MOVIE_LOG, e);
        }
    }

    @Override
    public boolean movieNameSuitable(String movieName) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_NAME)) {
            preparedStatement.setString(1, movieName);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.error(ERROR_CHECKING_MOVIE_NAME_LOG, e);
            throw new DaoException(ERROR_CHECKING_MOVIE_NAME_LOG, e);
        }
    }

    @Override
    public Optional<Integer> getIdByName(String name) throws DaoException {
        Optional<Integer> movie_id = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ID_BY_NAME)) {
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()) {
                movie_id = Optional.of(resultSet.getInt(MOVIE_ID));
            }}
            return movie_id;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_MOVIE_ID_LOG, e);
            throw new DaoException(ERROR_FINDING_MOVIE_ID_LOG, e);
        }
    }

}
