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
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }




    @PostMapping("/create")
    public String createUser(@ModelAttribute("newuser") User user, @RequestParam("role") List<Long> roles) {
        user.setRoles(roleService.findRoleById(roles));
        userService.add(user);
        return "redirect:/admin/panel";
    }

    @GetMapping(value = "/panel")
    public String printUsers(ModelMap model, Principal pr) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", userService.findByEmail(pr.getName()));
        model.addAttribute("newuser", new User());
        return "admin";
    }
//
//    @GetMapping("/delete")
//    public String deleteUserform(@RequestParam(value = "id") Long id, ModelMap model) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//        return "user";
//    }
//
//    @PostMapping("/{id}/delete")
//    public String deleteUser(@ModelAttribute("user") User user) {
//        userService.removeUser(user.getId());
//        return "redirect:/admin/panel";
//    }
//
//    @GetMapping("/update")
//    public String updateUserForm(@RequestParam(value = "id") Long id, ModelMap model) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//        return "user";
//    }
//
//    @PostMapping("/{id}/update")
//    public String updateUser(@ModelAttribute("user") User user, @RequestParam("role") List<Long> roles) {
//        user.setRoles(roleService.findRoleById(roles));
//        userService.changeUser(user);
//        return "redirect:/admin/panel";
//    }
@GetMapping("/logout")
public String logout(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
        request.getSession().invalidate();
    }
    return "redirect:/login";
}



}
