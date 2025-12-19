package org.example.userservice.service;

import org.example.userservice.dao.UserRepository;
import org.example.userservice.dto.UserEventDto;
import org.example.userservice.entity.User;
import org.example.userservice.exception.ResourceNotFoundException;
import org.example.userservice.kafka.UserEventProducer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserEventProducer producer;

    public UserServiceImpl(UserRepository repository, UserEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Override
    public User create(User user) {
        User saved = repository.save(user);
        producer.sendEvent(new UserEventDto("CREATE", saved.getEmail()));
        return saved;
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User update(Long id, User user) {
        User existing = getById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setAge(user.getAge());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        User existing = getById(id);
        repository.delete(existing);
        producer.sendEvent(new UserEventDto("DELETE", existing.getEmail()));
    }
}
