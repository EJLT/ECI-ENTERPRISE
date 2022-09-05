package com.svalero.ECIwebapp.servlet;



import com.svalero.ECIwebapp.dao.Database;
import com.svalero.ECIwebapp.dao.ProductDao;
import com.svalero.ECIwebapp.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet("/search-products")
public class SearchProductsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String searchText = request.getParameter("searchtext");

        Database database = new Database();
        ProductDao productDao = new ProductDao(database.getConnection());
        try {
          ArrayList<Product> products = productDao.findAllProducts(searchText);
            StringBuilder result = new StringBuilder("<ul class='list-group'>");
            for (Product product : products) {
                result.append("<li class='list-group-item'>").append(Product.getName()).append("</li>");
            }
            result.append("</ul>");
            out.println(result);
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Ha habido un error la buscar este producto</div>");
            sqle.printStackTrace();
        }
    }
}