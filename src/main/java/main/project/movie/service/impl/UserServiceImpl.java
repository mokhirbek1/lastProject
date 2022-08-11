package main.project.movie.service.impl;

import main.project.movie.entity.type.Status;
import main.project.movie.exception.DaoException;
import main.project.movie.exception.ServiceException;
import main.project.movie.dao.impl.UserDaoImpl;
import main.project.movie.entity.User;
import main.project.movie.entity.type.UserRole;
import main.project.movie.service.UserService;
import main.project.movie.util.PasswordCoding;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.message.LogMessage.*;

import java.util.List;
import java.util.Optional;


public class UserServiceImpl implements UserService {
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<String> checkEmail(String email) throws ServiceException {
        try {
            return userDao.getInfoByEmail(email);
        } catch (DaoException e) {
            logger.log(Level.ERROR, ERROR_CHECKING_USER_EMAIL_LOG, e);
            throw new ServiceException(ERROR_CHECKING_USER_EMAIL_LOG, e);
        }
    }

    @Override
    public boolean changeRole(int user_id, UserRole role) throws ServiceException {
        try {
            return userDao.changeRole(user_id, role);
        } catch (DaoException e) {
            logger.log(Level.ERROR, ERROR_CHANGING_USER_STATUS_LOG, e);
            throw new ServiceException(ERROR_CHANGING_USER_STATUS_LOG, e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        try {
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            logger.error(ERROR_FINDING_USER_BY_LOGIN_LOG, e);
            throw new ServiceException(ERROR_FINDING_USER_BY_LOGIN_LOG, e);
        }
    }

    @Override
    public UserRole findUserRole(String login) throws ServiceException {
        try {
            return userDao.getUserRole(login);
        } catch (DaoException e) {
            logger.error(ERROR_FINDING_USER_ROLE_BY_LOGIN_LOG, e);
            throw new ServiceException(ERROR_FINDING_USER_ROLE_BY_LOGIN_LOG, e);
        }
    }

    @Override
    public Status findUserStatus(String login) throws ServiceException {
        try {
            return userDao.getUserStatus(login);
        } catch (DaoException e) {
            logger.error(ERROR_FINDING_USER_STATUS_BY_LOGIN_LOG);
            throw new ServiceException(ERROR_FINDING_USER_STATUS_BY_LOGIN_LOG, e);
        }
    }

    @Override
    public List<User> findUsersByRole(UserRole userRole) throws ServiceException {
        try {
            return userDao.getUsersByRole(userRole);
        } catch (DaoException e) {
            logger.error(ERROR_FINDING_USERS_BY_ROLE_LOG, e);
            throw new ServiceException(ERROR_FINDING_USERS_BY_ROLE_LOG, e);
        }
    }

    @Override
    public boolean blockUserById(int user_id) throws ServiceException {
        try {
            return userDao.blockUserById(user_id);
        } catch (DaoException e) {
            logger.error(ERROR_BLOCKING_LOG, e);
            throw new ServiceException(ERROR_BLOCKING_LOG, e);
        }
    }

    @Override
    public boolean activateUserById(int user_id) throws ServiceException {
        try {
            return userDao.activateUserById(user_id);
        } catch (DaoException e) {
            logger.error(ERROR_ACTIVATING_LOG);
            throw new ServiceException(ERROR_ACTIVATING_LOG, e);
        }
    }

    @Override
    public boolean loginSuitable(String login) throws ServiceException {
        try {
            return userDao.loginSuitable(login);
        } catch (DaoException daoException) {
            logger.error(ERROR_CHECK_AVAILABLE_LOGIN_LOG, daoException);
            throw new ServiceException(ERROR_CHECK_AVAILABLE_LOGIN_LOG, daoException);
        }
    }

    @Override
    public boolean emailSuitable(String email) throws ServiceException {
        try {
            return userDao.emailSuitable(email);
        } catch (DaoException daoException) {
            logger.error(ERROR_CHECK_AVAILABLE_EMAIL_LOG, daoException);
            throw new ServiceException(ERROR_CHECK_AVAILABLE_EMAIL_LOG, daoException);
        }
    }

    @Override
    public boolean loginAuthenticate(String login, String password) throws ServiceException {
        try {
            return userDao.authenticate(login, password);
        } catch (DaoException e) {
            logger.error(ERROR_AUTHENTICATION_LOG, e);
            throw new ServiceException(ERROR_AUTHENTICATION_LOG, e);
        }
    }

    @Override
    public boolean insert(User user) throws ServiceException {
        boolean isSaved;
        try {
            user.setPassword(PasswordCoding.hashPassword(user.getPassword()));
            isSaved = userDao.insert(user);
        } catch (DaoException e) {
            logger.error(FATAL_REGISTRATION_LOG, e);
            throw new ServiceException(FATAL_REGISTRATION_LOG, e);
        }
        return isSaved;
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return userDao.delete(id);
        } catch (DaoException e) {
            logger.error(ERROR_DELETING_USER_BY_ID_LOG, e);
            throw new ServiceException(ERROR_DELETING_USER_BY_ID_LOG, e);
        }

    }

    @Override
    public Optional<User> findById(Long id) throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            logger.error(ERROR_FINDING_USER_BY_ID_LOG, e);
            throw new ServiceException(ERROR_FINDING_USER_BY_ID_LOG, e);
        }
    }

    @Override
    public boolean update(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DaoException e) {
            logger.log(Level.ERROR, ERROR_UPDATING_PSW_BY_EMAIL_LOG, e);
            throw new ServiceException(ERROR_UPDATING_PSW_BY_EMAIL_LOG, e);
        }
    }
}
