package ru.yaotone.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yaotone.project.model.User;
import ru.yaotone.project.service.UsersService;


@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("Users", usersService.getAllUsers());

        return "/users/index";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", usersService.getUserById(id));

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

        usersService.addUser(user);

        return "redirect:/users";
    }

    @GetMapping("{id}/update")
    public String update(@PathVariable("id")int id, Model model) {
        model.addAttribute("user", usersService.getUserById(id));

        return "/users/update";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "/users/update";
        }

        usersService.updateUser(user, id);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        usersService.deleteUser(id);

        return "redirect:/users";
    }
}
