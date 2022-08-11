package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class DeleteUserCommand implements Command {
    private static Router router = null;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String user_id = request.getParameter(ID);
        logger.info(USER_ID_LOG, user_id);
        try {
            if (userService.delete(Long.valueOf(user_id))) {
                logger.info(USER_DELETED_LOG, user_id);
                request.setAttribute(DELETING_MESSAGE, SUCCESS);
            } else {
                logger.info(USER_NOT_DELETED_LOG, user_id);
                request.setAttribute(DELETING_MESSAGE, FAIL);
            }
            router = new FindUsersCommand().execute(request);
            logger.log(Level.INFO, ROUTER_LOG, router);
            return router;
        } catch (ServiceException serviceException) {
            logger.error(ERROR_DELETING_LOG, user_id, serviceException);
            throw new CommandException(serviceException);
        }
    }
}