package com.example.teste03;

public class LoginResponse {
    private boolean success;
    private String message;
    // Inclua outros campos se necessários, como tokens ou informações do usuário

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
