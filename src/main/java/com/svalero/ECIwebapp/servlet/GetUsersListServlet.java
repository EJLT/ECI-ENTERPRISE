package com.svalero.ECIwebapp.servlet;

import com.svalero.ECIwebapp.dao.Database;
import com.svalero.ECIwebapp.dao.UserDao;
import com.svalero.ECIwebapp.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;



@WebServlet("/users")
public class GetUsersListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<head>\n" +
                "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\n" +
                "</head>");
        out.println("<div class='container'>");
        out.println("<h1>Lista de usuarios</h1>");
        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            out.println("<ul class='list-group'>");
            ArrayList<User> users = userDao.listar();
            for (User user : users) {
                out.println("<li class='list-group-item'><a href='user.jsp?id=" + user.getId() + "'>" + user.getUsername() + "</a></li>");
            }
            out.println("</ul>");
        } catch (SQLException sqle) {
            out.println("<h3>Se ha producido un error al cargar los detalles del usuario</h3>");
            sqle.printStackTrace();
        }
        out.println("</div>");
    }
}