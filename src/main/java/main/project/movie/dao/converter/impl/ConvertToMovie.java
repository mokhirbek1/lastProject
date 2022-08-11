package main.project.movie.dao.converter.impl;

import main.project.movie.dao.converter.BaseConverter;
import main.project.movie.entity.Movie;
import main.project.movie.entity.type.Category;
import main.project.movie.entity.type.Language;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.dao.attribute.ConverterAttributeName.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ConvertToMovie implements BaseConverter<Movie> {
    @Override
    public Optional<Movie> convert(ResultSet resultSet) {
        Movie movie = new Movie();
        try {
            movie.setId(resultSet.getInt(MOVIE_ID));
            movie.setName(resultSet.getString(MOVIE_NAME));
            movie.setCategory(Category.valueOf(resultSet.getString(CATEGORY)));
            movie.setLanguage(Language.valueOf(resultSet.getString(LANGUAGE)));
            movie.setCountry(resultSet.getString(COUNTRY));
            movie.setDescription(resultSet.getString(DESCRIPTION));
            movie.setAge_limit(resultSet.getInt(AGE_LIMIT));
            movie.setImage_path(resultSet.getString(IMAGE_PATH));
            movie.setRating_value(resultSet.getInt(RATING_VALUE));
            movie.setCreated_year(resultSet.getInt(CREATED_YEAR));
            logger.info(MOVIE_ID_LOG+movie);
            return Optional.of(movie);
        } catch (SQLException e) {
            logger.error(ERROR_CONVERTING_MOVIE,e);
            return Optional.empty();
        }
    }
}
