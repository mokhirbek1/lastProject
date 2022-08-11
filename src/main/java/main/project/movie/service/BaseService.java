package main.project.movie.service;

import main.project.movie.exception.ServiceException;

public interface BaseService<T> {
    boolean insert(T t) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
    boolean update(T t) throws ServiceException;

}
