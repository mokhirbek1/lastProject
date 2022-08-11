package main.project.movie.service.impl;

import main.project.movie.dao.impl.MovieDaoImpl;
import main.project.movie.entity.Movie;
import main.project.movie.exception.DaoException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.MovieService;
import static main.project.movie.controller.message.LogMessage.*;
import java.util.List;
import java.util.Optional;

public class MovieServiceImpl implements MovieService {
    private static MovieDaoImpl movieDao = MovieDaoImpl.getInstance();
    private static MovieServiceImpl instance = new MovieServiceImpl();

    private MovieServiceImpl() {
    }

    public static MovieServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Movie> findByCategory(String movieCategory) throws ServiceException {
        try {
            return movieDao.findByCategory(movieCategory);
        } catch (DaoException e) {
            logger.error(ERROR_MOVIE_BY_CATEGORY_LOG, e);
            throw new ServiceException(ERROR_MOVIE_BY_CATEGORY_LOG, e);
        }
    }

    @Override
    public Optional<Movie> getById(Long id) throws ServiceException {
        try {
            return movieDao.getById(id);
        } catch (DaoException e) {
            logger.error(ERROR_MOVIE_BY_ID_LOG, e);
            throw new ServiceException(ERROR_MOVIE_BY_ID_LOG, e);
        }
    }

    @Override
    public boolean update(Movie movie) throws ServiceException {
        try {
            return movieDao.update(movie);
        } catch (DaoException e) {
            logger.error(ERROR_UPDATING_MOVIE_LOG, e);
            throw new ServiceException(ERROR_UPDATING_MOVIE_LOG, e);
        }
    }

    @Override
    public boolean movieNameSuitable(String movieName) throws ServiceException {
        try {
            return movieDao.movieNameSuitable(movieName);
        } catch (DaoException e) {
            logger.error(ERROR_CHECKING_MOVIE_NAME_LOG, e);
            throw new ServiceException(ERROR_CHECKING_MOVIE_NAME_LOG, e);
        }
    }

    @Override
    public Optional<Integer> getIdByName(String name) throws ServiceException {
        try {
            return movieDao.getIdByName(name);
        } catch (DaoException e) {
            logger.error(ERROR_FINDING_MOVIE_NAME_LOG, e);
            throw new ServiceException(ERROR_FINDING_MOVIE_NAME_LOG, e);
        }
    }

    @Override
    public boolean insert(Movie movie) throws ServiceException {
        try {
            return movieDao.insert(movie);
        } catch (DaoException e) {
            logger.error(ERROR_INSERTING_MOVIE_LOG, e);
            throw new ServiceException(ERROR_INSERTING_MOVIE_LOG, e);
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return movieDao.delete(id);
        } catch (DaoException e) {
            logger.error(ERROR_DELETING_MOVIE_LOG, e);
            throw new ServiceException(ERROR_DELETING_MOVIE_LOG, e);
        }
    }

    @Override
    public List<Movie> findAll() throws ServiceException {
        try {
            return movieDao.findAll();
        } catch (DaoException e) {
            logger.error(ERROR_FINDING_ALL_MOVIE_LOG, e);
            throw new ServiceException(ERROR_FINDING_ALL_MOVIE_LOG, e);
        }
    }
}
