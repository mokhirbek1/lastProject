package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.MovieServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class DeleteMovieCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        String returnPage = request.getParameter(RETURN_PAGE);
        long id = Long.parseLong(request.getParameter(ID));
        Router router;
        logger.info(MOVIE_ID_LOG, id);
        try {
            String message = movieService.delete(id) ? SUCCESS : FAIL;
            request.setAttribute(DELETE_MESSAGE, message);
            if (returnPage.equals(ADMIN_PAGE)) {
                router = new AdminPageCommand().execute(request);
            } else {
                router = new FindMoviesCommand().execute(request);
            }
            logger.log(Level.INFO, ROUTER_LOG + router);
            return router;
        } catch (ServiceException serviceException) {
            logger.error(ERROR_DELETING_MOVIE_LOG, serviceException);
            throw new CommandException(ERROR_DELETING_MOVIE_LOG, serviceException);
        }
    }
}
