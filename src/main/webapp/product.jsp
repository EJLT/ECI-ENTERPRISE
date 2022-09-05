<%@ page import="com.svalero.ECIwebapp.dao.Database" %>
<%@ page import="com.svalero.ECIwebapp.dao.ProductDao" %>
<%@ page import="com.svalero.ECIwebapp.domain.Product" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<%
    String ProductName = request.getParameter("Name");
    Database db = new Database();
    ProductDao productDao = new ProductDao(db.getConnection());
    Product product = null;
    try {
        Optional<Product> optionalProduct = productDao.findByName(String.valueOf((ProductName)));
        product= optionalProduct.get();

%>
<div class="container">
    <div class="card text-center">
        <div class="card-header">
            Detalles de los productos
        </div>
        <div class="card-body">
            <h5 class="card-title"><%= product.getCategory() %></h5>
            <p class="card-text">El nombre del producto es <strong><%= product.getName() %></strong></p>
            <p class="card-text">Cuyo precio es de <strong><%= product.getPrice() %></strong></p>
            <a href="buy?id=<%= product.getId() %>" class="btn btn-primary">Mostrar detalles</a>

        </div>
        <div class="card-footer text-muted">
           Las existencias disponibles en este momento son<strong><%= product.getExistences() %></strong>
        </div>
    </div>
</div>
<%
} catch (SQLException sqle) {
%>
<div class='alert alert-danger' role='alert'>Se ha producido un error al mostrar los detalles del producto</div>
<%
    }
%>
</body>
</html>
