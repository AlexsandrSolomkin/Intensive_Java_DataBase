package org.example.userservice.dto;

public class UserEventDto {
    private String operation;
    private String email;

    public UserEventDto() {}
    public UserEventDto(String operation, String email) {
        this.operation = operation;
        this.email = email;
    }

    // Getters и Setters
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
