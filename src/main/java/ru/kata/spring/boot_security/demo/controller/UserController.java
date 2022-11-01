package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
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


    @PostMapping("/admin/create")
    public String createUser(@ModelAttribute("newuser") User user, @RequestParam("role") List<Long> roles) {
        user.setRoles(roleService.findRoleById(roles));
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin")
    public String printUsers(ModelMap model, Principal pr) {
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

    @GetMapping("/admin/update")
    public String updateUserForm(@RequestParam(value = "id") Long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/admin/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("role") List<Long> roles) {
        user.setRoles(roleService.findRoleById(roles));
        userService.changeUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String getUserPage(Principal pr, ModelMap model) {
        model.addAttribute("user", userService.findByEmail(pr.getName()));
        return "user";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }




}
