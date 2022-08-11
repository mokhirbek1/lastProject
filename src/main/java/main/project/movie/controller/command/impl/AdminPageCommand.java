package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;

public class AdminPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        try {
            Router router = new GetMoviesByCategoryCommand().execute(request);
            session.setAttribute(CURRENT_PAGE, PagePath.ADMIN_PAGE);
            router.setPage(PagePath.ADMIN_PAGE);
            router.setActionType(Router.Type.FORWARD);
            logger.log(Level.INFO, ROUTER_LOG, router);
            return router;
        } catch (CommandException commandException) {
            logger.error(ERROR_GETTING_MOVIE_LOG, commandException);
            throw new CommandException(commandException);
        }
    }
}
