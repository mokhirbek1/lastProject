<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"
            integrity="sha512-6PM0qYu5KExuNcKt5bURAoT6KCThUmHRewN3zUFNaoI6Di7XJPTMoT6K0nsagZKk2OB4L7E3q1uQKHNHd4stIQ=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <style>
        <%@include file="/css/style.css" %>
    </style>
    <style>
        <%@include file="/css/navbar.css" %>
    </style>
</head>
<body>
<input type="hidden" id="login_message" value="<%= request.getAttribute("login_message") %>"/>

<nav class="container-movie-navbar">
    <div class="container-navbar">
        <div class="nav-link-left">
            <form action="${pageContext.request.contextPath}/controller.do">
                <input type="hidden" name="command" value="user_    home_page"/>
                <input type="submit" class="nav-link-home" value="Home page"/>
            </form>
        </div>
        <form class="nav-link-logout" action="${pageContext.request.contextPath}/controller.do">
            <input type="hidden" name="command" value="logout">
            <input class="nav-link-logout" type="submit" value="Logout">
        </form>
    </div>
</nav>

<%-----------------------------Carousel_1---------------------------%>
<span class="movie-title d-inline-block" tabindex="0" id="new" data-bs-toggle="tooltip" title="Отключенная подсказка">
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_movie_category_page">
        <input type="hidden" name="movie_category" value="new">
        <input class="btn btn-primary" type="submit" value="New>">
    </form>
</span>
<div id="carouselExampleCaptions1" class="main-movies carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner ">
        <div class="carousel-item active" data-bs-interval="3000000">
            <img style="width: 800px; height: 300px;"
                 src="https://whatsappstatusboss.com/wp-content/uploads/2021/02/best-movies-of-all-time.png">
            <div class="carousel-caption d-md-block">
                <h1 style="color:#f63b3b; font-size: 100px">New Films</h1>
                <h5 style="color:#fff; font-size: 45px">Swipe to left <i class="fa-solid fa-angles-right"></i></h5>
            </div>
        </div>
        <c:forEach var="new_films" items="${new_list}">
            <div class="carousel-item" data-bs-interval="3000000">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="movie_info_page"/>
                    <button type="submit" value="${new_films.id}" name="id">
                        <img src="${new_films.image_path}" class="d-block w-100 new-movie-img" alt="movie-image"/>
                    </button>
                </form>
                <div class="carousel-caption d-md-block">
                    <h5 class="movie-name" title="${new_films.name}">${new_films.name}</h5>
                    <div class="movie-rating">
                        <c:forEach begin="1" end="${new_films.rating_value}" step="1">
                            <span style="font-size:20px;color:yellow;">&starf;</span>
                        </c:forEach>
                    </div>
                    <h5 class="movie-age-limit">${new_films.age_limit}+</h5>
                </div>
           </div>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Предыдущий</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Следующий</span>
    </button>
</div>
<%-----------------------------Carousel_2---------------------------%>
<span class="movie-title d-inline-block" tabindex="0" id="best" data-bs-toggle="tooltip" title="Отключенная подсказка">
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_movie_category_page">
        <input type="hidden" name="movie_category" value="best">
        <input class="btn btn-primary" type="submit" value="Best>">
    </form>
</span>
<div id="carouselExampleCaptions2" class="main-movies carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner ">
        <div class="carousel-item active" data-bs-interval="3000">
            <img style="width: 800px; height: 300px;"
                 src="https://pluggedin.ru/images/3-bigTopImage_2019_12_14_12_06_22.jpg"
                 alt="...">
            <div class="carousel-caption d-md-block">
                <h1 style="color:#fff; font-size: 100px">Best Films</h1>
                <h5 style="color:#fff; font-size: 45px">Swipe to left <i class="fa-solid fa-angles-right"></i></h5>
            </div>
        </div>
        <c:forEach var="best" items="${best_list}">
            <div class="carousel-item" data-bs-interval="3000">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="movie_info_page"/>
                    <button type="submit" value="${best.id}" name="id">
                        <img src="${best.image_path}" class="d-block w-100 new-movie-img" alt="movie-image"/>
                    </button>
                </form>
                <div class="carousel-caption d-md-block">
                    <h5 class="movie-name" title="${best.name}">${best.name}</h5>
                    <div class="movie-rating">
                        <c:forEach begin="1" end="${best.rating_value}" step="1">
                            <span style="font-size:20px;color:yellow;">&starf;</span>
                        </c:forEach>
                    </div>
                    <h5 class="movie-age-limit">${best.age_limit}+</h5>
                </div>
            </div>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions2" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Предыдущий</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions2" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Следующий</span>
    </button>
