package br.com.fiap.seniorsmart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.seniorsmart.models.Plano;
import br.com.fiap.seniorsmart.repository.PlanoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/planos")
@Slf4j
public class PlanoController {

    @Autowired
    private PlanoRepository planoRepository;

    @GetMapping
    public List<Plano> index() {
        log.info("Buscar Planos");
        return planoRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Plano> show(@PathVariable Long id) {
        log.info("Buscar Plano " + id);
        return ResponseEntity.ok(findByPlano(id));
    }

    @PostMapping
    public ResponseEntity<Plano> create(@RequestBody @Valid Plano plano) {
        log.info("Cadastrando Plano" + plano);
        planoRepository.save(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(plano);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deletando Plano");

        planoRepository.delete(findByPlano(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Plano> update(@PathVariable Long id, @RequestBody Plano plano) {
        log.info("Alterar Plano " + id);
        findByPlano(id);

        plano.setId(id);
        planoRepository.save(plano);
        return ResponseEntity.ok(plano);
    }

    private Plano findByPlano(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
    }
}
