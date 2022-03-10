<%-- 
    Document   : error
    Created on : 03-03-2022, 16:41:33
    Author     : Thiep Ngo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="components/headerComponent.jsp" %>
    <%@include file="components/navBarComponent.jsp" %>
    <body>
        <div id="notfound" style="display: flex; text-align: center; min-height: 600px">
            <div class="notfound" style="margin: auto">
                <div class="notfound-404">
                    <h1>404</h1>
                </div>
                <h2>Oops! Page Not Be Found</h2>
                <p>Sorry but the page you are looking for does not exist, have been removed. name changed or is temporarily unavailable</p>
                <a class="btn btn-outline-danger" href="MainController?action=Home">Back to homepage</a>
            </div>
        </div>
    </body>
    <%@include file="components/footerComponent.jsp" %>
</html>
