package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.User;
import main.project.movie.entity.type.Status;
import main.project.movie.entity.type.UserRole;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import java.util.Optional;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        boolean isMatched;
        String userName = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        Router router;
        UserServiceImpl userService = UserServiceImpl.getInstance();
        session.setAttribute(CURRENT_PAGE, INDEX_PAGE);
        try {
                isMatched = userService.loginAuthenticate(userName, password);
                if (isMatched) {
                    UserRole userRole = userService.findUserRole(userName);
                    Status userStatus = userService.findUserStatus(userName);
                    Optional<User> optionalUser = userService.findByLogin(userName);
                    optionalUser.ifPresent(user -> session.setAttribute(USER, user));
                    optionalUser.ifPresent(user -> session.setAttribute(USERID, user.getId()));
                    request.setAttribute(USERNAME, userName);
                    session.setAttribute(USERNAME, userName);
                    session.setAttribute(USER_ROLE, userRole);
                    session.setAttribute(USER_STATUS, userStatus);

                    if (session.getAttribute(USER_STATUS) == Status.BLOCKED) {
                        logger.error(USERNAME_BLOCKED_LOG,  userName);
                        request.setAttribute(LOGIN_MESSAGE, PROFILE_BLOCKED);
                        router = new Router(PagePath.LOGIN_PAGE, Router.Type.FORWARD);
                    } else if (session.getAttribute(USER_ROLE) == UserRole.ADMIN) {
                        session.setAttribute(CURRENT_PAGE, PagePath.ADMIN_PAGE);
                        logger.info(USER_IS_ADMIN_LOG,  userName);
                        request.setAttribute(LOGIN_MESSAGE, LOGIN_SUCCESS);
                        router = new GetMoviesByCategoryCommand().execute(request);
                        router.setPage(PagePath.ADMIN_PAGE);
                        router.setActionType(Router.Type.FORWARD);
                    } else {
                        session.setAttribute(CURRENT_PAGE, PagePath.HOME_PAGE);
                        request.setAttribute(LOGIN_MESSAGE, LOGIN_SUCCESS);
                        router = new Router(PagePath.HOME_PAGE, Router.Type.FORWARD);
                    }
                } else {
                    request.setAttribute(LOGIN_MESSAGE, INCORRECT);
                    router = new Router(PagePath.INDEX_PAGE, Router.Type.FORWARD);
                }
            logger.log(Level.INFO,ROUTER_LOG, router);
            return router;
        } catch (ServiceException serviceException) {
            logger.info(ERROR_LOGIN_LOG,  serviceException);
            throw new CommandException(serviceException);
        }
    }
}
