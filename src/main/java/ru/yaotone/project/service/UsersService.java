package ru.yaotone.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yaotone.project.UserJdbcDaoImpl.UsersDAO;
import ru.yaotone.project.model.User;

import java.util.List;

@Service
public class UsersService {
    private final UsersDAO usersDAO;

    @Autowired
    public UsersService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return usersDAO.showUsers();
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return usersDAO.showUser(id);
    }

    @Transactional
    public void addUser(User user) {
        usersDAO.addUser(user);
    }

    @Transactional
    public void updateUser(User user, int id) {
        usersDAO.updateUser(user, id);
    }

    @Transactional
    public void deleteUser(int id) {
        usersDAO.deleteUser(id);
    }
}
