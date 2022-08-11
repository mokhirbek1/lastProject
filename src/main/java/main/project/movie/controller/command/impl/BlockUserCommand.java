package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;

public class BlockUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = null;
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String returnPage = request.getParameter(PAGE);
        String user_id = request.getParameter(ID);
        logger.info(USER_ID_LOG, user_id);

        try {
            if (userService.blockUserById(Integer.parseInt(user_id))) {
                if (returnPage.equals(USER_PAGE)) {
                    router = new FindUsersCommand().execute(request);
                    logger.info(USER_BLOCKED_LOG, user_id);
                } else {
                    logger.info(ADMIN_BLOCKED_LOG, user_id);
                    router = new FindAdminsCommand().execute(request);
                }
            } else {
                if (returnPage.equals(USER_PAGE)) {
                    logger.info(USER_NOT_BLOCKED_LOG, user_id);
                    router = new FindUsersCommand().execute(request);
                } else {
                    logger.info(ADMIN_NOT_BLOCKED_LOG, user_id);
                    router = new FindAdminsCommand().execute(request);
                }
            }
        } catch (ServiceException serviceException) {
            logger.error(ERROR_BLOCKING_USER_LOG, user_id, serviceException);
            throw new CommandException(serviceException);
        }
        logger.info(ROUTER_LOG, router);
        return router;
    }
}
