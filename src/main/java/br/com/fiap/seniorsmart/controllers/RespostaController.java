package br.com.fiap.seniorsmart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.seniorsmart.models.Resposta;
import br.com.fiap.seniorsmart.repository.RespostaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/resposta")
@Slf4j
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @GetMapping
    public Page<Resposta> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        log.info("Buscar Respostas");        
        if (busca == null) 
            return respostaRepository.findAll(pageable);
        return respostaRepository.findByRespostaContaining(busca, pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> show(@PathVariable Long id) {
        log.info("Buscar Resposta " + id);
        return ResponseEntity.ok(findByResposta(id));
    }

    @PostMapping
    public ResponseEntity<Resposta> create(@RequestBody @Valid Resposta resposta) {
        log.info("Cadastrando Resposta" + resposta);
        respostaRepository.save(resposta);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    private Resposta findByResposta(Long id) {
        return respostaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));
    }

}
