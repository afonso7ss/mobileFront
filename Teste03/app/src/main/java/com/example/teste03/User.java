package com.example.teste03;

public class User {

    private int id;
    private String name;
    private String matricula;
    private String password;
    private boolean funcionario;

    // Construtor para login
    public User(String matricula, String password) {
        this.matricula = matricula;
        this.password = password;
    }

    // Getters e Setters
    // ...

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMatricula() {
        return matricula;
    }

    public boolean isFuncionario() {
        return funcionario;
    }

    // Outros getters e setters
}