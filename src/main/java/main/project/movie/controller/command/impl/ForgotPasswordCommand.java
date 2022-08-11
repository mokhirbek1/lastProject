package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.controller.path.PagePath;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;
import java.util.List;

public class ForgotPasswordCommand implements Command {
    private static Router router = new Router();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String userEmail = request.getParameter(EMAIL);
        if (userEmail !=null || !userEmail.equals("")){
            try {
                List<String> userInfo = userService.checkEmail(userEmail);
                if (userInfo.isEmpty()){
                    logger.log(Level.INFO, USER_NOT_EXIST_LOG, userEmail);
                    router = new Router(PagePath.FORGOT_PASSWORD_PAGE, Router.Type.FORWARD);
                }else {
                    logger.log(Level.INFO, USER_EXIST_LOG, userEmail, " ", userInfo);
                    request.setAttribute(EMAIL, userEmail);
                    request.setAttribute(FIRSTNAME, userInfo.get(0));
                    request.setAttribute(LASTNAME, userInfo.get(1));
                    router = new SendConfirmPasswordCommand().execute(request);
                }
                logger.log(Level.INFO,ROUTER_LOG, router);
                return router;
            } catch (ServiceException e) {
                logger.log(Level.ERROR, ERROR_CHECKING_EMAIL_LOG, userEmail);
                throw new CommandException(e);
            }
        }else {
            logger.log(Level.DEBUG,EMAIL_EMPTY_LOG);
            router = new Router(PagePath.FORGOT_PASSWORD_PAGE, Router.Type.FORWARD);
            logger.log(Level.INFO,ROUTER_LOG, router);
            return router;
        }
    }
}
