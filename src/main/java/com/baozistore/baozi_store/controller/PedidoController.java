package com.baozistore.baozi_store.controller;

import com.baozistore.baozi_store.exception.RecursoNaoEncontradoException;
import com.baozistore.baozi_store.model.Pedido;
import com.baozistore.baozi_store.repository.ClienteRepository;
import com.baozistore.baozi_store.repository.PedidoRepository;
import com.baozistore.baozi_store.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido pedido) {
        pedido.setId(null);
        validarReferencias(pedido);
        Pedido salvo = pedidoRepository.save(pedido);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: " + id));
    }

    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @Valid @RequestBody Pedido dados) {
        Pedido pedido = buscarPorId(id);
        validarReferencias(dados);
        pedido.setClienteId(dados.getClienteId());
        pedido.setProdutoId(dados.getProdutoId());
        pedido.setQuantidade(dados.getQuantidade());
        return pedidoRepository.save(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Pedido pedido = buscarPorId(id);
        pedidoRepository.delete(pedido);
        return ResponseEntity.noContent().build();
    }

    private void validarReferencias(Pedido pedido) {
        if (!clienteRepository.existsById(pedido.getClienteId())) {
            throw new IllegalArgumentException("Cliente não encontrado: " + pedido.getClienteId());
        }
        if (!produtoRepository.existsById(pedido.getProdutoId())) {
            throw new IllegalArgumentException("Produto não encontrado: " + pedido.getProdutoId());
        }
    }
}
