package main.project.movie.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.validation.attribute.ValidationAttribute.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUser {
    private static final Logger logger = LogManager.getLogger();
    public static boolean passwordSuitable(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = passwordPattern.matcher(password);
        boolean result = matcher.matches();
        logger.info(result ? PSW_SUITABLE_LOG : PSW_NOT_SUITABLE_LOG);
        return result;
    }
    public static boolean loginSuitable(String username) {
        Pattern loginPattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = loginPattern.matcher(username);
        boolean result = matcher.matches();
        logger.info(result ? LOGIN_SUITABLE_LOG : LOGIN_NOT_SUITABLE_LOG);
        return result;
    }
    public static boolean emailSuitable(String email) {
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = emailPattern.matcher(email);
        boolean result = matcher.matches();
        logger.info(result ? EMAIL_SUITABLE_LOG : EMAIL_NOT_SUITABLE_LOG);
        return result;
    }
    public static boolean nameSuitable(String firstname) {
        Pattern firstnamePattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = firstnamePattern.matcher(firstname);
        boolean result = matcher.matches();
        logger.info(result ? NAME_SUITABLE_LOG : NAME_NOT_SUITABLE_LOG);
        return result;
    }
}
