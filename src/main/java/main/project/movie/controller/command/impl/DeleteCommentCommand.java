package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.CommentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;

public class DeleteCommentCommand implements Command {
    private static Router router = null;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        CommentServiceImpl commentService = new CommentServiceImpl();
        long comment_id = Long.parseLong(request.getParameter(COMMENT_ID));
        String movie_id = request.getParameter(ID);
        String comment_text = request.getParameter(COMMENT_TEXT);
        logger.log(Level.INFO, COMMENT_ID_LOG, comment_id);
        logger.log(Level.INFO, COMMENT_TEXT_LOG, comment_text);
        logger.log(Level.INFO, MOVIE_ID_LOG, movie_id);
        try {
            if (commentService.delete(comment_id)) {
                logger.log(Level.INFO, COMMENT_DELETED_LOG, comment_id);
                request.setAttribute(ID, movie_id);
                router = new MovieInfoPageCommand().execute(request);
            } else {
                logger.log(Level.DEBUG, COMMENT_NOT_DELETED_LOG, comment_id);
                request.setAttribute(ID, movie_id);
                router = new MovieInfoPageCommand().execute(request);
            }
            logger.log(Level.INFO, ROUTER_LOG, router);
            return router;
        } catch (ServiceException serviceException) {
            logger.error(ERROR_DELETING_COMMENT_LOG, serviceException);
            throw new CommandException(ERROR_DELETING_COMMENT_LOG, serviceException);
        }
    }
}