</div>
<%-----------------------------Carousel_3---------------------------%>
<span class="movie-title d-inline-block" tabindex="0" id="adventure" data-bs-toggle="tooltip"
      title="Отключенная подсказка">
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_movie_category_page">
        <input type="hidden" name="movie_category" value="adventure">
        <input class="btn btn-primary" type="submit" value="Adventure>">
    </form>
</span>
<div id="carouselExampleCaptions3" class="main-movies carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner ">
        <div class="carousel-item active" data-bs-interval="3000">
            <img src="https://cs14.pikabu.ru/post_img/big/2022/01/04/11/164132034215446936.jpg" class="new-movie-img" alt="...">
            <div class="carousel-caption d-md-block">
                <h1 style="color:#fff; font-size: 100px">Adventure</h1>
                <h5 style="color:#fff; font-size: 45px">Swipe to left <i class="fa-solid fa-angles-right"></i></h5>
            </div>
        </div>
        <c:forEach var="adventure" items="${adventure_list}">
            <div class="carousel-item" data-bs-interval="3000">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="movie_info_page"/>
                    <button type="submit" value="${adventure.id}" name="id">
                        <img src="${adventure.image_path}" class="d-block w-100 new-movie-img" alt="movie-image"/>
                    </button>
                </form>
                <div class="carousel-caption d-md-block">
                    <h5 class="movie-name" title="${adventure.name}">${adventure.name}</h5>
                    <div class="movie-rating">
                        <c:forEach begin="1" end="${adventure.rating_value}" step="1">
                            <span style="font-size:20px;color:yellow;">&starf;</span>
                        </c:forEach>
                    </div>
                    <h5 class="movie-age-limit">${adventure.age_limit}+</h5>
                </div>
            </div>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions3" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Предыдущий</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions3" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Следующий</span>
    </button>
</div>
<%-----------------------------Carousel_4---------------------------%>
<span class="movie-title d-inline-block" tabindex="0" id="serials" data-bs-toggle="tooltip"
      title="Отключенная подсказка">
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_movie_category_page">
        <input type="hidden" name="movie_category" value="serials">
        <input class="btn btn-primary" type="submit" value="Serials>">
    </form>
</span>
<div id="carouselExampleCaptions4" class="main-movies carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner ">
        <div class="carousel-item active" data-bs-interval="3000">
            <img src="https://cs14.pikabu.ru/post_img/big/2021/09/21/11/1632249347134222535.jpg" class="new-movie-img" alt="...">
            <div class="carousel-caption d-md-block">
                <h1 style="color:#fff; font-size: 100px">Serials</h1>
                <h5 style="color:#fff; font-size: 45px">Swipe to left <i class="fa-solid fa-angles-right"></i></h5>
            </div>
        </div>
        <c:forEach var="serial" items="${serial_list}">
            <div class="carousel-item" data-bs-interval="3000">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="movie_info_page"/>
                    <button type="submit" value="${serial.id}" name="id">
                        <img src="${serial.image_path}" class="d-block w-100 new-movie-img" alt="movie-image"/>
                    </button>
                </form>
                <div class="carousel-caption d-md-block">
                    <h5 class="movie-name" title="${serial.name}">${serial.name}</h5>
                    <div class="movie-rating">
                        <c:forEach begin="1" end="${serial.rating_value}" step="1">
                            <span style="font-size:20px;color:yellow;">&starf;</span>
                        </c:forEach>
                    </div>
                    <h5 class="movie-age-limit">${serial.age_limit}+</h5>
                </div>
            </div>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions4" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Предыдущий</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions4" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Следующий</span>
    </button>
</div>
<%-----------------------------Carousel_5---------------------------%>
<span class="movie-title d-inline-block" tabindex="0" id="cartoon" data-bs-toggle="tooltip"
      title="Отключенная подсказка">
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_movie_category_page">
        <input type="hidden" name="movie_category" value="cartoon">
        <input class="btn btn-primary" type="submit" value="Cartoon>">
    </form>
</span>
<div id="carouselExampleCaptions5" class="main-movies carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner ">
        <div class="carousel-item active" data-bs-interval="3000">
            <img style="width: 800px;height: 300px;" src="http://images6.fanpop.com/image/photos/34500000/Epic-epic-2013-34591475-1465-580.png" alt="...">
            <div class="carousel-caption d-md-block">
                <h1 style="color:#fff; font-size: 100px">Cartoon</h1>
                <h5 style="color:#fff; font-size: 45px">Swipe to left <i class="fa-solid fa-angles-right"></i></h5>
            </div>
        </div>
        <c:forEach var="cartoon" items="${cartoon_list}">
            <div class="carousel-item" data-bs-interval="3000">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="movie_info_page"/>
                    <button type="submit" value="${cartoon.id}" name="id">
                        <img src="${cartoon.image_path}" class="d-block w-100 new-movie-img" alt="movie-image"/>
                    </button>
                </form>
                <div class="carousel-caption d-md-block">
                    <h5 class="movie-name" title="${cartoon.name}">${cartoon.name}</h5>
                    <div class="movie-rating">
                        <c:forEach begin="1" end="${cartoon.rating_value}" step="1">
                            <span style="font-size:20px;color:yellow;">&starf;</span>
                        </c:forEach>
                    </div>
                    <h5 class="movie-age-limit">${cartoon.age_limit}+</h5>
                </div>
            </div>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions5" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Предыдущий</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions5" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Следующий</span>
    </button>
