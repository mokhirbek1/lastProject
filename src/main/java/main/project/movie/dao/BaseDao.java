package main.project.movie.dao;

import main.project.movie.exception.DaoException;
import main.project.movie.entity.AbstractEntity;

public interface BaseDao<T extends AbstractEntity> {
    boolean delete(Long id) throws DaoException;
    boolean insert(T t) throws DaoException;
    boolean update(T t) throws DaoException;

}
