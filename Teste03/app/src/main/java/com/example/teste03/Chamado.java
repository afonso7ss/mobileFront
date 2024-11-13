package com.example.teste03.models;

public class Chamado {

    private int id;
    private String categoria;
    private String local;
    private String descricao;
    private boolean status;
    private int userId;
    private Integer funcionarioId;

    // Construtores, getters e setters

    public Chamado() {
    }

    public Chamado(String categoria, String local, String descricao, int userId) {
        this.categoria = categoria;
        this.local = local;
        this.descricao = descricao;
        this.userId = userId;
        this.status = false; // Padrão é falso
    }

    // Getters e Setters
    // ...

    public int getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}
