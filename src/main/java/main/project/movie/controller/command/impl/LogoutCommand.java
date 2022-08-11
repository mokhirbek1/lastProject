package main.project.movie.controller.command.impl;


import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import main.project.movie.controller.path.PagePath;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;
public class LogoutCommand implements Command {
    private Router router = new Router();
    @Override
    public Router execute(HttpServletRequest request) {
        logger.log(Level.INFO, USER_LOGGED_OUT_LOG);
        request.getSession().invalidate();
        router = new Router(PagePath.INDEX_PAGE, Router.Type.REDIRECT);
        logger.log(Level.INFO,ROUTER_LOG,router);
        return router;
    }
}
