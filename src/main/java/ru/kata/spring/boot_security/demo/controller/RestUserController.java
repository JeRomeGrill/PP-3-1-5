package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RestUserController {
    private UserService userService;
    private RoleService roleService;
    @Autowired
    public RestUserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/current")
    public ResponseEntity<User> getUserPage(Principal pr) {
        return ResponseEntity.ok(userService.findByEmail(pr.getName()));
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers () {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping
    public ResponseEntity<?> createNewUser (@RequestBody User user){
        userService.add(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


}
