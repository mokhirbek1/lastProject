package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.entity.type.Status;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.entity.User;
import main.project.movie.entity.type.UserRole;
import main.project.movie.service.UserService;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;

public class RegistrationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        String email = request.getParameter(EMAIL);
        String firstname = request.getParameter(FIRSTNAME);
        String lastname = request.getParameter(LASTNAME);
        String username = request.getParameter(REG_USERNAME);
        String password = request.getParameter(REG_PASSWORD);
        String repeated_password = request.getParameter(RE_PASSWORD);
        if (!password.equals(repeated_password)) {
            logger.info(NOT_MATCHES_PSW_LOG);
            request.setAttribute(REG_MESSAGE, NOT_MATCHES_PSW_REG);
            return new Router(PagePath.REGISTER_PAGE, Router.Type.FORWARD);
        }
            User user = new User();
            try {
                if (userService.loginSuitable(username)) {
                    user.setUserName(username);
                } else {
                    request.setAttribute(REG_MESSAGE, EXIST_LOGIN);
                    logger.info(EXIST_LOGIN_LOG);
                    return new Router(PagePath.REGISTER_PAGE, Router.Type.FORWARD);
                }
                if (userService.emailSuitable(email)) {
                    user.setEmail(email);
                } else {
                    request.setAttribute(REG_MESSAGE, email+ EXIST_EMAIL);
                    logger.info(EXIST_EMAIL_LOG);
                    return new Router(PagePath.REGISTER_PAGE, Router.Type.FORWARD);
                }
                if (username.equals(ADMIN_USERNAME)) {
                    user.setRole(UserRole.ADMIN);
                } else {
                    user.setRole(UserRole.USER);
                }
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setPassword(password);
                user.setStatus(Status.ACTIVE);
                request.setAttribute(USER, user);
                if (userService.insert(user)) {
                    if (username.equals(ADMIN_USERNAME)) {
                        request.setAttribute(REG_MESSAGE, SUCCESS);
                        router = new Router(PagePath.LOGIN_PAGE, Router.Type.FORWARD);
                    }
                    request.setAttribute(REG_MESSAGE, SUCCESS);
                    router = new Router(PagePath.LOGIN_PAGE, Router.Type.FORWARD);
                } else {
                    request.setAttribute(REG_MESSAGE, ERROR);
                    router = new Router(PagePath.REGISTER_PAGE, Router.Type.FORWARD);
                }
            } catch (ServiceException e) {
                logger.error(ERROR_REGISTR_USER_LOG, e);
                throw new CommandException(e);
            }
        logger.log(Level.INFO,ROUTER_LOG,router);
        return router;
    }
}
