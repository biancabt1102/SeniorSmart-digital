package br.com.fiap.seniorsmart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.seniorsmart.models.Pergunta;
import br.com.fiap.seniorsmart.repository.PerguntaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/pergunta")
@Slf4j
public class PerguntaController {

    @Autowired
    PerguntaRepository perguntaRepository;

    @GetMapping
    public Page<Pergunta> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        log.info("Buscar Perguntas");        
        if (busca == null) 
            return perguntaRepository.findAll(pageable);
        return perguntaRepository.findByPerguntaContaining(busca, pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> show(@PathVariable Long id) {
        log.info("Buscar Pergunta " + id);
        return ResponseEntity.ok(findByPergunta(id));
    }

    @PostMapping
    public ResponseEntity<Pergunta> create(@RequestBody @Valid Pergunta pergunta) {
        log.info("Cadastrando Pergunta" + pergunta);
        perguntaRepository.save(pergunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(pergunta) ;
    }

    private Pergunta findByPergunta(Long id) {
        return perguntaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pergunta não encontrada"));
    }

}
