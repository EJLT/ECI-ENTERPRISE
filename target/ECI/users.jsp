<%@ page import="com.svalero.ECIwebapp.dao.Database" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.svalero.ECIwebapp.domain.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.svalero.ECIwebapp.dao.UserDao" %>

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
    <h2>Lista de Usuarios</h2>
    <ul class="list-group">
        <%
            Database database = new Database();
            UserDao userDao = new UserDao(database.getConnection());
            try {
                ArrayList<User> users= userDao.listar();
                for (User user : users) {
        %>
        <li class="list-group-item">
            <a target="_blank" href="user.jsp?id=<%= user.getId() %>"><%= user.getUsername() %></a>
            <%
                if ((currentUser != null) && (currentUser.getRole().equals("admin"))) {
            %>
            <a href="delete-book?id=<%= user.getId() %>" class="btn btn-outline-danger">Eliminar usuario</a>
            <%
                }
            %>
        </li>
        <%
            }
        } catch (SQLException sqle) {
        %>
        <div class="alert alert-danger" role="alert">
            Error al conectarse con la base de datos Users
        </div>
        <%
            }
        %>
    </ul>
</div>
</body>
</html>
