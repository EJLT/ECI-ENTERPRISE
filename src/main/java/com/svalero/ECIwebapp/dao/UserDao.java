package com.svalero.ECIwebapp.dao;



import com.svalero.ECIwebapp.domain.User;
import com.svalero.ECIwebapp.exception.UserAlreadyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserDao {

    private Connection connection; //ATRIBUTO

    public UserDao(Connection connection) {
        this.connection = connection;
    }  // Constructor

    public void register (User user) throws SQLException, UserAlreadyExistException {                //FUNCIÓN QUE REGISTRA UN USUARIO

        if (existUser(user.getUsername()))
            throw new UserAlreadyExistException();

        String sql = " INSERT INTO users (username, password) VALUES (?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.executeUpdate();
    }

    public boolean delete (String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        int rows = statement.executeUpdate();
        return rows == 1;
    }


    public Optional<User> findByUsername(String username) throws SQLException {                         //Funcion que busca por nombre de usuario para saber si existe o no
        String sql = "SELECT * FROM users WHERE username = ?";
        User user= null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setUsername(resultSet.getString("username"));

        }

        return Optional.ofNullable(user);
    }

    private boolean existUser(String username) throws SQLException {                    // Para saber si existe un usuario
        Optional<User> user = findByUsername(username);
        return user.isPresent();
    }

    private boolean checkPassword(String username, String password) throws SQLException{
        String sql = "SELECT * FROM users WHERE username = ?";
        User user= null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            if (user.getPassword()== password){                               // SABER QUE LA CONTRASEÑA ES IGUAL A LA DEL USUARIO
                return true;
            }else {
                return false;
            }

        }else {
            return false;
        }

    }

    public boolean login(User user) throws SQLException{
        // if(existUser(user.getUsername()) && checkPassword(user.getUsername(), user.getPassword())){             //Comprueba dos cosas al mismo tiemp con &&, solo devuelve true si la dos condiciones se cumplen
        //    return true;

        // }else{
        //    return false;
        //    }
        return true;
    }


    public ArrayList<User> listar () throws SQLException{
        String sql = "SELECT * FROM users ORDER BY username";
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }

        return users;
    }

    private User fromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString("Username"));
        return user;
    }
    public ArrayList<User> findAllUsers(String searchText) throws SQLException {
        String sql = "SELECT * FROM users WHERE INSTR(username, ?) != 0 OR INSTR(email , ?) != 0 ORDER BY username";
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }

        return users;
    }
}
