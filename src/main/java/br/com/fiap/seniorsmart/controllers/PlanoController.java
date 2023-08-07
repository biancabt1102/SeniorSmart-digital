package br.com.fiap.seniorsmart.controllers;

import org.springdoc.core.annotations.ParameterObject;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/planos")
@Tag(name = "Plano", description = "Manipulação de dados dos planos feitos pelos usuários cadastrados")
@Slf4j
public class PlanoController {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Listar planos", 
		description = "Retorna todos os planos cadastrados"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Listagem feita com sucesso"),
		@ApiResponse(responseCode = "404", description = "Lista não encontrada")
	})
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        log.info("Buscar Planos");
        Page<Plano> planos = (busca == null) ?
            planoRepository.findAll(pageable):
            planoRepository.findByTipoPlanoContaining(busca, pageable);

        return assembler.toModel(planos.map(Plano::toEntityModel));
    }

    @GetMapping("{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Detalhes plano", 
		description = "Retorna o plano cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Os dados foram retornados com sucesso"),
		@ApiResponse(responseCode = "404", description = "Não foi encontrado um plano com esse id")
	})
    public EntityModel<Plano> show(@PathVariable Long id) {
        log.info("Buscar Plano " + id);
        var plano = findByPlano(id);
        return plano.toEntityModel();
    }

    @PostMapping("/cadastro")
	@Operation(
		summary = "Cadastrar plano", 
		description = "Cadastrando o plano com os campos requisitados"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Plano criado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Plano inválido")
	})
    public ResponseEntity<Object> create(@RequestBody @ParameterObject @Valid Plano plano) {
        log.info("Cadastrando Plano" + plano);
        planoRepository.save(plano);
        return ResponseEntity
            .created(plano.toEntityModel().getRequiredLink("self").toUri())
            .body(plano.toEntityModel());
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Excluindo plano", 
		description = "Exclui o plano cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Requisição bem-sucedida"),
		@ApiResponse(responseCode = "404", description = "Conteúdo não encontrado"),
	})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deletando Plano");

        planoRepository.delete(findByPlano(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Alterar dados do plano", 
		description = "Alteração de dados do plano cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Alteração inválida"),
		@ApiResponse(responseCode = "404", description = "Plano não encontrada")
	})
    public EntityModel<Plano> update(@PathVariable Long id, @ParameterObject @RequestBody Plano plano) {
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
