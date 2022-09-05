package com.svalero.ECIwebapp.servlet;



import com.svalero.ECIwebapp.dao.Database;
import com.svalero.ECIwebapp.dao.ProductDao;
import com.svalero.ECIwebapp.domain.Product;
import com.svalero.ECIwebapp.exception.ProductAlreadyExistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("Nombre");
        String category = request.getParameter("Categoría");
        Float price = Float.valueOf(request.getParameter("Precio"));
        Product product = new Product(name, category, price);

        Database database = new Database();
        ProductDao productDao = new ProductDao(database.getConnection());
        try {
            productDao.addProduct();
            out.println("<div class='alert alert-success' role='alert'>El producto se ha registrado correctamente</div>");
        } catch (ProductAlreadyExistException paee) {
            out.println("<div class='alert alert-danger' role='alert'>Este producto ya existe en la base de datos</div>");
            paee.printStackTrace();
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Ha habido un error al registrar el producto</div>");
            sqle.printStackTrace();

        }
    }
}