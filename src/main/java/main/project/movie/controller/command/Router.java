package main.project.movie.controller.command;

import static main.project.movie.controller.path.PagePath.INDEX_PAGE;

public class Router {

    private String page;
    private Type actionType;
    public enum Type {
        FORWARD,
        REDIRECT;
    }

    public Router() {
        this.actionType = Type.FORWARD;
        this.page = INDEX_PAGE;
    }

    public Router(String page, Type actionType) {
        this.page = page;
        this.actionType = actionType;
    }

    public Router(String page) {
        this.page = page;
        this.actionType = Type.FORWARD;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Type getActionType() {
        return actionType;
    }

    public void setActionType(Type actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
       return builder.append("Router{ ")
               .append("page= "+page)
               .append("actionType= "+actionType)
               .append("}")
               .toString();
    }
}
