package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.User;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class UpdatePasswordByEmailCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String new_password = (String) request.getAttribute(NEW_PASSWORD);
        String email = (String)request.getAttribute(EMAIL);
        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(new_password);
            if (userService.update(user)){
                request.setAttribute(MESSAGE, RESET_SUCCESS);
                router = new Router(PagePath.LOGIN_PAGE, Router.Type.FORWARD);
            }else{
                request.setAttribute(EMAIL, RESET_FAILED);
                router = new Router(PagePath.NEW_PASSWORD_PAGE, Router.Type.FORWARD);
            }
            logger.log(Level.INFO,ROUTER_LOG,router);
            return router;
        } catch (ServiceException serviceException) {
            logger.error(ERROR_UPDATING_PSW_LOG,serviceException);
            throw new CommandException(serviceException);
        }
    }
}
