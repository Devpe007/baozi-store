package com.baozistore.baozi_store.controller;

import com.baozistore.baozi_store.exception.RecursoNaoEncontradoException;
import com.baozistore.baozi_store.model.Cliente;
import com.baozistore.baozi_store.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
        cliente.setId(null);
        Cliente salvo = repository.save(cliente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + id));
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @Valid @RequestBody Cliente dados) {
        Cliente cliente = buscarPorId(id);
        cliente.setNome(dados.getNome());
        cliente.setClienteDesde(dados.getClienteDesde());
        return repository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Cliente cliente = buscarPorId(id);
        repository.delete(cliente);
        return ResponseEntity.noContent().build();
    }
}
