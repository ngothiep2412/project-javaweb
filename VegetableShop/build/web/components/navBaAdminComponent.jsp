<%-- 
    Document   : navBaAdminComponent
    Created on : 03-03-2022, 09:52:14
    Author     : Thiep Ngo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="data.dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="L" scope="session" value="${LOGIN_USER}"></c:set>
<c:set var="K" scope="request" value="${KEYWORD}"></c:set>
    <body>
    <c:if test="${L == NULL || 1 != L.roleID}">
        <c:redirect url="MainController?action=Logout"></c:redirect>
    </c:if>

    <nav class="navbar navbar-expand-lg navbar-light bg-light h-100">
        <div class="container px-4 px-lg-5">
            <a href="HomeAdminController" style="font-weight: 500" class="navbar-brand" aria-current="page">
                Home
            </a>
            <div class="navbar-brand ms-3" style="color: green; font-weight: 350" >  
                Welcome: ${L.fullName}
            </div>


            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" style="font-weight: 700" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">Category</a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <c:forEach items="${sessionScope.LIST_CATEGORY}" var="C">
                                <li>
                                    <a class="dropdown-item" href="MainController?action=Filter&categoryID=${C.categoryID}&roleID=${L.roleID}">
                                        ${C.categoryName}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>  


                <form action="MainController" class="d-flex mx-left w-100" >
                    <input class="form-control me-2" name="keyword" type="search" placeholder="Search" aria-label="Search">
                    <input name="action" value="Search" class="btn btn-outline-primary" type="submit">
                    <input name="roleID" value="${L.roleID}" type="hidden">
                    <input name="page" value="1" type="hidden">
                </form>


                <form action="admin_add.jsp" method="POST">
                    <input class="btn btn-outline-info ms-lg-2" type="submit" name="action" value="Add">
                </form>

                <form action="MainController" method="POST">
                    <input class="btn btn-outline-danger ms-lg-2" type="submit" name="action" value="Logout">
                </form>
            </div>
        </div>
    </nav>
