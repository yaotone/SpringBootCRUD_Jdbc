package ru.yaotone.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yaotone.project.UserJdbcDaoImpl.UsersDAO;
import ru.yaotone.project.model.User;


@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersDAO usersDAO;

    @Autowired
    public UsersController(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("Users", usersDAO.showUsers());

        return "/users/index";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", usersDAO.showUser(id));

        return "/users/showUser";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("user") User user) {
        return "/users/create";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/users/create";
        }

        usersDAO.addUser(user);

        return "redirect:/users";
    }

    @GetMapping("{id}/update")
    public String update(@PathVariable("id")int id, Model model) {
        model.addAttribute("user", usersDAO.showUser(id));

        return "/users/update";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "/users/update";
        }

        usersDAO.updateUser(user, id);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        usersDAO.deleteUser(id);

        return "redirect:/users";
    }
}
