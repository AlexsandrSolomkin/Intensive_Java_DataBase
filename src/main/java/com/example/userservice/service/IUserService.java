package com.example.userservice.service;

import com.example.userservice.dto.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> getAll();
    UserDto get(Long id);
    UserDto create(UserDto dto);
    UserDto update(Long id, UserDto dto);
    void delete(Long id);
}
