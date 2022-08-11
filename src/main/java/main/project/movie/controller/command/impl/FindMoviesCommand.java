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
import java.util.List;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class FindMoviesCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        try {
            List<Movie> movieList = movieService.findAll();
            session.setAttribute(CURRENT_PAGE, PagePath.MOVIES_PAGE);
            request.setAttribute(MOVIE_LIST,movieList);
            String message = (String) session.getAttribute(ADD_MESSAGE);
            request.setAttribute(ADD_MESSAGE, message);
            router = new Router(PagePath.MOVIES_PAGE,Router.Type.FORWARD);
            logger.info(SUCCESSFULLY_GOT_MOVIES_LOG, movieList);
            logger.log(Level.INFO,ROUTER_LOG, router);
            return router;
        }catch (ServiceException serviceException) {
            logger.error( ERROR_GETTING_MOVIES_LOG,  serviceException);
            throw new CommandException(ERROR_GETTING_MOVIES_LOG, serviceException);
        }
    }
}
