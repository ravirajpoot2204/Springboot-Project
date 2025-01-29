<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@include file="./base.jsp"%>

<!doctype html>
<html lang="en">
<head>
    <title>${title}</title>
</head>
<body>
    <div class="container mt-3">
        <div class="row">
            <div class="col-md-12">
                <h1 class="text-center mb-3">Welcome to Product CRUD App</h1>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Price</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${allProducts}" var="p">
                            <tr>
                                <th scope="row">${p.id}</th>
                                <td>${p.name}</td>
                                <td>${p.description}</td>
                                <td class="font-weight-bold">&#8377; ${p.price}</td>
                                <td>
                                    <a href="delete/${p.id}" class="fa-solid fa-trash text-danger" style="font-size: 25px"></a>
                                    <a href="update/${p.id}" class="fa-solid fa-pen-to-square" style="font-size: 25px"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="container text-center">
                    <a href="form" class="btn btn-outline-success">Add Product</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
