package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.Comment;
import main.project.movie.entity.Movie;
import main.project.movie.entity.Rating;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.MovieServiceImpl;
import main.project.movie.service.impl.CommentServiceImpl;
import main.project.movie.service.impl.RatingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import java.util.List;
import java.util.Optional;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class MovieRatingForUserCommand implements Command {
    private static Router router = new Router();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        RatingServiceImpl ratingService = RatingServiceImpl.getInstance();
        CommentServiceImpl commentService = CommentServiceImpl.getInstance();
        Router router = new Router((String) session.getAttribute(CURRENT_PAGE),Router.Type.FORWARD);
        int id = Integer.parseInt(request.getParameter(MOVIE_ID));
        int value = Integer.parseInt(request.getParameter(RATE));

        try {
            Rating rating = new Rating();
            rating.setMovie_id(id);
            rating.setValue(value);
            if (ratingService.update(rating)) {
                List<Comment> commentList = commentService.findAllCommentByMovieId(id);
                Optional<Movie> optionalMovie = movieService.getById((long) id);
                Optional<Rating> optionalRating = ratingService.getRatingByMovieId((long) id);
                Movie movie = optionalMovie.isPresent()?optionalMovie.get():new Movie();
                Rating newRating = optionalRating.isPresent()?optionalRating.get():new Rating();
                request.setAttribute(MOVIE_INFO,movie);
                request.setAttribute(RATING_INFO,newRating);
                request.setAttribute(COMMENT_LIST,commentList);
                session.setAttribute(CURRENT_PAGE, PagePath.MOVIE_INFO_USER_PAGE);
                logger.log(Level.INFO,ROUTER_LOG,router);
                return router;
            }else {
                logger.error(NOT_UPDATED_RATING_VALUE_LOG);
                throw new CommandException(NOT_UPDATED_RATING_VALUE_LOG);
            }
        }catch (ServiceException serviceException) {
            logger.error(ERROR_RATING_COMMAND_LOG , serviceException);
            throw new CommandException(serviceException);
        }
    }
}
