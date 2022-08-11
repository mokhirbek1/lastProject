package main.project.movie.validation.attribute;

public final class ValidationAttribute {
    public static final int MOVIE_NAME_LENGTH_FROM = 3;
    public static final int MOVIE_NAME_LENGTH_TO = 50;
    public static final int COUNTRY_NAME_LENGTH_FROM = 3;
    public static final int COUNTRY_NAME_LENGTH_TO = 45;
    public static final int CREATED_YEAR_FROM = 1930;
    public static final int CREATED_YEAR_TO = 1930;
    public static final int AGE_LIMIT_FROM = 1;
    public static final int AGE_LIMIT_TO = 22;
    public static final int DESCRIPTION_TEXT_LENGTH = 22;
    public static final int IMAGE_URL_LENGTH_FROM = 10;
    public static final int IMAGE_URL_LENGTH_TO = 500;
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$";
    public static final String USERNAME_REGEX = "^[a-z0-9_-]{3,16}$";
    public static final String EMAIL_REGEX = "^(\\w\\.?)+@[\\w\\.-]+\\.\\w{2,4}";
    public static final String NAME_REGEX = "^[A-Za-z]{5,10}$";

    private ValidationAttribute(){}
}
