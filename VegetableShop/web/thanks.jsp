<%-- 
    Document   : index
    Created on : 27-02-2022, 20:02:15
    Author     : Thiep Ngo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="PD" scope="request" value="${PRODUCT}"></c:set>
    <!DOCTYPE html>
    <html lang="en">
    <%@include file="components/headerComponent.jsp" %>  
    <%@include file="components/navBarComponent.jsp"%>

    <div class="container"  style="min-height: 550px">
        <div class="alert alert-success jumbotron text-center mt-5" role="alert">
            <h1 class="display-3">Thank You!</h1>
            <p class="lead"><strong>Please check your email</strong> for further instructions on showing your order.</p>
            <hr>
            <p class="lead">
                <a class="btn btn-outline-primary btn-sm" href="MainController?action=Home" role="button">Continue to shopping</a>
            </p>
        </div>
    </div>

    <%@include file="components/footerComponent.jsp"%>

</body>
</html>

