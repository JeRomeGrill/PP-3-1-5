package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class RestUserController {
    private UserService userService;
    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public User getUserPage(Principal pr) {
        return userService.findByEmail(pr.getName());
    }
    @GetMapping ("/users")
    public List<User> getAllUsers () {
        return userService.listUsers();
    }


}
