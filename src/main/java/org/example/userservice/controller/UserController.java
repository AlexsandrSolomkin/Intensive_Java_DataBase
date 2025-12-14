package org.example.userservice.controller;

import org.example.userservice.entity.User;
import org.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) { this.service = service; }

    @PostMapping
    public User createUser(@RequestBody User user) throws Exception {
        return service.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws Exception {
        service.deleteUser(id);
    }
}