</div>
<%-----------------------------Carousel_6---------------------------%>
<span class="movie-title d-inline-block" tabindex="0" id="films" data-bs-toggle="tooltip" title="Отключенная подсказка">
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_movie_category_page">
        <input type="hidden" name="movie_category" value="films">
        <input class="btn btn-primary" type="submit" value="Films>">
    </form>
</span>
<div id="carouselExampleCaptions6" class="main-movies carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner ">
        <div class="carousel-item active" data-bs-interval="3000">
            <img src="https://cs13.pikabu.ru/post_img/big/2021/03/28/7/1616931425157880148.jpg" class="new-movie-img" alt="...">
            <div class="carousel-caption d-md-block">
                <h1 style="color:#fff; font-size: 100px">Films</h1>
                <h5 style="color:#fff; font-size: 45px">Swipe to left <i class="fa-solid fa-angles-right"></i></h5>
            </div>
        </div>
        <c:forEach var="film" items="${film_list}">
            <div class="carousel-item" data-bs-interval="3000">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="movie_info_page"/>
                    <button type="submit" value="${film.id}" name="id">
                        <img src="${film.image_path}" class="d-block w-100 new-movie-img" alt="movie-image"/>
                    </button>
                </form>
                <div class="carousel-caption d-md-block">
                    <h5 class="movie-name" title="${film.name}">${film.name}</h5>
                    <div class="movie-rating">
                        <c:forEach begin="1" end="${film.rating_value}" step="1">
                            <span style="font-size:20px;color:yellow;">&starf;</span>
                        </c:forEach>
                    </div>
                    <h5 class="movie-age-limit">${film.age_limit}+</h5>
                </div>
            </div>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions6" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Предыдущий</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions6" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Следующий</span>
    </button>
</div>
<%-----------------------------Carousel_7---------------------------%>
<span class="movie-title d-inline-block" tabindex="0" id="comedy" data-bs-toggle="tooltip"
      title="Отключенная подсказка">
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_movie_category_page">
        <input type="hidden" name="movie_category" value="comedy">
        <input class="btn btn-primary" type="submit" value="Comedy>">
    </form>
</span>
<div id="carouselExampleCaptions7" class="main-movies carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner ">
        <div class="carousel-item active" data-bs-interval="3000">
            <img src="https://cs12.pikabu.ru/post_img/big/2021/10/05/11/1633458624164197645.jpg" class="new-movie-img" alt="...">
            <div class="carousel-caption d-md-block">
                <h1 style="color:#fff; font-size: 100px">Comedy</h1>
                <h5 style="color:#fff; font-size: 45px">Swipe to left <i class="fa-solid fa-angles-right"></i></h5>
            </div>
        </div>
        <c:forEach var="comedy" items="${comedy_list}">
            <div class="carousel-item" data-bs-interval="3000">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="movie_info_page"/>
                    <button type="submit" value="${comedy.id}" name="id">
                        <img src="${comedy.image_path}" class="d-block w-100 new-movie-img" alt="movie-image"/>
                    </button>
                </form>
                <div class="carousel-caption d-md-block">
                    <h5 class="movie-name" style="color:#ef4e4e;" title="${comedy.name}">${comedy.name}</h5>
                    <div class="movie-rating">
                        <c:forEach begin="1" end="${comedy.rating_value}" step="1">
                            <span style="font-size:20px;color:yellow;">&starf;</span>
                        </c:forEach>
                    </div>
                    <h5 class="movie-age-limit">${comedy.age_limit}+</h5>
                </div>
            </div>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions7" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Предыдущий</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions7" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Следующий</span>
    </button>
</div>

<footer class="home-page-footer">
    <div class="card text-center" >
        <div class="card-header">
            Featured
        </div>
        <div class="card-body">
            <h5 class="card-title">Special title treatment</h5>
            <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
            <form action="${pageContext.request.contextPath}/controller.do">
                <input type="hidden" name="command" value="home_page"/>
                <input type="submit" class="nav-link-home" value="Home page"/>
            </form>
        </div>
        <div class="card-footer text-muted">
            2 days ago
        </div>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
        crossorigin="anonymous"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="alert/dist/sweetalert.css">
<script type="text/javascript">
    var login_status = document.getElementById("login_message").value;
    if (login_status == "login_success") {
        swal("Great", "You have successfully logged in", "success");
    }
</script>
</body>
</html>
