package main.project.movie.dao.converter.impl;

import main.project.movie.dao.converter.BaseConverter;
import main.project.movie.entity.Rating;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.dao.attribute.ConverterAttributeName.*;

public class ConvertToRating implements BaseConverter<Rating> {
    @Override
    public Optional<Rating> convert(ResultSet resultSet) {
        Rating rating = new Rating();
        try {
            rating.setId(resultSet.getInt(RATING_ID));
            rating.setMovie_id(resultSet.getInt(MOVIE_ID));
            rating.setValue(resultSet.getInt(VALUE));
            return Optional.of(rating);
        } catch (SQLException e) {
            logger.error(ERROR_CONVERTING_RATING_LOG);
            return Optional.empty();
        }
    }
}
