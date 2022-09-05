<%@ page import="com.svalero.ECIwebapp.dao.Database" %>
<%@ page import="com.svalero.ECIwebapp.dao.ProductDao" %>
<%@ page import="com.svalero.ECIwebapp.domain.Product" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="com.svalero.ECIwebapp.domain.User" %>
<%@ page import="java.util.ArrayList" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login-user.jsp");
    }
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h2>Listado de productos</h2>
    <ul class="list-group">
        <%
            Database database = new Database();
            ProductDao productDao = new ProductDao(database.getConnection());
            try {
                ArrayList<Product> products= productDao.ShowProduct();
                for (Product product : products) {
        %>
        <li class="list-group-item">
            <a target="_blank" href="product.jsp?id=<%= product.getId() %>"><%= product.getName() %></a>
            <%
                if ((currentUser != null) && (currentUser.getRole().equals("admin"))) {
            %>
            <a href="delete-book?id=<%= product.getId() %>" class="btn btn-outline-danger">Eliminar producto</a>
            <%
                }
            %>
        </li>
        <%
            }
        } catch (SQLException sqle) {
        %>
        <div class="alert alert-danger" role="alert">
            Error al intentar la conexion con la base de datos
        </div>
        <%
            }
        %>
    </ul>
</div>
</body>
</html>