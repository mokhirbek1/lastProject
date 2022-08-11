package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;
public class ActivateUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = null;
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String adminPage = request.getParameter(PAGE);
        String user_id = request.getParameter(ID);
        logger.info(USER_ID_LOG, user_id);
        try {
            if (userService.activateUserById(Integer.parseInt(user_id))) {
                if (adminPage.equals(USER_PAGE)) {
                    router = new FindUsersCommand().execute(request);
                    logger.info(ACTIVATED_USER_LOG, user_id);
                } else {
                    router = new FindAdminsCommand().execute(request);
                    logger.info(ACTIVATED_ADMIN_LOG,user_id);
                }
            } else {
                logger.info(NOT_BLOCKED_USER_LOG, user_id);
            }
        } catch (ServiceException serviceException) {
            logger.error(FAILED_ACTIVATING_USER_LOG, user_id, serviceException);
            throw new CommandException(serviceException);
        }
        logger.info(ROUTER_LOG, router);
        return router;
    }
}
