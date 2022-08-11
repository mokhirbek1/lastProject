package main.project.movie.service;

import main.project.movie.entity.type.Status;
import main.project.movie.entity.type.UserRole;
import main.project.movie.exception.ServiceException;
import main.project.movie.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User> {
    boolean blockUserById(int userId) throws ServiceException;
    boolean activateUserById(int userId) throws ServiceException;
    List<User> findUsersByRole(UserRole userRole) throws ServiceException;
    boolean loginSuitable(String login) throws ServiceException;
    boolean emailSuitable(String email) throws ServiceException;
    boolean loginAuthenticate(String login, String password) throws ServiceException;
    Optional<User> findByLogin(String login) throws ServiceException;
    UserRole findUserRole(String login) throws ServiceException;
    Status findUserStatus(String login) throws ServiceException;
    boolean changeRole(int userId, UserRole role) throws ServiceException;
    List<String> checkEmail(String email) throws ServiceException;
    Optional<User> findById(Long id) throws ServiceException;
}
