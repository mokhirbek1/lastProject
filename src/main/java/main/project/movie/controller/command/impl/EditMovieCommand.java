package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.Movie;
import main.project.movie.entity.type.Category;
import main.project.movie.entity.type.Language;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.MovieServiceImpl;
import main.project.movie.validation.CheckMovie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;
import java.util.Locale;

public class EditMovieCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String returnPage = (String) session.getAttribute(RETURN_PAGE);
        long id = (long) session.getAttribute(ID);
        Movie movie = new Movie();
        Router router;
        logger.info(MOVIE_ID_LOG, id);
        try {
            String movieName = request.getParameter(MOVIE_NAME);
            String country = request.getParameter(COUNTRY_NAME);
            int createdYear = Integer.parseInt(request.getParameter(CREATED_YEAR));
            Category category = Category.valueOf(request.getParameter(CATEGORY).toUpperCase(Locale.ROOT));
            Language language = Language.valueOf(request.getParameter(LANGUAGE).toUpperCase());
            int ageLimit = Integer.parseInt(request.getParameter(AGE_LIMIT));
            String description = request.getParameter(DESCRIPTION);
            String imagePath = request.getParameter(IMAGE_PATH);
            if (!CheckMovie.checkMovieValue(movieName, country, createdYear, category.toString(), language.toString(), ageLimit, description, imagePath)) {
                logger.error(INVALID_INPUT_LOG);
                request.setAttribute(UPDATE_MESSAGE, FAIL);
                router = new Router(PagePath.EDIT_PAGE, Router.Type.FORWARD);
                return router;
            }
            boolean isSuitable = CheckMovie.checkMovieValue(movieName, country, createdYear, category.toString(), language.toString(), ageLimit, description, imagePath);
            if (isSuitable) {
                movie.setId((int) id);
                movie.setName(movieName);
                movie.setCountry(country);
                movie.setCreated_year(createdYear);
                movie.setCategory(category);
                movie.setLanguage(language);
                movie.setAge_limit(ageLimit);
                movie.setDescription(description);
                movie.setImage_path(imagePath);
                String message = movieService.update(movie) ? SUCCESS : FAIL;
                request.setAttribute(UPDATE_MESSAGE, message);
            } else {
                logger.error(ERROR_UPDATING_LOG);
                request.setAttribute(UPDATE_MESSAGE, FAIL);
            }
            if (returnPage.equals(ADMIN_PAGE)) {
                router = new AdminPageCommand().execute(request);
            } else {
                router = new FindMoviesCommand().execute(request);
            }
        } catch (ServiceException serviceException) {
            logger.error(ERROR_EDITING_MOVIE_LOG, serviceException);
            throw new CommandException(serviceException);
        }
        logger.log(Level.INFO, ROUTER_LOG, router);
        return router;
    }
}
