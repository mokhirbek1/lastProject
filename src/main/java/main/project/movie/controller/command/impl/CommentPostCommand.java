package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.Comment;
import main.project.movie.entity.Movie;
import main.project.movie.entity.Rating;
import main.project.movie.entity.User;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.MovieServiceImpl;
import main.project.movie.service.impl.CommentServiceImpl;
import main.project.movie.service.impl.RatingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import java.util.List;
import java.util.Optional;
import static main.project.movie.controller.message.LogMessage.*;

public class CommentPostCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MovieServiceImpl movieService = MovieServiceImpl.getInstance();
        RatingServiceImpl ratingService = RatingServiceImpl.getInstance();
        CommentServiceImpl commentService = CommentServiceImpl.getInstance();
        String comment_text = request.getParameter(COMMENT);
        Router router = new Router((String) session.getAttribute(CURRENT_PAGE));
        int movie_id = Integer.parseInt(request.getParameter(ID));
        User user = (User) session.getAttribute(USER);
        String username = user.getUserName();
        int user_id = user.getId();
        try {
            Comment comment = new Comment();
            comment.setComment_text(comment_text);
            comment.setMovie_id(movie_id);
            comment.setUser_id(user_id);
            comment.setUsername(username);
            if (commentService.insert(comment)) {
                Optional<Movie> optionalMovie = movieService.getById((long) movie_id);
                Optional<Rating> optionalRating = ratingService.getRatingByMovieId((long) movie_id);
                List<Comment> commentList = commentService.findAllCommentByMovieId(movie_id);
                Movie movie = optionalMovie.get();
                Rating rating = optionalRating.get();
                request.setAttribute(MOVIE_INFO, movie);
                request.setAttribute(RATING_INFO, rating);
                request.setAttribute(COMMENT_LIST, commentList);
                session.setAttribute(CURRENT_PAGE, PagePath.MOVIE_INFO_USER_PAGE);
                logger.log(Level.INFO, ROUTER_LOG, router);
                return router;
            } else {
                logger.error(NOT_REGISTERED_COMMENT_LOG);
                throw new CommandException(NOT_REGISTERED_COMMENT_LOG);
            }
        } catch (ServiceException serviceException) {
            logger.error(ERROR_POST_COMMENT_LOG, serviceException);
            throw new CommandException(serviceException);
        }
    }
}
