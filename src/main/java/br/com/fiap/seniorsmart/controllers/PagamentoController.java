package br.com.fiap.seniorsmart.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.seniorsmart.models.Pagamento;
import br.com.fiap.seniorsmart.repository.PagamentoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/pagamentos")
@Slf4j
public class PagamentoController {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @GetMapping
    public List<Pagamento> index() {
        log.info("Buscar Pagamentos");
        return pagamentoRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> show(@PathVariable Long id) {
        log.info("Buscar Pagamento " + id);
        return ResponseEntity.ok(findByPagamento(id));
    }

    @PostMapping
    public ResponseEntity<Pagamento> create(@RequestBody @Valid Pagamento pagamento) {
        log.info("Cadastrando Pagamento " + pagamento);
        pagamentoRepository.save(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Deletando Pagamento");

        pagamentoRepository.delete(findByPagamento(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable @Valid Long id, @RequestBody Pagamento pagamento) {
        log.info("Alterar Pagamento " + id);
        findByPagamento(id);

        pagamento.setId(id);
        pagamentoRepository.save(pagamento);
        return ResponseEntity.ok(pagamento);
    }

    private Pagamento findByPagamento(Long id) {
        return pagamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado"));
    }
}