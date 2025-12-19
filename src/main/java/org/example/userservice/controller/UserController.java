package org.example.userservice.controller;

import org.example.userservice.dto.UserDto;
import org.example.userservice.dto.UserMapper;
import org.example.userservice.entity.User;
import org.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        User user = service.create(UserMapper.toEntity(dto));
        return UserMapper.toDto(user);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return UserMapper.toDto(service.getById(id));
    }

    @GetMapping
    public List<UserDto> getAll() {
        return service.getAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto dto) {
        return UserMapper.toDto(service.update(id, UserMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
