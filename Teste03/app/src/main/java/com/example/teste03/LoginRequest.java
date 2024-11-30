package com.example.teste03;

public class LoginRequest {
    private String matricula;
    private String password;

    public LoginRequest(String matricula, String password) {
        this.matricula = matricula;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "matricula='" + matricula + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
