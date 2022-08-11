package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.entity.Movie;
import main.project.movie.entity.type.Category;
import main.project.movie.exception.CommandException;
import main.project.movie.exception.ServiceException;
import main.project.movie.service.impl.MovieServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import static main.project.movie.controller.attribute.AttributeName.*;
import java.util.ArrayList;
import java.util.List;
import static main.project.movie.controller.message.LogMessage.*;

public class GetMoviesByCategoryCommand implements Command {
    MovieServiceImpl movieService = MovieServiceImpl.getInstance();
    List<Movie> movieList = new ArrayList<>();
    List<Movie> newList = new ArrayList<>();
    List<Movie> filmList = new ArrayList<>();
    List<Movie> bestList = new ArrayList<>();
    List<Movie> adventureList = new ArrayList<>();
    List<Movie> serialList = new ArrayList<>();
    List<Movie> cartoonList = new ArrayList<>();
    List<Movie> comedyList = new ArrayList<>();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            movieList = movieService.findAll();
            for (Movie movie : movieList) {
                if (movie.getCategory().equals(Category.NEW)) {
                    logger.log(Level.INFO, NEW_MOVIE_LOG, movie.getName());
                    newList.add(movie);
                } else if (movie.getCategory().equals(Category.FILM)) {
                    logger.log(Level.INFO, FILM_LOG, movie.getName());
                    filmList.add(movie);
                } else if (movie.getCategory().equals(Category.BEST)) {
                    logger.log(Level.INFO, BEST_MOVIE_LOG, movie.getName());
                    bestList.add(movie);
                } else if (movie.getCategory().equals(Category.ADVENTURE)) {
                    logger.log(Level.INFO, ADVENTURE_MOVIE_LOG, movie.getName());
                    adventureList.add(movie);
                } else if (movie.getCategory().equals(Category.SERIALS)) {
                    logger.log(Level.INFO, SERIAL_MOVIE_LOG, movie.getName());
                    serialList.add(movie);
                } else if (movie.getCategory().equals(Category.CARTOON)) {
                    logger.log(Level.INFO, CARTOON_MOVIE_LOG, movie.getName());
                    cartoonList.add(movie);
                } else if (movie.getCategory().equals(Category.COMEDY)) {
                    logger.log(Level.INFO, COMEDY_MOVIE_LOG, movie.getName());
                    comedyList.add(movie);
                }
            }
            logger.log(Level.INFO, SUCCESSFULLY_FOUND_MOVIE_LOG);
            request.setAttribute(NEW_LIST, newList);
            request.setAttribute(BEST_LIST, bestList);
            request.setAttribute(ADVENTURE_LIST, adventureList);
            request.setAttribute(SERIAL_LIST, serialList);
            request.setAttribute(CARTOON_LIST, cartoonList);
            request.setAttribute(FILM_LIST, filmList);
            request.setAttribute(COMEDY_LIST, comedyList);
            request.setAttribute(MOVIE_LIST, movieList);
            return new Router();
        } catch (ServiceException serviceException) {
            logger.error(ERROR_GETTING_MOVIE_BY_CATEGORY_LOG, serviceException);
            throw new CommandException(serviceException);
        }
    }
}
