package main.project.movie.service.impl;

import main.project.movie.dao.impl.RatingDaoImpl;
import main.project.movie.entity.Rating;
import main.project.movie.exception.DaoException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.RatingService;
import static main.project.movie.controller.message.LogMessage.*;

import java.util.Optional;

public class RatingServiceImpl implements RatingService {
    private RatingDaoImpl ratingDao = RatingDaoImpl.getInstance();
    private static RatingServiceImpl instance = new RatingServiceImpl();

    public static RatingServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Rating rating) throws ServiceException {
        boolean isSaved;
        try {
            isSaved = ratingDao.insert(rating);
        } catch (DaoException daoException) {
            logger.error(ERROR_REGISTR_RATING_LOG, daoException);
            throw new ServiceException(ERROR_REGISTR_RATING_LOG, daoException);
        }
        return isSaved;
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return ratingDao.delete(id);
        } catch (DaoException daoException) {
            logger.error(ERROR_DELETING_RATING_LOG, daoException);
            throw new ServiceException(ERROR_DELETING_RATING_LOG, daoException);
        }
    }

    @Override
    public Optional<Rating> getRatingByMovieId(Long id) throws ServiceException {
        try {
            return ratingDao.getRatingByMovieId(id);
        } catch (DaoException daoException) {
            logger.error(ERROR_FINDING_RATING_BY_MOVIE_ID_LOG, daoException);
            throw new ServiceException(ERROR_FINDING_RATING_BY_MOVIE_ID_LOG, daoException);
        }
    }

    @Override
    public boolean update(Rating rating) throws ServiceException {
        try {
            return ratingDao.update(rating);
        } catch (DaoException daoException) {
            logger.error(ERROR_UPDATING_RATING_BY_MOVIE_ID_LOG, daoException);
            throw new ServiceException(ERROR_UPDATING_RATING_BY_MOVIE_ID_LOG, daoException);
        }
    }
}
