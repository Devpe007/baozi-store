package com.baozistore.baozi_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "Informe se o produto está em estoque.")
    @Column(nullable = false)
    private Boolean estoque;

    public Produto() {}

    public Produto(String nome, BigDecimal preco, Boolean estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Boolean getEstoque() { return estoque; }
    public void setEstoque(Boolean estoque) { this.estoque = estoque; }
}
