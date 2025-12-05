package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public UserDto get(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserDto create(UserDto dto) {
        User user = mapper.toEntity(dto);
        return mapper.toDto(repository.save(user));
    }

    public UserDto update(Long id, UserDto dto) {
        User user = repository.findById(id).orElseThrow();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());

        return mapper.toDto(repository.save(user));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}