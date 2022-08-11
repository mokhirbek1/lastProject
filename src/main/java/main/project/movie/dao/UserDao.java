package main.project.movie.dao;

import main.project.movie.entity.type.Status;
import main.project.movie.entity.type.UserRole;
import main.project.movie.exception.DaoException;
import main.project.movie.entity.User;

import java.util.List;
import java.util.Optional;
public interface UserDao extends BaseDao<User>{
    Optional<User> findByLogin(String login) throws DaoException;
    UserRole getUserRole(String login) throws DaoException;
    Status getUserStatus(String login) throws DaoException;
    List<User> getUsersByRole(UserRole userRole) throws DaoException;
    boolean loginSuitable(String login) throws DaoException;
    boolean emailSuitable(String email) throws DaoException;
    boolean blockUserById(int userId) throws DaoException;
    boolean activateUserById(int login) throws DaoException;
    boolean changeRole(int userId, UserRole role) throws DaoException;
    List<String> getInfoByEmail(String email) throws DaoException;
    Optional<User> findById(Long id) throws DaoException;
    boolean authenticate(String login, String password) throws DaoException;

}
