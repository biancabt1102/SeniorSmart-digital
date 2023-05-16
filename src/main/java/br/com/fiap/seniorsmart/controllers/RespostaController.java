package br.com.fiap.seniorsmart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
    
    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        log.info("Buscar Respostas");        
        Page<Resposta> resposta = (busca == null) ?
            respostaRepository.findAll(pageable):
            respostaRepository.findByRespostaContaining(busca, pageable);

        return assembler.toModel(resposta.map(Resposta::toEntityModel));
    }

    @GetMapping("{id}")
    public EntityModel<Resposta> show(@PathVariable Long id) {
        log.info("Buscar Resposta " + id);
        var resposta = findByResposta(id);
        return resposta.toEntityModel();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Resposta resposta) {
        log.info("Cadastrando Resposta" + resposta);
        respostaRepository.save(resposta);
        return ResponseEntity
            .created(resposta.toEntityModel().getRequiredLink("self").toUri())
            .body(resposta.toEntityModel());
    }

    private Resposta findByResposta(Long id) {
        return respostaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));
    }

}
