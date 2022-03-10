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
         <c:if test="${ERROR_QUANTITY_MESSAGE != NULL}">
            <div class="alert alert-warning container">
                <center>
                    <strong>Warning!!!</strong> </br> 
                    ${requestScope.ERROR_QUANTITY_MESSAGE}
                </center>
            </div>
        </c:if> 
        <div class="container px-4 px-lg-5 my-5" style="min-height: 400px; border: 1px solid green">
            
            <div class="row gx-4 gx-lg-5 align-items-center">
                <div class="col-md-6" style="text-align: center">
                    <img class="card-img-top mb-5 mb-md-0 w-50" src="${PD.imageUrl}" alt="..." />
                </div>
                <div class="col-md-6">
                    <h1 class="display-6 fw-normal">Product Name: ${PD.productName}</h1>
                    <h4 class="lead" style="font-size: 20px">Description: ${PD.description}</h4>
                    <div class="fs-5 mb-5" >
                        <p class="display-6 fw-bold" style="font-size: 20px">Price: $${PD.price}</p>
                    </div>
                    <div class="fs-5 mb-5" >
                        <p class="display-6 fw-bold" style="font-size: 20px">Quantity: ${PD.quantity}</p>
                    </div>  
                    <div class="d-flex">
                        <a href="MainController?action=AddToCart&productID=${PD.productID}" class="btn btn-outline-dark flex-shrink-0" type="button">
                            <i class="bi-cart-fill me-1"></i>
                            Add to cart
                        </a>

                    </div>
                </div>
            </div>
        </div>
    </section>
    <%@include file="components/footerComponent.jsp"%>
    <script>
        window.setTimeout(function () {
            $(".alert").fadeTo(500, 0).slideUp(500, function () {
                $(this).remove();
            });
        }, 2000);
    </script>
</body>
</html>

