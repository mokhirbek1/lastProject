package main.project.movie.dao.converter;

import java.sql.ResultSet;
import java.util.Optional;

public interface BaseConverter<T> {
    Optional<T> convert(ResultSet resultSet);
}
