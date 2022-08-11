package main.project.movie.dao.impl;

import main.project.movie.dao.UserDao;
import main.project.movie.dao.converter.impl.ConvertToUser;
import main.project.movie.entity.type.Status;
import main.project.movie.exception.DaoException;
import main.project.movie.entity.User;
import main.project.movie.entity.type.UserRole;
import main.project.movie.pool.ConnectionPool;
import main.project.movie.util.PasswordCoding;
import org.apache.logging.log4j.Level;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.dao.attribute.DaoAttributeName.*;
import static main.project.movie.dao.query.DaoQuery.*;

public class UserDaoImpl implements UserDao {
    private final ConvertToUser convertToUser = new ConvertToUser();
    private static final UserDaoImpl instance = new UserDaoImpl();


    public static UserDaoImpl getInstance() {
        return instance;
    }

    private UserDaoImpl() {
    }

    @Override
    public List<String> getInfoByEmail(String email) throws DaoException {
        List<String> userInfo = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_INFO_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
            if (resultSet.next()) {
                userInfo.add(resultSet.getString(FIRST_NAME));
                userInfo.add(resultSet.getString(LAST_NAME));
                logger.log(Level.INFO, FIRSTNAME_LOG, userInfo.get(0));
                logger.log(Level.INFO, LASTNAME_LOG, userInfo.get(1));
            } else {
                userInfo = null;
            }
            }
            return userInfo;
        } catch (SQLException e) {
            logger.error(ERROR_CHECKING_USER_EMAIL_LOG, e);
            throw new DaoException(ERROR_CHECKING_USER_EMAIL_LOG, e);
        }
    }

    @Override
    public boolean loginSuitable(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return !resultSet.next();
            }
        } catch (SQLException e) {
            logger.error(ERROR_CHECKING_USER_LOGIN_LOG, e);
            throw new DaoException(ERROR_CHECKING_USER_LOGIN_LOG, e);
        }
    }

    @Override
    public boolean emailSuitable(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return !resultSet.next();
            }
        } catch (SQLException e) {
            logger.error(ERROR_CHECKING_USER_EMAIL_LOG, e);
            throw new DaoException(ERROR_CHECKING_USER_EMAIL_LOG, e);
        }
    }

    @Override
    public boolean insert(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_QUERY)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRole().toString());
            statement.setString(3, user.getFirstname());
            statement.setString(4, user.getLastname());
            statement.setString(5, user.getUserName());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getStatus().toString());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_INSERTING_USER_LOG, e);
            throw new DaoException(ERROR_INSERTING_USER_LOG, e);
        }
    }

    @Override
    public boolean changeRole(int user_id, UserRole role) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_ROLE)) {
            preparedStatement.setString(1, role.toString());
            preparedStatement.setInt(2, user_id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_CHANGING_USER_ROLE_LOG, e);
            throw new DaoException(ERROR_CHANGING_USER_ROLE_LOG, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_DELETING_USER_LOG, e);
            throw new DaoException(ERROR_DELETING_USER_LOG, e);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        String newPassword = user.getPassword();
        String email = user.getEmail();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_BY_EMAIL)) {
            String hashedPassword = PasswordCoding.hashPassword(newPassword);
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, email);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException sqlException) {
            logger.error(ERROR_UPDATING_USER_PASSWORD_LOG, email);
            throw new DaoException(ERROR_UPDATING_USER_PASSWORD_LOG, sqlException);
        }
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        Optional<User> user = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = convertToUser.convert(resultSet);
                }
            }
            return user;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_USER_BY_ID_LOG, e);
            throw new DaoException(ERROR_FINDING_USER_BY_ID_LOG, e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        Optional<User> user = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = convertToUser.convert(resultSet);
                }
            }
            return user;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_USER_BY_LOGIN_LOG, e);
            throw new DaoException(ERROR_FINDING_USER_BY_LOGIN_LOG, e);
        }
    }

    @Override
    public UserRole getUserRole(String login) throws DaoException {
        UserRole userRole = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_ROLE_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userRole = UserRole.valueOf(resultSet.getString(ROLE).toUpperCase());
                }
            }
            return userRole;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_USER_BY_ROLE_LOG, e);
            throw new DaoException(ERROR_FINDING_USER_BY_ROLE_LOG, e);
        }
    }

    @Override
    public Status getUserStatus(String login) throws DaoException {
        Status status = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_STATUS_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    status = Status.valueOf(resultSet.getString(STATUS).toUpperCase());
                }
            }
            return status;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_USER_BY_STATUS_LOG, e);
            throw new DaoException(ERROR_FINDING_USER_BY_STATUS_LOG, e);
        }
    }

    @Override
    public List<User> getUsersByRole(UserRole userRole) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ROLE)) {
            statement.setString(1, userRole.toString().toUpperCase());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<User> user = convertToUser.convert(resultSet);
                    user.ifPresent(users::add);
                }
            }
            return users;
        } catch (SQLException e) {
            logger.error(ERROR_FINDING_USER_BY_ROLE_LOG, e);
            throw new DaoException(ERROR_FINDING_USER_BY_ROLE_LOG, e);
        }
    }


    @Override
    public boolean blockUserById(int user_id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(BLOCK_USER)) {
            statement.setInt(1, user_id);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_BLOCKING_USER_BY_ID_LOG, e);
            throw new DaoException(ERROR_BLOCKING_USER_BY_ID_LOG, e);
        }
    }

    @Override
    public boolean activateUserById(int user_id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ACTIVATE_USER)) {
            statement.setInt(1, user_id);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(ERROR_ACTIVATING_USER_BY_ID_LOG, e);
            throw new DaoException(ERROR_ACTIVATING_USER_BY_ID_LOG, e);
        }
    }


    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        if (login.isEmpty() || password.isEmpty()) {
            return false;
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_FOR_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                String passwordFromDb;
                if (resultSet.next()) {
                    passwordFromDb = resultSet.getString(PASSWORD);
                    return PasswordCoding.checkPassword(password, passwordFromDb);
                }
            }
        } catch (SQLException e) {
            logger.error(ERROR_AUTHENTICATING_USER_LOG, e);
            throw new DaoException(ERROR_AUTHENTICATING_USER_LOG, e);
        }
        return false;
    }
}