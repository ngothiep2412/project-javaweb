<%-- 
    Document   : login
    Created on : 01-03-2022, 18:19:32
    Author     : Thiep Ngo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Login</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/shopFavicon.ico" />
    <!-- Bootstrap icons-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles_login.css" rel="stylesheet" />
    <script src="https://www.google.com/recaptcha/api.js">
    </script>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card border-0 shadow rounded-3 my-5">
                        <div class="card-body p-4 p-sm-5">
                            <h3 class="card-title text-center mb-5 fw-light fw-bold fs-2">Sign In</h3>
                            <form action="MainController" method="POST">
                                <div class="form-floating mb-3">
                                    <input type="email" name="email" required="" class="form-control" id="email" placeholder="Email">
                                    <label for="email">Email</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="password" name="password" 
                                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one 
                                           number and one uppercase and lowercase letter, and at least 8 or more characters" placeholder="Password" required="">
                                    <label for="password">Password</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <div class="g-recaptcha form-floating mb-3" id="grecaptcha" data-sitekey="6LcmCb0eAAAAAIcF8f4TesyiQO7Qjz_9BmDUMEsD"></div>
                                    <c:if test="${requestScope.ERROR_RECAPTCHA != null}">
                                        <h3 style="color: red; font-size: 0.975rem">${requestScope.ERROR_RECAPTCHA}</h3>
                                    </c:if>
                                </div>

                                <div class="d-grid" style="color: #669237">
                                    <input class="btn btn-primary btn-login text-uppercase fw-bold"  type="submit" name="action" value="Login">
                                </div>
                                <c:if test="${requestScope.ERROR != null}">
                                    <h3 style="color: red; font-size: 0.975rem">${requestScope.ERROR}</h3>
                                </c:if>

                            </form>

                            <hr class="my-4">
                            <div class="d-grid mb-2">
                                <button class="btn btn-google btn-login text-uppercase fw-bold" type="submit">
                                    <a style="text-decoration: none; color: white" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/VegetableShop/LoginGoogleServlet&response_type=code
                                       &client_id=610511387737-t3psv0rgflquot1dvtagret612hv7m1p.apps.googleusercontent.com&approval_prompt=force"> <i class="fab fa-google me-2"></i> Sign in with Google</a>
                                </button>
                                <div class="mt-2" style="font-size: 0.975rem; color: rgba(0,0,0,.26); display: flex; justify-content: center">Are you just know a shop?<a href="create.jsp" style="color: #669237">Register</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
