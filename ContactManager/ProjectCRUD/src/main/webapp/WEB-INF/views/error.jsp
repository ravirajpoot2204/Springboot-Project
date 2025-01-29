<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ include file="./base.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Error Page</title>
</head>
<body>
    <div class="container mt-3">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <h1 class="text-center mb-3 text-danger">Error Occurred</h1>
                <hr>
                <h2 class="text-center">${msg}</h2>
                <div class="text-center mt-3">
                    <a href="${pageContext.request.servletContext.contextPath}/" class="btn btn-outline-warning">Go to Home</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
