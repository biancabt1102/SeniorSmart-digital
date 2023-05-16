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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable) {
        log.info("Buscar Planos");
        Page<Plano> planos = (busca == null) ?
            planoRepository.findAll(pageable):
            planoRepository.findByTipoPlanoContaining(busca, pageable);

        return assembler.toModel(planos.map(Plano::toEntityModel));
    }

    @GetMapping("{id}")
    public EntityModel<Plano> show(@PathVariable Long id) {
        log.info("Buscar Plano " + id);
        var plano = findByPlano(id);
        return plano.toEntityModel();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Plano plano) {
        log.info("Cadastrando Plano" + plano);
        planoRepository.save(plano);
        return ResponseEntity
            .created(plano.toEntityModel().getRequiredLink("self").toUri())
            .body(plano.toEntityModel());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deletando Plano");

        planoRepository.delete(findByPlano(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public EntityModel<Plano> update(@PathVariable Long id, @RequestBody Plano plano) {
        log.info("Alterar Plano " + id);
        findByPlano(id);

        plano.setId(id);
        planoRepository.save(plano);
        return plano.toEntityModel();
    }

    private Plano findByPlano(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
    }
}
