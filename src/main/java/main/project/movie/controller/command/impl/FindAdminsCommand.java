package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.User;
import main.project.movie.entity.type.UserRole;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import java.util.List;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class FindAdminsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        int userid =  (int)session.getAttribute(USERID);
        logger.log(Level.INFO, CURRENT_ADMIN_LOG, userid);
        Router router;
        try {
            List<User> admins = userService.findUsersByRole(UserRole.ADMIN);
            request.setAttribute(ADMINS, admins);
            request.setAttribute(USERID, userid);
            router = new Router(PagePath.ADMINS_PAGE,Router.Type.FORWARD);
            logger.log(Level.INFO,ROUTER_LOG, router);
            return router;
        }catch (ServiceException serviceException) {
            logger.error(ERROR_GETTING_ADMINS_LOG, serviceException);
            throw new CommandException(serviceException);
        }
    }
}
