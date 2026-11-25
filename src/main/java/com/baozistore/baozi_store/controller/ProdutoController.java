package com.baozistore.baozi_store.controller;

import com.baozistore.baozi_store.exception.RecursoNaoEncontradoException;
import com.baozistore.baozi_store.model.Produto;
import com.baozistore.baozi_store.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        produto.setId(null);
        Produto salvo = repository.save(produto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @Valid @RequestBody Produto dados) {
        Produto produto = buscarPorId(id);
        produto.setNome(dados.getNome());
        produto.setPreco(dados.getPreco());
        produto.setEstoque(dados.getEstoque());
        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Produto produto = buscarPorId(id);
        repository.delete(produto);
        return ResponseEntity.noContent().build();
    }
}
