package main.project.movie.dao;

import main.project.movie.entity.Movie;
import main.project.movie.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface MovieDao extends BaseDao<Movie>{
    List<Movie> findAll() throws DaoException;
    Optional<Integer> getIdByName(String name) throws DaoException;
    List<Movie> findByCategory(String name) throws DaoException;
    boolean movieNameSuitable(String movieName) throws DaoException;
    Optional<Movie> getById(Long id) throws DaoException;
}
