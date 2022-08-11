package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.Movie;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.MovieServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import java.util.List;
import static main.project.movie.controller.message.LogMessage.*;

public class AdminMovieCategoryPageCommand implements Command {
    private static Router router = null;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String movieCategory = request.getParameter(MOVIE_CATEGORY);
        try {
            List<Movie> movieList = movieService.findByCategory(movieCategory);
            request.setAttribute(MOVIE_CATEGORY_LIST, movieList);
            request.setAttribute(MOVIE_CATEGORY, movieCategory);
            session.setAttribute(CURRENT_PAGE, PagePath.ADMIN_PAGE_BY_CATEGORY_MOVIE);
            router = new Router(PagePath.ADMIN_PAGE_BY_CATEGORY_MOVIE, Router.Type.FORWARD);
            logger.log(Level.INFO, ROUTER_LOG, router);
            return router;
        } catch (ServiceException serviceException) {
            logger.error(ERROR_GETTING_ALL_MOVIE_LOG, serviceException);
            throw new CommandException(serviceException);
        }
    }
}
