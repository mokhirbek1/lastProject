package main.project.movie.service;

import main.project.movie.entity.Rating;
import main.project.movie.exception.ServiceException;

import java.util.Optional;

public interface RatingService extends BaseService<Rating> {
    Optional<Rating> getRatingByMovieId(Long id) throws ServiceException;
}
