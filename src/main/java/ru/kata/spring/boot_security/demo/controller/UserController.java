package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin#new-user")
    public String createUserForm(User user) {
        return "admin#new-user";
    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute("currentUser") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin")
    public String printUsers(ModelMap model, Principal pr, User user) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", userService.findByEmail(pr.getName()));
        model.addAttribute("newuser", new User());
        return "admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/admin/update")
    public String updateUser(User user, @RequestParam("role") List<Long> roles) {
        user.setRoles(roleService.findRoleById(roles));
        userService.changeUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String getUserPage(Principal pr, ModelMap model) {
        model.addAttribute("user", userService.findByEmail(pr.getName()));
        return "user";
    }


//    @GetMapping(value = "/admin")
//    public String getAdminPage() {
//        return "admin";
//    }

    @GetMapping(value = "/")
    public String getLoginPage() {
        return "login";
    }


}
