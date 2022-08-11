package main.project.movie.validation;

import main.project.movie.entity.type.Category;
import main.project.movie.entity.type.Language;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.validation.attribute.ValidationAttribute.*;

public class CheckMovie {
    private CheckMovie() {}
    public static boolean checkMovieValue(String movieName, String country, int createdYear, String category, String language, int ageLimit, String description, String image_url) {
        if (movieName.isEmpty() || country.isEmpty() || createdYear == 0 || category.isEmpty() || language.isEmpty() || ageLimit == 0 || description.isEmpty() || image_url.isEmpty()) {
            return false;
        }
        if (movieName.length() < MOVIE_NAME_LENGTH_FROM || movieName.length() > MOVIE_NAME_LENGTH_TO) {
            logger.log(Level.DEBUG, MOVIE_NAME_LOG + movieName + NOT_SUITABLE_LOG);
            return false;
        }
        if (country.length() < COUNTRY_NAME_LENGTH_FROM || country.length() > COUNTRY_NAME_LENGTH_TO) {
            logger.log(Level.DEBUG, COUNTRY_LOG + country + NOT_SUITABLE_LOG);
            return false;
        }
        if (createdYear < CREATED_YEAR_FROM || createdYear > CREATED_YEAR_TO) {
            logger.log(Level.DEBUG, CREATED_YEAR_LOG + createdYear + NOT_SUITABLE_LOG);
            return false;
        }
        if (Category.valueOf(category).equals(category)) {
            logger.log(Level.DEBUG, CATEGORY_LOG + category + NOT_SUITABLE_LOG);
            return false;
        }
        if (Language.valueOf(language).equals(language)) {
            logger.log(Level.DEBUG, LANGUAGE_LOG + language + NOT_SUITABLE_LOG);
            return false;
        }
        if (ageLimit <AGE_LIMIT_FROM || ageLimit > AGE_LIMIT_TO) {
            logger.log(Level.DEBUG, AGE_LIMIT_LOG + ageLimit + NOT_SUITABLE_LOG);
            return false;
        }
        if (description.length() > DESCRIPTION_TEXT_LENGTH) {
            logger.log(Level.DEBUG, DESCRIPTION_LOG + description + NOT_SUITABLE_LOG);
            return false;
        }
        if (image_url.length() < IMAGE_URL_LENGTH_FROM || image_url.length() > IMAGE_URL_LENGTH_TO) {
            logger.log(Level.DEBUG, IMAGE_PATH_LOG + image_url + NOT_SUITABLE_LOG);
            return false;
        }
        return true;
    }
}
