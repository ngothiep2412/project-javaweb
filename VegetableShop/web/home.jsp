<%-- 
    Document   : index
    Created on : 27-02-2022, 20:02:15
    Author     : Thiep Ngo
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <%@include file="components/headerComponent.jsp" %>
        <%@include file="components/navBarComponent.jsp"%>
        <!-- Header-->
        <header class="bg-success py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="text-center text-white d-flex">
                    <div>
                        <h1 class="display-4 fw-bolder mt-4">Cửa Hàng Rau Quả Của Ngô Thiệp</h1>
                        <h1 class="display-4 fw-bolder mt-3 fs-3" style="font-style: italic">- Sự lựa chọn hàng đầu -</h1>
                    </div>
                    <img class="d-block w-75" src="https://images.foody.vn/res/g94/939752/prof/s576x330/foody-upload-api-foody-mobile-yu-190716102431.jpg" alt="alt"/>
                </div>
            </div>
        </header>
        <!-- Section-->
        <section class="py-3">
            <div class="container px-4 px-lg-5 mt-5">
                <div class="row">
                    <div class="col-md-3 mb-sm-4">
                        <h3>List Categories</h3>
                        <div class="list-group">
                            <c:forEach items="${sessionScope.LIST_CATEGORY}" var="C">
                                <li  class="list-group-item ">
                                    <a class="text-success" style="text-decoration: none; display: block" href="FilterCategoryController?categoryID=${C.categoryID}">${C.categoryName}</a>
                                </li>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <c:if test="${ERROR_QUANTITY_MESSAGE != NULL}">
                            <div class="alert alert-warning">
                                <center>
                                    <strong>Warning!!!</strong> </br> 
                                    ${requestScope.ERROR_QUANTITY_MESSAGE}
                                </center>
                            </div>
                        </c:if>  
                        <h3 style="text-align: center">
                            List Product</h3>
                            <c:choose>
                                <c:when test="${LIST_PRODUCT == NULL || LIST_PRODUCT.size() == 0}">
                                <h3 style="text-align: left; color: red">
                                    Not Found</h3>
                                </c:when>
                                <c:otherwise>
                                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 justify-content-left text-center">
                                    <c:forEach items="${LIST_PRODUCT}" var="P">
                                        <div class="col mb-5">
                                            <div class="card h-100">
                                                <!-- Product image-->
                                                <a href="MainController?action=Detail&productID=${P.productID}">
                                                    <img class="card-img-top" src="${P.imageUrl}" alt="..." />
                                                </a>

                                                <!-- Product details-->
                                                <div class="card-body p-4" style="border-top: 1px solid #ccc">
                                                    <div class="text-center">

                                                        <div style="min-height: 100px">
                                                            <!-- Product name-->
                                                            <h5 style="min-height: 70px" class="fw-bolder">${P.productName}</h5>
                                                            <!-- Product reviews-->
                                                            <div class="d-flex justify-content-center small text-warning mb-2">
                                                                <div class="bi-star-fill"></div>
                                                                <div class="bi-star-fill"></div>
                                                                <div class="bi-star-fill"></div>
                                                                <div class="bi-star-fill"></div>
                                                                <div class="bi-star-fill"></div>
                                                            </div>
                                                        </div>

                                                        <!-- Product price-->
                                                        <div>
                                                            <h5 class="fw-normal">Price: $${P.price} </h5>
                                                            <h5 class="fw-normal">Quantity: ${P.quantity} </h5>
                                                        </div>

                                                    </div>
                                                </div>
                                                <!-- Product actions-->
                                                <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                                                    <div class="te xt-center">
                                                        <c:choose>
                                                            <c:when test="${requestScope.CATEGORYID == NULL && requestScope.KEYWORD == NULL}">
                                                                <a class="btn btn-outline-dark mt-auto" href="MainController?action=AddToCart&productID=${P.productID}&page=${requestScope.PAGE}&quantity=${P.quantity}">Add to cart</a>
                                                            </c:when>
                                                            <c:when test="${requestScope.CATEGORYID != NULL}">
                                                                <a class="btn btn-outline-dark mt-auto"
                                                                   href="MainController?action=AddToCart&productID=${P.productID}&page=${requestScope.PAGE}&categoryID=${requestScope.CATEGORYID}&quantity=${P.quantity}">Add to cart</a>
                                                            </c:when>
                                                            <c:when test="${requestScope.KEYWORD != NULL}">
                                                                <a class="btn btn-outline-dark mt-auto"
                                                                   href="MainController?action=AddToCart&productID=${P.productID}&page=${requestScope.PAGE}&keyword=${requestScope.KEYWORD}&quantity=${P.quantity}">Add to cart</a>
                                                            </c:when>
                                                        </c:choose>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:otherwise>
                        </c:choose>


                        <c:choose>
                            <c:when test="${sessionScope.LOGIN_USER == NULL || sessionScope.LOGIN_USER.roleID == 2}">
                                <c:choose>
                                    <c:when test="${requestScope.KEYWORD == NULL && requestScope.CATEGORYID == NULl }">
                                        <nav aria-label="Page navigation example">
                                            <ul class="pagination justify-content-end">
                                                <li class="page-item ${requestScope.PAGE - 1 < 1 ? "disabled":""}">
                                                    <a class="page-link" href="MainController?action=Home&page=${requestScope.PAGE - 1}" tabindex="-1" aria-disabled="true">Previous</a>
                                                </li>
                                                <c:forEach begin="1" end="${requestScope.TOTAL_PAGE}" var="i">
                                                    <li class="page-item ${i == requestScope.PAGE ? "active":""}"><a class="page-link" href="MainController?action=Home&page=${i}">${i}</a></li>
                                                    </c:forEach>
                                                <li class="page-item ${requestScope.PAGE + 1 > requestScope.TOTAL_PAGE ? "disabled":""}">
                                                    <a class="page-link" href="MainController?action=Home&page=${requestScope.PAGE + 1}">Next</a>
                                                </li> 
                                            </ul>
                                        </nav> 
                                    </c:when>
                                    <c:when test="${requestScope.KEYWORD == NULL && requestScope.CATEGORYID != NULl}">
                                        <nav aria-label="Page navigation example">
                                            <ul class="pagination justify-content-end">
                                                <li class="page-item ${requestScope.PAGE - 1 < 1 ? "disabled":""}">
                                                    <a class="page-link" href="MainController?action=Filter&page=${requestScope.PAGE - 1}&categoryID=${requestScope.CATEGORYID}" tabindex="-1" aria-disabled="true">Previous</a>
                                                </li>
                                                <c:forEach begin="1" end="${requestScope.TOTAL_PAGE}" var="i">
                                                    <li class="page-item ${i == requestScope.PAGE ? "active":""}"><a class="page-link" href="MainController?action=Filter&page=${i}&categoryID=${requestScope.CATEGORYID}">${i}</a></li>
                                                    </c:forEach>
                                                <li class="page-item ${PAGE + 1 > requestScope.TOTAL_PAGE ? "disabled":""}">
                                                    <a class="page-link" href="MainController?action=Filter&page=${requestScope.PAGE + 1}&categoryID=${requestScope.CATEGORYID}">Next</a>
                                                </li>
                                            </ul>
                                        </nav>  
                                    </c:when>
                                    <c:when test="${requestScope.KEYWORD != NULl}">
                                        <nav aria-label="Page navigation example">
                                            <ul class="pagination justify-content-end">
                                                <li class="page-item ${requestScope.PAGE - 1 < 1 ? "disabled":""}">
                                                    <a class="page-link" href="MainController?action=Search&keyword=${requestScope.KEYWORD}&page=${requestScope.PAGE - 1}" tabindex="-1" aria-disabled="true">Previous</a>
                                                </li>
                                                <c:forEach begin="1" end="${requestScope.TOTAL_PAGE}" var="i">
                                                    <li class="page-item ${i == requestScope.PAGE ? "active":""}"><a class="page-link" href="MainController?action=Search&keyword=${requestScope.KEYWORD}&page=${i}">${i}</a></li>
                                                    </c:forEach>
                                                <li class="page-item ${requestScope.PAGE + 1 > requestScope.TOTAL_PAGE ? "disabled":""}">
                                                    <a class="page-link" href="MainController?action=Search&keyword=${requestScope.KEYWORD}&page=${requestScope.PAGE + 1}">Next</a>
                                                </li>
                                            </ul>
                                        </nav>  
                                    </c:when>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </section>
        <%@include file="components/footerComponent.jsp"%>

    </body>
</html>

