package ru.yaotone.project.UserJdbcDaoImpl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yaotone.project.model.User;

import java.util.List;

@Component
public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> showUsers(){
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public User showUser(int id){
        return jdbcTemplate.query("SELECT * FROM users WHERE id=?", new BeanPropertyRowMapper<>(User.class), id)
                .stream().findAny().orElse(null);
    }

    public void addUser(User user){
        jdbcTemplate.update("INSERT INTO Users VALUES(nextval('my_seq'),?,?,?)",
                user.getName(), user.getSurname(), user.getAge());
    }

    public void updateUser(User user, int id){
        jdbcTemplate.update("UPDATE Users SET name=?, surname=?, age=? WHERE id=?",
                user.getName(), user.getSurname(), user.getAge(), id);
    }

    public void deleteUser(int id){
        jdbcTemplate.update("DELETE FROM Users WHERE id=?", id);
    }
}
