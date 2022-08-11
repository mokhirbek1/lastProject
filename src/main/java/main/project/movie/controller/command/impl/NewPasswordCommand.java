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
public class NewPasswordCommand implements Command {
    private static Router router = new Router();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(EMAIL);
        String newPassword = request.getParameter(PASSWORD);
        String confPassword = request.getParameter(CONF_PASSWORD);
        if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {
            try {
                request.setAttribute(NEW_PASSWORD, newPassword);
                request.setAttribute(EMAIL, email);
                router = new UpdatePasswordByEmailCommand().execute(request);
            } catch (CommandException commandException) {
                logger.error(ERROR_UPDATING_PSW_LOG,commandException);
                throw new CommandException(ERROR_UPDATING_PSW_LOG,commandException);
            }
        } else {
            request.setAttribute(STATUS, NOT_MATCHES_PSW);
            logger.error(NOT_MATCHES_PSW_LOG);
            router = new Router(PagePath.NEW_PASSWORD_PAGE, Router.Type.FORWARD);
        }
        logger.log(Level.INFO,ROUTER_LOG,router);
        return router;
    }
}
