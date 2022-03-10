<%-- 
    Document   : verify
    Created on : 06-03-2022, 21:19:17
    Author     : Thiep Ngo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Verify Page</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/shopFavicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles_login.css" rel="stylesheet" />
    </head>
    <body>
        <div class="container text-center">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card border-0 shadow rounded-3 my-5">
                        <div class="card-body p-4 p-sm-5">
                            <div class="form-floating mb-3">
                                <span class="ms-2">We already send a verification code to your email.</span> 
                                <form action="MainController" method="POST">
                                    <input type="text" name="authcode">
                                    <input type="submit" name="action" value="Verify">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
