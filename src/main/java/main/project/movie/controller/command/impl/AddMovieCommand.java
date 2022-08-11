package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.Movie;
import main.project.movie.entity.Rating;
import main.project.movie.entity.type.Category;
import main.project.movie.entity.type.Language;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.MovieServiceImpl;
import main.project.movie.service.impl.RatingServiceImpl;
import main.project.movie.validation.CheckMovie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import java.util.Optional;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;

public class AddMovieCommand implements Command {
    private static Router router = new Router();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        RatingServiceImpl ratingService = RatingServiceImpl.getInstance();
        String movieName = request.getParameter(MOVIE_NAME);
        String country = request.getParameter(COUNTRY_NAME);
        int createdYear = Integer.parseInt(request.getParameter(CREATED_YEAR));
        Category category = Category.valueOf(request.getParameter(CATEGORY).toUpperCase());
        Language language = Language.valueOf(request.getParameter(LANGUAGE).toUpperCase());
        int ageLimit = Integer.parseInt(request.getParameter(AGE_LIMIT));
        String description = request.getParameter(DESCRIPTION);
        String image_url = request.getParameter(IMAGE_PATH);
        if (!CheckMovie.checkMovieValue(movieName, country, createdYear, category.toString(), language.toString(), ageLimit, description, image_url)) {
            logger.error(MOVIES_VALUES_NOT_CORRECTLY_LOG);
            router = new Router(PagePath.ADD_PAGE, Router.Type.FORWARD);
            logger.log(Level.INFO, ROUTER_LOG, router);
            return router;
        }
        boolean isMatched = CheckMovie.checkMovieValue(movieName, country, createdYear, category.toString(), language.toString(), ageLimit, description, image_url);

        try {
            if (isMatched) {
                if (movieService.movieNameSuitable(movieName)) {
                    Movie movie = new Movie();
                    movie.setName(movieName);
                    movie.setCountry(country);
                    movie.setCreated_year(createdYear);
                    movie.setCategory(category);
                    movie.setLanguage(language);
                    movie.setAge_limit(ageLimit);
                    movie.setDescription(description);
                    movie.setImage_path(image_url);
                    if (movieService.insert(movie)) {
                        Optional<Integer> optionalIdOfMovie = movieService.getIdByName(movie.getName());
                        if (optionalIdOfMovie.isEmpty()) {
                            logger.error(OPTIONAL_MOVIE_ID_EMPTY_LOG);
                            throw new CommandException(OPTIONAL_MOVIE_ID_EMPTY_LOG);
                        }
                        Rating rating = new Rating();
                        int movieId = optionalIdOfMovie.get();
                        rating.setMovie_id(movieId);
                        ratingService.insert(rating);
                        session.setAttribute(ADD_MASSAGE, SUCCESS);
                        return new FindMoviesCommand().execute(request);
                    } else {
                        request.setAttribute(ADD_MASSAGE, FAIL);
                        router = new Router(PagePath.ADD_PAGE, Router.Type.FORWARD);
                    }
                } else {
                    session.setAttribute(ADD_MASSAGE, EXIST_MOVIE);
                    logger.error(NOT_SAVED_MOVIE_LOG);
                    return new FindMoviesCommand().execute(request);
                }

            } else {
                logger.error(MOVIE_NOT_VALIDATED_LOG);
                router = new Router(PagePath.ADD_PAGE, Router.Type.FORWARD);
            }
        } catch (ServiceException serviceException) {
            logger.error(ERROR_REGISTR_MOVIE_LOG, serviceException);
            throw new CommandException(serviceException);
        }
        logger.log(Level.INFO, ROUTER_LOG, router);
        return router;
    }
}
