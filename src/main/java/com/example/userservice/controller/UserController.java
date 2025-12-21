package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Операции с пользователями")
public class UserController {

    private final IUserService userService;

    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    public CollectionModel<EntityModel<UserDto>> getAll() {
        List<EntityModel<UserDto>> users = userService.getAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public EntityModel<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userService.get(id);
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("users"));
    }

    @PostMapping
    @Operation(summary = "Создать нового пользователя")
    public EntityModel<UserDto> createUser(@Valid @RequestBody UserDto dto) {
        UserDto created = userService.create(dto);
        return EntityModel.of(created,
                linkTo(methodOn(UserController.class).getUser(created.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("users"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя по ID")
    public EntityModel<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
        UserDto updated = userService.update(id, dto);
        return EntityModel.of(updated,
                linkTo(methodOn(UserController.class).getUser(updated.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("users"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по ID")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}