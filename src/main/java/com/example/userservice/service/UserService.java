package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getAll() {
        log.info("Getting all users");
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserDto get(Long id) {
        log.info("Getting user with id {}", id);
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public UserDto create(UserDto dto) {
        log.info("Creating user {}", dto);
        User user = mapper.toEntity(dto);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        log.info("Updating user {} with id {}", dto, id);
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        return mapper.toDto(repository.save(user));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id {}", id);
        repository.deleteById(id);
    }
}
