package main.project.movie.controller;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.CommandType;
import main.project.movie.controller.command.Router;
import main.project.movie.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.path.PagePath.*;
import java.io.IOException;

@WebServlet(name = "Controller",urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        realizeRequest(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        realizeRequest(request,response);
    }
    private void realizeRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("text/html");
        String commandStr = request.getParameter(COMMAND);
        logger.info(COMMAND_LOG+commandStr);
        Command command = CommandType.define(commandStr);
        try {
            Router router = command.execute(request);
            logger.info(CURRENT_PAGE_LOG+router.getPage());
            if(router.getActionType().equals(Router.Type.FORWARD)) {
                request.getRequestDispatcher(router.getPage()).forward(request,response);
            }else {
                response.sendRedirect(request.getContextPath() + router.getPage());
            }
        } catch (CommandException e) {
            request.setAttribute(ERROR_MSG, e.getCause());
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
