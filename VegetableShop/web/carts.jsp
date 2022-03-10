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

    <!-- Product section-->
    <section class="py-5">
        <div class="container" style="min-height: 500px">

            <c:choose>
                <c:when test="${sessionScope.CARTS.size()==0 || sessionScope.CARTS == NULL}">
                    <div class="container text-center">
                        <h3>Your cart is empty.</h3>
                        <a class="btn btn-success" style="color: white; text-decoration: none" href="MainController?action=Home">BUY NOW</a>
                    </div>


                </c:when>
                <c:otherwise>
                    <h3>List Products</h3>
                    <table class="table text-center">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Product ID</th>
                                <th scope="col">Image</th>
                                <th scope="col">Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Total Price</th>
                                <th scope="col">Action</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${sessionScope.CARTS}" var="C" varStatus="couter">
                            <form action="UpdateQuantityController">
                                <tr>
                                <input type="hidden" name="productID" value="${C.value.product.productID}"/>
                                <th scope="row">${couter.count}</th>
                                <th>${C.value.product.productID}</th>
                                <td><img src="${C.value.product.imageUrl}" width="60" alt="alt"/></td>
                                <th>${C.value.product.productName}</th>
                                <td>${C.value.product.price}</td>
                                <c:if test="${C.value.quantity > C.value.product.quantity}">
                                    <td>
                                        <input onchange="this.form.submit()"  required="" min="1" max="${C.value.product.quantity}" name="quantity" type="number" value="${C.value.product.quantity}"/>
                                    </td>
                                    <td>${C.value.product.quantity * C.value.product.price}</td>
                                </c:if>
                                <c:if test="${C.value.quantity <= C.value.product.quantity}">
                                    <td> 
                                        <input onchange="this.form.submit()" required="" min="1" max="${C.value.product.quantity}" name="quantity" type="number" value="${C.value.quantity}"/>
                                    </td>
                                    <td>${C.value.quantity * C.value.product.price}</td>
                                </c:if>
                                <td>
                                    <a style="width: 50%" href="MainController?action=DeleteCart&productID=${C.value.product.productID}" class="btn btn-outline-danger">
                                        <i class="bi bi-trash"></i> Delete
                                    </a>
                                </td>
                                </tr>
                            </form>


                        </c:forEach>

                        </tbody>
                    </table>
                    <div>
                        <h3 >Total Amount: $${requestScope.TOTAL_MONEY}</h3>
                        <a href="MainController?action=CheckOut" class="btn btn-outline-success w-25">Check Out</a>
                 </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>


    <%@include file="components/footerComponent.jsp"%>

</body>
</html>

