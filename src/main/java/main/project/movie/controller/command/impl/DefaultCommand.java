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

public class DefaultCommand implements Command {
    private static Router router = null;
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        try {
            Router router = new Router(PagePath.INDEX_PAGE, Router.Type.FORWARD);
            logger.info(ROUTER_VALUE_LOG, router);
            session.setAttribute(CURRENT_PAGE, PagePath.INDEX_PAGE);
            logger.log(Level.INFO, ROUTER_LOG, router);
            return router;
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error(DEFAULT_EXCEPTION_LOG, illegalArgumentException);
            throw new CommandException(illegalArgumentException);
        }

    }
}
