package ru.yaotone.project.UserJdbcDaoImpl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yaotone.project.model.User;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Connection connection;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/UsersDB", "postgres", "0000");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> showUsers(){
        List<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM users").executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setAge(resultSet.getInt("age"));

                users.add(user);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
//        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public User showUser(int id){
        User user = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setAge(resultSet.getInt("age"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;

//        return jdbcTemplate.query("SELECT * FROM users WHERE id=?", new BeanPropertyRowMapper<>(User.class), id)
//                .stream().findAny().orElse(null);
    }


    public void addUser(User user){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users (id, name, surname, age) VALUES (nextval('my_seq'),?, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

//        jdbcTemplate.update("INSERT INTO Users VALUES(nextval('my_seq'),?,?,?)",
//                user.getName(), user.getSurname(), user.getAge());
    }

    public void updateUser(User user, int id){
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE users SET name=?, surname=?, age=? WHERE id=?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

//        jdbcTemplate.update("UPDATE Users SET name=?, surname=?, age=? WHERE id=?",
//                user.getName(), user.getSurname(), user.getAge(), id);
    }

    public void deleteUser(int id){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Users WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

//        jdbcTemplate.update("DELETE FROM Users WHERE id=?", id);
    }
}
