<%-- 
    Document   : admin
    Created on : 01-03-2022, 19:03:46
    Author     : Thiep Ngo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="data.dto.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page import="data.dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="components/headerAdminComponent.jsp"%>
    <%@include file="components/navBaAdminComponent.jsp"%>
    <section class="py-4">
        <div class="container" style="min-height: 800px">
            <table class="table" style="text-align: center" >
                <thead>
                    <tr>
                        <th scope="col">ProductID</th>
                        <th scope="col">Image</th>
                        <th scope="col">Product Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col" style="min-width: 130px ;text-align: center">Category</th>
                        <th scope="col" style="text-align: center; padding-right: 32px">Import Date</th>
                        <th scope="col" style="text-align: center; padding-right: 41px">Using Date</th>
                        <th scope="col"style="text-align: center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test = "${LIST_PRODUCT == null || LIST_PRODUCT.size() == 0}">
                        <h3 style="font-size: 25px; color: red">NOT FOUND</h3> 
                        </c:when>
                        <c:otherwise>
                        <h3>List Products</h3>
                        <c:forEach items="${LIST_PRODUCT}" var="P">
                            <tr>
                            <input type="hidden" name="productID" value="${P.productID}" />
                            <th scope="row">${P.productID}</th>
                            <td><img src="${P.imageUrl}" width="70"/></td>
                            <td style="text-align: -webkit-auto">${ P.productName}</td>
                            <td style="text-align: -webkit-auto">${P.description}</td>
                            <td>${P.price}</td>
                            <td>${P.quantity}</td>
                            <th scope="col">${P.categoryName}</th>
                            <td>${P.importDate}</td>
                            <td>${P.usingDate}</td>
                            <td>
                                <a style="width: 100%" href="MainController?action=Update&productID=${P.productID}" class="btn btn-outline-success mb-2">
                                    <i class="bi bi-box-arrow-in-down"></i> Update
                                </a>
                                <a style="width: 100%" href="MainController?action=Delete&productID=${P.productID}" class="btn btn-outline-danger">
                                    <i class="bi bi-trash"></i> Delete
                                </a>
                            </td>                           
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>

            <!-- Filter -->
            <c:choose>
                <c:when test="${sessionScope.LOGIN_USER.roleID == 1}">
                    <c:choose>
                        <c:when test="${requestScope.KEYWORD == NULL && requestScope.CATEGORYID == NULl }">
                            <nav aria-label="Page navigation example">
                                <ul class="pagination justify-content-end">
                                    <li class="page-item ${requestScope.PAGE - 1 < 1 ? "disabled":""}">
                                        <a class="page-link" href="MainController?action=HomeAdmin&page=${requestScope.PAGE - 1}" tabindex="-1" aria-disabled="true">
                                            Previous
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${requestScope.TOTAL_PAGE}" var="i">
                                        <li class="page-item ${i == requestScope.PAGE ? "active":""}">
                                            <a class="page-link" href="MainController?action=HomeAdmin&page=${i}">
                                                ${i}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${requestScope.PAGE + 1 > requestScope.TOTAL_PAGE ? "disabled":""}">
                                        <a class="page-link" href="MainController?action=HomeAdmin&page=${requestScope.PAGE + 1}">
                                            Next
                                        </a>
                                    </li> 
                                </ul>
                            </nav> 
                        </c:when>
                        <c:when test="${requestScope.KEYWORD == NULL && requestScope.CATEGORYID != NULl}">
                            <nav aria-label="Page navigation example">
                                <ul class="pagination justify-content-end">
                                    <li class="page-item ${requestScope.PAGE - 1 < 1 ? "disabled":""}">
                                        <a class="page-link" href="MainController?action=Filter&page=${requestScope.PAGE - 1}&categoryID=${requestScope.CATEGORYID}&roleID=${sessionScope.LOGIN_USER.roleID}" tabindex="-1" aria-disabled="true">
                                            Previous
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${requestScope.TOTAL_PAGE}" var="i">
                                        <li class="page-item ${i == requestScope.PAGE ? "active":""}">
                                            <a class="page-link" href="MainController?action=Filter&page=${i}&categoryID=${requestScope.CATEGORYID}&roleID=${sessionScope.LOGIN_USER.roleID}">
                                                ${i}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${requestScope.PAGE + 1 > requestScope.TOTAL_PAGE ? "disabled":""}">
                                        <a class="page-link" href="MainController?action=Filter&page=${requestScope.PAGE + 1}&categoryID=${requestScope.CATEGORYID}&roleID=${sessionScope.LOGIN_USER.roleID}">
                                            Next
                                        </a>
                                    </li>
                                </ul>
                            </nav>  
                        </c:when>
                        <c:when test="${requestScope.KEYWORD != NULl}">
                            <nav aria-label="Page navigation example">
                                <ul class="pagination justify-content-end">
                                    <li class="page-item ${requestScope.PAGE - 1 < 1 ? "disabled":""}">
                                        <a class="page-link" href="MainController?action=Search&keyword=${requestScope.KEYWORD}&page=${requestScope.PAGE - 1}&roleID=${sessionScope.LOGIN_USER.roleID}" tabindex="-1" aria-disabled="true">
                                            Previous</a>
                                    </li>
                                    <c:forEach begin="1" end="${requestScope.TOTAL_PAGE}" var="i">
                                        <li class="page-item ${i == requestScope.PAGE ? "active":""}">
                                            <a class="page-link" href="MainController?action=Search&keyword=${requestScope.KEYWORD}&page=${i}&roleID=${sessionScope.LOGIN_USER.roleID}">
                                                ${i}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${requestScope.PAGE + 1 > requestScope.TOTAL_PAGE ? "disabled":""}">
                                        <a class="page-link" href="MainController?action=Search&keyword=${requestScope.KEYWORD}&page=${requestScope.PAGE + 1}&roleID=${sessionScope.LOGIN_USER.roleID}">
                                            Next
                                        </a>
                                    </li>
                                </ul>
                            </nav>  
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>
        </div>
    </section>
    <%@include file="components/footerComponent.jsp"%>
</body>
</html>
