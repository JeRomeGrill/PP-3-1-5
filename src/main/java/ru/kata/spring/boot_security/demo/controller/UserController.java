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

    @GetMapping("/create")
    public String createUserForm(User user) {
        return "create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/list";
    }

    @GetMapping(value = "/list")
    public String printUsers(ModelMap model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/list";
    }

    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "update";
    }
    @PostMapping("/update")
    public String updateUser(User user, @RequestParam("role") List<Long> roles) {
        user.setRoles(roleService.findRoleById(roles));
        userService.changeUser(user);
        return "redirect:/list";
    }
    @GetMapping(value = "/user")
    public String getUserPage(Principal pr, ModelMap model) {
        model.addAttribute("user", userService.findByEmail(pr.getName()));
        return "user";
    }



    @GetMapping (value = "/admin")
    public String getAdminPage(){
        return "admin";
    }

    @GetMapping (value = "/index")
    public String getLoginpage(){
        return "index";
    }


}
