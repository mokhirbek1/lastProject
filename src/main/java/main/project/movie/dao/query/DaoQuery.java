package main.project.movie.dao.query;

public final class DaoQuery {
    /*COMMENT DAO QUERY*/
    public static final String SELECT_ALL_COMMENT = "SELECT comment_id,comment_text,user_id,movie_id, user_name FROM comments";
    public static final String INSERT_COMMENT = "INSERT INTO comments (comment_text, movie_id, user_id, username) VALUES(?,?,?,?)";
    public static final String DELETE_COMMENT_BY_ID = "DELETE FROM comments WHERE comment_id=?";
    public static final String FIND_COMMENT_BY_MOVIE_ID = "SELECT comment_id, comment_text, movie_id, user_id, username FROM comments WHERE movie_id = ?";
    public static final String UPDATE_COMMENT = "UPDATE comments SET comment_text=? WHERE comment_id=?";
    /*MOVIE DAO QUERY*/
    public static final String SELECT_ALL_MOVIE = "SELECT movie_id,movie_name,movie_country,created_year,category,age_limit,description,image_path, rating_value, language FROM movieproject.movies";
    public static final String INSERT_MOVIE_QUERY = "INSERT INTO movies (movie_name,movie_country,created_year,category,age_limit, description,image_path, language) VALUES(?,?,?,?,?,?,?,?)";
    public static final String DELETE_MOVIE_BY_ID = "DELETE FROM movies WHERE movie_id=?";
    public static final String FIND_MOVIE_BY_ID = "SELECT movie_id,movie_name,movie_country,created_year,category,language, age_limit,description,image_path, rating_value FROM movies WHERE movie_id = ?";
    public static final String EDIT_MOVIE = "UPDATE movies SET movie_name=?,movie_country=?,created_year=?,category=?,language =?, age_limit=?,description=?,image_path=? WHERE movie_id=?";
    public static final String CHECK_NAME = "SELECT movie_name FROM movies WHERE movie_name=?";
    public static final String FIND_ID_BY_NAME = "SELECT movie_id from movies WHERE movie_name = ?";
    public static final String FIND_BY_CATEGORY = "SELECT * FROM movieproject.movies WHERE category=?";
    public static final String UPDATE_MOVIE_RATING = "UPDATE movies SET rating_value=? WHERE movie_id=?";
    /*RATING DAO QUERY*/
    public static final String INSERT_RATING_QUERY = "INSERT INTO ratings (movie_id,rating_value) VALUES(?,?)";
    public static final String DELETE_RATING_BY_ID = "DELETE FROM ratings WHERE rating_id=?";
    public static final String DELETE_RATING_BY_MOVIE_ID = "DELETE FROM ratings WHERE movie_id=?";
    public static final String SELECT_ALL_RATING = "SELECT rating_id,movie_id, rating_value FROM ratings";
    public static final String FIND_RATING_BY_ID = "SELECT rating_id,movie_id, rating_value FROM ratings WHERE rating_id = ?";
    public static final String FIND_RATING_BY_MOVIE_ID = "SELECT rating_id,movie_id, rating_value FROM ratings WHERE movie_id = ?";
    public static final String UPDATE_VALUE = "UPDATE ratings SET rating_value=? WHERE movie_id=?";

    /* USER DAO QUERY*/
    public static final String INSERT_USER_QUERY = "INSERT INTO movieproject.users (email,role,first_name,last_name,user_name,password,status) VALUES(?,?,?,?,?,?,?)";
    public static final String CHANGE_ROLE = "UPDATE movieproject.users SET role = ? WHERE user_id = ?";
    public static final String CHECK_FOR_LOGIN = "SELECT password FROM movieproject.users WHERE user_name=?";
    public static final String CHECK_LOGIN = "SELECT first_name FROM movieproject.users WHERE users.user_name=?";
    public static final String CHECK_EMAIL = "SELECT first_name FROM movieproject.users WHERE users.email=?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM movieproject.users WHERE user_id = ?";
    public static final String SELECT_BY_LOGIN = "SELECT user_id,email,role,first_name,last_name,user_name,password,status FROM movieproject.users WHERE user_name = ?";
    public static final String FIND_USER_ROLE_BY_LOGIN = "SELECT role FROM movieproject.users WHERE user_name = ?";
    public static final String FIND_USER_STATUS_BY_LOGIN = "SELECT status FROM movieproject.users WHERE user_name = ?";
    public static final String FIND_BY_ID = "SELECT user_id,email,role,first_name,last_name,user_name,password,status FROM movieproject.users WHERE user_id = ?";
    public static final String FIND_BY_ROLE = "SELECT user_id,email,role,first_name,last_name,user_name,password,status FROM movieproject.users WHERE role = ?";
    public static final String UPDATE_PASSWORD_BY_EMAIL = "UPDATE movieproject.users SET password = ? WHERE email = ?";
    public static final String BLOCK_USER = "UPDATE movieproject.users SET status = 'BLOCKED' WHERE user_id = ?";
    public static final String ACTIVATE_USER = "UPDATE movieproject.users SET status = 'ACTIVE' WHERE user_id = ?";
    public static final String GET_INFO_BY_EMAIL = "SELECT first_name, last_name FROM movieproject.users WHERE email=?";

    private DaoQuery() {
    }
}
