package com.svalero.ECIwebapp.servlet;


import com.svalero.ECIwebapp.dao.Database;
import com.svalero.ECIwebapp.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/list-user")
public class ListarUserServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            userDao.listar();
            out.println("<p style='color:green'>Usuario listado correctamente</p>");
        } catch (SQLException sqle) {
            out.println("<p style='color:red'>El usuario no existe</p>");
            sqle.printStackTrace();
        }
    }


}