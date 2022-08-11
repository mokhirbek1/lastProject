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
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;
import java.util.List;
import java.util.Optional;

public class MovieInfoPageCommand implements Command {
    private static Router router = new Router();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        RatingServiceImpl ratingService = RatingServiceImpl.getInstance();
        CommentServiceImpl commentService = CommentServiceImpl.getInstance();
        Router router;
        long id = Long.parseLong(request.getParameter(ID));
        logger.log(Level.INFO,MOVIE_ID_LOG ,id);
        try {
            Optional<Movie> optionalMovie = movieService.getById(id);
            Optional<Rating> optionalRating = ratingService.getRatingByMovieId(id);
            List<Comment> commentList = commentService.findAllCommentByMovieId(id);
            if (optionalMovie.isEmpty()) {
                throw new CommandException(NOT_FOUND_MOVIE_LOG+id);
            }
            if (optionalRating.isEmpty()) {
                logger.info(RATING_NOT_ADDED_LOG);
                Rating rating = new Rating(0,0,0);
                request.setAttribute(RATING_INFO, rating);
            }else {
                Rating rating = optionalRating.get();
                request.setAttribute(RATING_INFO, rating);
            }
            Movie movie = optionalMovie.get();
            request.setAttribute(MOVIE_INFO, movie);
            request.setAttribute(COMMENT_LIST, commentList);
            request.setAttribute(ID, id);
            session.setAttribute(CURRENT_PAGE, PagePath.MOVIE_INFO_PAGE);
            router = new Router(PagePath.MOVIE_INFO_PAGE, Router.Type.FORWARD);
            logger.log(Level.INFO,ROUTER_LOG,router);
            return router;
        } catch (ServiceException serviceException) {
            logger.error(ERROR_FINDING_MOVIE_LOG , serviceException);
            throw new CommandException(serviceException);
        }

    }
}
