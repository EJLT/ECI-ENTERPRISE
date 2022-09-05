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


@WebServlet("/search-users")
public class SearchUsersServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String searchText = request.getParameter("searchtext");

        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            ArrayList<User> users = userDao.findAllUsers(searchText);
            StringBuilder result = new StringBuilder("<ul class='list-group'>");
            for (User user : users) {
                result.append("<li class='list-group-item'>").append(user.getUsername()).append("</li>");
            }
            result.append("</ul>");
            out.println(result);
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error durante la búsqueda</div>");
            sqle.printStackTrace();
        }
    }
}