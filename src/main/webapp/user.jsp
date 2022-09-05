
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.svalero.ECIwebapp.dao.Database" %>
<%@ page import="com.svalero.ECIwebapp.dao.UserDao" %>
<%@ page import="com.svalero.ECIwebapp.domain.User" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<%
    String Username = request.getParameter("Username");
    Database db = new Database();
    UserDao userDao = new UserDao(db.getConnection());
    User user = null;
    try {
        Optional<User> optionalUser = userDao.findByUsername(String.valueOf((Username)));
        user= optionalUser.get();

%>
<div class="container">
    <div class="card text-center">
        <div class="card-header">
            Detalles de los usuarios
        </div>
        <div class="card-body">
            <h5 class="card-title"><%= user.getId() %></h5>
            <p class="card-text">Su nombre es <strong><%= user.getName() %></strong></p>
            <p class="card-text">Su nombre de usuario es <strong><%= user.getUsername() %></strong></p>
            <a href="buy?id=<%= user.getId() %>" class="btn btn-primary">Mostrar detalles</a>
</div>
<%
} catch (SQLException sqle) {
%>
<div class='alert alert-danger' role='alert'>Se ha producido un error al mostrar los detalles del usuario seleccionado</div>
<%
    }
%>
</body>
</html>
