package main.project.movie.dao.converter.impl;

import main.project.movie.dao.converter.BaseConverter;
import main.project.movie.entity.User;
import main.project.movie.entity.type.Status;
import main.project.movie.entity.type.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.dao.attribute.ConverterAttributeName.*;

public class ConvertToUser implements BaseConverter<User> {
    @Override
    public Optional<User> convert(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt(USER_ID));
            user.setUserName(resultSet.getString(USER_NAME));
            user.setEmail(resultSet.getString(EMAIL));
            user.setFirstname(resultSet.getString(FIRSTNAME));
            user.setLastname(resultSet.getString(LASTNAME));
            user.setRole(UserRole.valueOf(resultSet.getString(ROLE)));
            user.setStatus(Status.valueOf(resultSet.getString(STATUS)));
            return Optional.of(user);
        } catch (SQLException e) {
            logger.error(ERROR_CONVERTING_USER);
            return Optional.empty();
        }
    }
}
