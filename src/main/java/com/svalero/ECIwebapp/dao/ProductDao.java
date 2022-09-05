package com.svalero.ECIwebapp.dao;



import com.svalero.ECIwebapp.domain.Product;
import com.svalero.ECIwebapp.exception.ProductAlreadyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ProductDao {

    private Connection connection;

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    public void addProduct() throws SQLException, ProductAlreadyExistException {

        if (existProduct(Product.getName()))
            throw new ProductAlreadyExistException();

        String sql = "INSERT INTO products (nombre) VALUES (?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Product.getName());
        statement.executeUpdate();
    }

    public boolean delete (String name) throws SQLException {
        String sql = "DELETE FROM products WHERE nombre = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        int rows = statement.executeUpdate();
        return rows == 1;
    }


    public Optional<Product> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM products WHERE nombre = ?";
        Product product = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            product = new Product();
            product.setName(resultSet.getString("Nombre"));

        }

        return Optional.ofNullable(product);
    }

    private boolean existProduct(String name) throws SQLException {                    // Para saber si existe un usuario
        Optional <Product> product = findByName(name);
        return product.isPresent();
    }


    public ArrayList<Product> ShowProduct () throws SQLException{
        String sql = "SELECT * FROM products ORDER BY nombre";
        ArrayList<Product> products = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Product product = fromResultSet(resultSet);
            products.add(product);
        }
        return products;

    }

    private Product fromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setName(resultSet.getString("Nombre"));
        return product;
    }


    public ArrayList<Product> findAllProducts(String searchText) throws SQLException {
        String sql = "SELECT * FROM products WHERE INSTR(nombre, ?) != 0 OR INSTR(categoria, ?) != 0 ORDER BY categoria";
        ArrayList<Product> products = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Product product= fromResultSet(resultSet);
            products.add(product);
        }

        return products;
    }



    }

