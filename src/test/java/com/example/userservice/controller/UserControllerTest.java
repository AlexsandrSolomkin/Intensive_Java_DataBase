package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDto user1;
    private UserDto user2;

    @BeforeEach
    void setup() {
        user1 = new UserDto(1L, "Oleg", "oleg@mail.com", 22);
        user2 = new UserDto(2L, "Anna", "anna@mail.com", 25);
    }

    // Helper для конвертации объекта в JSON
    private String asJsonString(UserDto dto) throws Exception {
        return objectMapper.writeValueAsString(dto);
    }

    // ====== GET all users ======
    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = Arrays.asList(user1, user2);
        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Oleg"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Anna"));
    }

    // ====== GET user by ID ======
    @Test
    void testGetUserById() throws Exception {
        when(userService.get(1L)).thenReturn(user1);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Oleg"))
                .andExpect(jsonPath("$.email").value("oleg@mail.com"))
                .andExpect(jsonPath("$.age").value(22));
    }

    // ====== POST create user ======
    @Test
    void testCreateUser() throws Exception {
        when(userService.create(any(UserDto.class))).thenReturn(user1);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Oleg"))
                .andExpect(jsonPath("$.email").value("oleg@mail.com"))
                .andExpect(jsonPath("$.age").value(22));
    }

    // ====== PUT update user ======
    @Test
    void testUpdateUser() throws Exception {
        UserDto updatedUser = new UserDto(1L, "Oleg", "oleg@mail.com", 22);

        when(userService.update(eq(1L), any(UserDto.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Oleg"))
                .andExpect(jsonPath("$.email").value("oleg@mail.com"))
                .andExpect(jsonPath("$.age").value(22));
    }

    // ====== DELETE user ======
    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }
}