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
    <title>Information Page</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/shopFavicon.ico" />
    <!-- Bootstrap icons-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles_login.css" rel="stylesheet" />
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card border-0 shadow rounded-3 my-5">
                        <div class="card-body p-4 p-sm-5">
                            <h3 class="card-title text-center mb-5 fw-light fw-bold fs-2">Register</h3>
                            <form action="MainController" method="POST">
                                <div class="form-floating mb-3">
                                    <input type="email" name="email" required="" class="form-control" id="email" placeholder="Email">
                                    <label for="email">Email</label>
                                    <c:if test="${requestScope.USER_ERROR != NULL}">
                                        <h5 style="font-size: 0.875rem" class="text-danger m-2">${USER_ERROR.emailError}</h5>
                                    </c:if>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" minlength="2" maxlength="30" title="Full Name length should be more than 2 and less than 31" name="fullName" required="" class="form-control" id="fullName" placeholder="Full Name">
                                    <label for="fullName">Full Name</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="floatingPassword" name="password" 
                                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one 
                                           number and one uppercase and lowercase letter, and at least 8 or more characters" placeholder="Password" required="">
                                    <label for="floatingPassword">Password</label>
                                </div>

                                <div class="form-floating mb-3">
                                    <input type="password" name="confirmPassword" required="" class="form-control" id="floatingConfirmPassword" placeholder="Confirm Password">
                                    <label for="floatingConfirmPassword">Confirm Password</label>
                                </div>
                                <c:if test="${requestScope.USER_ERROR != NULL}">
                                    <h5 style="font-size: 0.875rem" class="text-danger m-2">${USER_ERROR.confirmError}</h5>
                                </c:if>

                                <div class="form-floating mb-3">
                                    <input type="date" name="birthday" required="" class="form-control" id="birthday" placeholder="Birthday">
                                    <label for="birthday">Birthday</label>
                                </div>

                                <div class="form-floating mb-3">
                                    <input type="text" name="phone" required="" class="form-control" pattern="[0][0-9]{9}"
                                           title="Phone must be 10 characters long number and begin is 0!" id="phone" placeholder="Phone Number">
                                    <label for="phone">Phone</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" name="address" required="" class="form-control" id="address" placeholder="Address">
                                    <label for="address">Address</label>
                                </div>
                                <div class="d-grid" style="color: #669237">
                                    <input class="btn btn-primary btn-login text-uppercase fw-bold"  type="submit" name="action" value="Register">
                                </div>
                            </form>
                            <c:if test="${ERROR_VERIFY != null}">
                                <h3 style="font-size: 0.875rem" class="text-danger m-2">${ERROR}</h3>
                            </c:if>
                            <hr class="my-4">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="js/scripts.js"></script>
    </body>
</html>
