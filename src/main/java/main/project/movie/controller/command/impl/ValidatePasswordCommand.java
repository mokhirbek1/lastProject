package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class ValidatePasswordCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        int confirm_password = Integer.parseInt(request.getParameter(CONFIRM_PASSWORD));
        HttpSession session = request.getSession();
        int sent_password = (int) session.getAttribute(SENT_PASSWORD);
        String user_email = (String) session.getAttribute(EMAIL);
        if (confirm_password == sent_password) {
            logger.log(Level.INFO, CONFIRM_PASSWORD_LOG);
            request.setAttribute(EMAIL, user_email);
            request.setAttribute(STATUS, SUCCESS);
            session.setAttribute(CURRENT_PAGE, PagePath.NEW_PASSWORD_PAGE);
            logger.log(Level.INFO, MOVED_TO_NEW_PSW_PAGE_LOG);
            router = new Router(PagePath.NEW_PASSWORD_PAGE, Router.Type.FORWARD);
        } else {
            logger.log(Level.INFO, CONFIRM_PASSWORD_NOT_MATCH_LOG);
            session.setAttribute(CURRENT_PAGE, PagePath.CONFIRM_PASSWORD_PAGE);
            logger.log(Level.INFO, MOVED_TO_CONFRM_PSW_PAGE_LOG);
            request.setAttribute(MESSAGE, WRONG_CONFRM_PSW_LOG);
            router = new Router(PagePath.CONFIRM_PASSWORD_PAGE, Router.Type.FORWARD);
        }
        logger.log(Level.INFO,ROUTER_LOG+router);
        return router;
    }
}
