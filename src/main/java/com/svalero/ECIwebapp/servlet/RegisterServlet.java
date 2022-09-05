package com.svalero.ECIwebapp.servlet;


import com.svalero.ECIwebapp.dao.Database;
import com.svalero.ECIwebapp.dao.UserDao;
import com.svalero.ECIwebapp.domain.User;
import com.svalero.ECIwebapp.exception.UserAlreadyExistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");                       //PETICIÓN
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        User user = new User(username,password,name,email);                       // COGE LOS DATOS DE LA PETICION Y CREA UN USUARIO

        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            userDao.register(user);
            out.println("<p style='color:green'>El registro se ha realizado correctamente</p>");
        } catch (SQLException sqle) {
            out.println("<p style='color:red'>Los datos son incorrectos</p>");
            sqle.printStackTrace();
        }catch (UserAlreadyExistException userAlreadyExistException) {
            out.println("<p style='color:red'>Ese nombre de usuario ya esta en uso</p>");
            userAlreadyExistException.printStackTrace();
        }
    }
}
