package com.svalero.ECIwebapp.servlet;


import com.svalero.ECIwebapp.dao.Database;
import com.svalero.ECIwebapp.dao.UserDao;
import com.svalero.ECIwebapp.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/login-user")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");                       //PETICIÓN
        String password = request.getParameter("password");
        User user = new User(username, password);                       // COGE LOS DATOS DE LA PETICION Y CREA UN USUARIO

        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            if(userDao.login(user)){   //ESTO ES LO MISMO QUE if(userDao.login(user)){
                HttpSession session = request.getSession(true);
                session.setAttribute("currentUser", user.get());
                System.out.println("sesión iniciada");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println("No se ha podido loggear");
            out.println("<p style='color:green'>Usuario logeado correctamente</p>");
        } catch (SQLException sqle) {
            out.println("<p style='color:red'>El usuario no existe</p>");
            sqle.printStackTrace();
        }
    }
}
