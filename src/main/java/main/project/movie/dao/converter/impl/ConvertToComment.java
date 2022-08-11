package main.project.movie.dao.converter.impl;

import main.project.movie.dao.converter.BaseConverter;
import main.project.movie.entity.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.dao.attribute.ConverterAttributeName.*;
public class ConvertToComment implements BaseConverter<Comment> {
    @Override
    public Optional<Comment> convert(ResultSet resultSet) {
        Comment comment = new Comment();
        try {
            comment.setId(resultSet.getInt(COMMENT_ID));
            comment.setComment_text(resultSet.getString(COMMENT_TEXT));
            comment.setMovie_id(resultSet.getInt(MOVIE_ID));
            comment.setUsername(resultSet.getString(USERNAME));
            comment.setUser_id(resultSet.getInt(USER_ID));
            return Optional.of(comment);
        } catch (SQLException e) {
            logger.error(ERROR_CONVERTING_COMMENT_LOG);
            return Optional.empty();
        }
    }
}
