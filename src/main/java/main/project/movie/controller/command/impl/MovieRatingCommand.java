package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.Movie;
import main.project.movie.entity.Rating;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.MovieServiceImpl;
import main.project.movie.service.impl.RatingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;
import java.util.Optional;

public class    MovieRatingCommand implements Command {
    private static Router router = new Router();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        RatingServiceImpl ratingService = RatingServiceImpl.getInstance();
        Router router = new Router((String) session.getAttribute(CURRENT_PAGE),Router.Type.FORWARD);
        long id = Long.parseLong(request.getParameter(ID));
        double value = Double.parseDouble(request.getParameter(RATE));
        try {
            Optional<Movie> optionalMovie = movieService.getById(id);
            Rating rating = new Rating();
            rating.setValue((int) value);
            rating.setMovie_id((int) id);
            if (ratingService.update(rating)) {
                Movie movie = optionalMovie.get();
                request.setAttribute(MOVIE_INFO,movie);
                request.setAttribute(RATING_INFO,rating);
                session.setAttribute(CURRENT_PAGE, PagePath.MOVIE_INFO_PAGE);
                logger.log(Level.INFO,ROUTER_LOG,router);
                return router;
            }else {
                logger.error(NOT_UPDATED_RATING_VALUE_LOG);
                throw new CommandException(NOT_UPDATED_RATING_VALUE_LOG);
            }
        }catch (ServiceException serviceException) {
            logger.error(ERROR_RATING_COMMAND_LOG, serviceException);
            throw new CommandException(serviceException);
        }
    }
}
