<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@include file="./base.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
</head>
<body>
    <div class="container mt-3">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <h1 class="text-center mb-3">${form_heading}</h1>
                <form action="${pageContext.request.servletContext.contextPath}/form-handle" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="productId" class="form-label">Product ID</label>
                        <input type="text" class="form-control" id="productId" name="id" value="${product.id}" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="name" class="form-label">Product Name</label>
                        <input type="text" class="form-control" id="name" name="name" value="${product.name}" required>
                    </div>
                    <div class="mb-3">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" class="form-control" id="price" name="price" value="${product.price}" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="3" required>${product.description}</textarea>
                    </div>
                    <div class="container text-center">
                        <a href="${pageContext.request.servletContext.contextPath}/" class="btn btn-outline-warning">Back</a>
                        <button type="submit" class="btn btn-outline-primary">Submit</button>
                    </div>
                </form>
                <c:if test="${not empty msg}">
                    <div class="alert alert-danger mt-3">${msg}</div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
