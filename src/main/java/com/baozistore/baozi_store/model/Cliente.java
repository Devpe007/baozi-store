package com.baozistore.baozi_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório.")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotNull(message = "A data de cadastro é obrigatória.")
    @Column(name = "cliente_desde", nullable = false)
    private LocalDate clienteDesde;

    public Cliente() {}

    public Cliente(String nome, LocalDate clienteDesde) {
        this.nome = nome;
        this.clienteDesde = clienteDesde;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getClienteDesde() { return clienteDesde; }
    public void setClienteDesde(LocalDate clienteDesde) { this.clienteDesde = clienteDesde; }
}
