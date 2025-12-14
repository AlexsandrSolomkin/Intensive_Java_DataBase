package org.example.userservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.userservice.entity.User;
import org.example.userservice.repository.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public User createUser(User user) throws Exception {
        User saved = repository.save(user);
        sendKafkaEvent(saved.getEmail(), "CREATE");
        return saved;
    }

    public void deleteUser(Long id) throws Exception {
        Optional<User> user = repository.findById(id);
        if(user.isPresent()) {
            repository.deleteById(id);
            sendKafkaEvent(user.get().getEmail(), "DELETE");
        }
    }

    private void sendKafkaEvent(String email, String operation) throws Exception {
        String message = objectMapper.writeValueAsString(new UserEvent(email, operation));
        kafkaTemplate.send("user-events", message);
    }

    // Inner class for event
    static class UserEvent {
        public String email;
        public String operation;
        public UserEvent(String email, String operation) { this.email = email; this.operation = operation; }
    }
}
