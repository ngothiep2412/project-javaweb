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
    <c:if test="${sessionScope.LOGIN_USER == NULL && 2 != sessionScope.LOGIN_USER.roleID}">
        <c:redirect url="MainController?action=Logout"></c:redirect>  
    </c:if>
    <c:if test="${sessionScope.CARTS == NULL || sessionScope.CARTS.size() == 0}">
        <c:redirect url="MainController?action=Logout"></c:redirect>  
    </c:if>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container px-4 px-lg-5">
            <a class="navbar-brand" href="MainController?action=Home">
                <i class="bi bi-shop-window" style="margin-right: 8px"></i>
                VEGETABLE SHOP
            </a>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="MainController?action=Home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">About</a>
                    </li>
                </ul>

                <c:if test="${sessionScope.LOGIN_USER == NULL || sessionScope.LOGIN_USER.roleID == 2}">
                    <form action="MainController" class="d-flex mx-auto" >
                        <input class="form-control me-2" name="keyword" type="search" placeholder="Search" aria-label="Search">
                        <input name="action" value="Search" class="btn btn-outline-success" type="submit">
                        <input name="page" value="1" type="hidden">
                    </form>
                </c:if>

                <div class="d-flex my-1"> 
                    <a class="btn btn-outline-dark" href="MainController?action=Cart">
                        <i class="bi-cart-fill me-1"></i>
                        Cart
                        <c:if test="${sessionScope.CARTS == null}">
                            <span class="badge bg-dark text-white ms-1 rounded-pill">0</span>
                        </c:if>
                        <c:if test="${sessionScope.CARTS != null}">
                            <span class="badge bg-dark text-white ms-1 rounded-pill">${sessionScope.CARTS.size()}</span>
                        </c:if>

                    </a>
                </div>

                <c:choose>
                    <c:when test="${sessionScope.LOGIN_USER == Null}">
                        <a href="login.jsp">
                            <button class="btn btn-outline-info ms-lg-2">Login</button>  
                        </a>
                    </c:when>
                    <c:when test="${sessionScope.LOGIN_USER != Null || sessionScope.LOGIN_USER.roleID == 2}">
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle ms-2" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                Welcome: ${sessionScope.LOGIN_USER.fullName}
                            </button>
                            <ul class="dropdown-menu" style="text-align: center; width: 100%" aria-labelledby="dropdownMenuButton1">
                                <li><a class="dropdown-item" href="#">Profile</a></li>
                                <li><a class="dropdown-item" href="MainController?action=Logout">Logout</a></li>
                            </ul>
                        </div>  
                    </c:when>
                </c:choose>

            </div>
        </div>
    </nav>

    <!-- Product section-->
    <section class="py-5">
        <div class="container" style="min-height: 500px">
            <div class="row">
                <h3>Check Out</h3>
                <div class="col-md-8" style="border: 1px solid #ccc; border-radius: 4px">

                    <table class="table text-center">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Image</th>
                                <th scope="col">Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Total Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${sessionScope.CARTS}" var="C" varStatus="counter">
                                <tr>
                            <input type="hidden" name="productID" value="${C.value.product.productID}"/>
                            <th scope="row">${counter.count}</th>
                            <th>${C.value.product.productName}</th>
                            <td><img src="${C.value.product.imageUrl}" width="60" alt="alt"/></td>
                            <td>${C.value.product.price}</td>
                            <td>${C.value.quantity}</td>
                            <td>${C.value.quantity * C.value.product.price}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-4">
                    <h3>Information of customer</h3>
                    <form action="CheckOutController" method="POST">
                        <div class="mb-3">
                            <label for="name" class="form-label">Full Name</label>
                            <input type="text" class="form-control" id="name" name="fullName" value="${sessionScope.LOGIN_USER.fullName}">
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email" value="${sessionScope.LOGIN_USER.email}" aria-describedby="emailHelp">
                            <div id="emailHelp" class="form-text">We'll never share your email with anyone else. And send your order to your email.</div>
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">Phone</label>
                            <input type="text" class="form-control" id="phone" name="phone" value="${sessionScope.LOGIN_USER.phone}" >

                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" name="address" value="${sessionScope.LOGIN_USER.address}" aria-describedby="emailHelp">
                        </div>
                        <div class="mb-3">
                            <label for="note" class="form-label">Note</label>
                            <textarea class="form-control" name="note" id="note" rows="3"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <%@include file="components/footerComponent.jsp"%>

</body>
</html>

