package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.Movie;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.MovieService;
import main.project.movie.service.impl.MovieServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import java.util.Optional;

import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;

public class UpdateMovieCommand implements Command {
    private static Router router = new Router();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MovieService movieService = MovieServiceImpl.getInstance();
        String returnPage = request.getParameter(RETURN_PAGE);
        session.setAttribute(RETURN_PAGE, returnPage);
        Movie forUpdate;
        long id = Long.parseLong(request.getParameter(ID));
        try {
            Optional<Movie> optionalMovie = movieService.getById(id);
            if (optionalMovie.isEmpty()) {
                throw new ServiceException(EMPTY_OPTIONAL_LOG+ id);
            }
            forUpdate = optionalMovie.get();
            session.setAttribute(MOVIE_INFO, forUpdate);
            session.setAttribute(ID, id);
            router = new Router(PagePath.EDIT_PAGE, Router.Type.FORWARD);
            logger.log(Level.INFO, ROUTER_LOG, router);
            return router;
        } catch (ServiceException serviceException) {
            logger.error(ERROR_SEARCHING_MOVIE_LOG, id, serviceException);
            throw new CommandException(serviceException);
        }
    }
}
