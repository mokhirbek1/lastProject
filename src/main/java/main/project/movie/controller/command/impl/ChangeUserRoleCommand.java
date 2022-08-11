package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.entity.type.UserRole;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;

public class ChangeUserRoleCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String returnPage = request.getParameter(PAGE);
        String user_id = request.getParameter(USER_ID);
        UserRole role = UserRole.valueOf(String.valueOf(request.getParameter(ROLE)).toUpperCase());
        Router router;
        try {
            if (userService.changeRole(Integer.parseInt(user_id), role)) {
                if (returnPage.equals(USER_PAGE)) {
                    logger.log(Level.INFO, ADDED_USER_LOG, user_id);
                    router = new FindUsersCommand().execute(request);
                } else {
                    logger.log(Level.INFO, CHANGED_ADMIN_ROLE_LOG, user_id);
                    router = new FindAdminsCommand().execute(request);
                }
            } else {
                if (returnPage.equals(USER_PAGE)) {
                    logger.log(Level.DEBUG, NOT_ADDED_USER_LOG, user_id);
                    router = new FindUsersCommand().execute(request);
                } else {
                    logger.log(Level.DEBUG, NOT_CHANGED_ADMIN_ROLE_LOG, user_id);
                    router = new FindAdminsCommand().execute(request);
                }
            }
            logger.log(Level.INFO, ROUTER_LOG, router);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, ERROR_ADDING_ADMIN_LOG, e);
            throw new CommandException(e);
        }
        return router;
    }
}
