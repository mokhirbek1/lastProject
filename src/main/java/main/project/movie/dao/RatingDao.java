package main.project.movie.dao;

import main.project.movie.entity.Rating;
import main.project.movie.exception.DaoException;

import java.util.Optional;

public interface RatingDao extends BaseDao<Rating>{
    Optional<Rating> getRatingByMovieId(Long id) throws DaoException;
}
