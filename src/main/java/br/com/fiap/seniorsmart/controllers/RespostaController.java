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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.seniorsmart.models.Resposta;
import br.com.fiap.seniorsmart.repository.RespostaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/resposta")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Resposta", description = "Manipulação de dados das respostas feitas pelo chatbot")
@Slf4j
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;
    
    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
        @Operation(
		summary = "Listar respostas", 
		description = "Retorna todas as respostas cadastradas"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Listagem feita com sucesso"),
		@ApiResponse(responseCode = "404", description = "Lista não encontrada")
	})
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @ParameterObject @PageableDefault(size = 5) Pageable pageable){
        log.info("Buscar Respostas");        
        Page<Resposta> resposta = (busca == null) ?
            respostaRepository.findAll(pageable):
            respostaRepository.findByRespostaContaining(busca, pageable);

        return assembler.toModel(resposta.map(Resposta::toEntityModel));
    }

    @GetMapping("{id}")
    @Operation(
		summary = "Detalhes resposta", 
		description = "Retorna a resposta cadastrada com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Os dados foram retornados com sucesso"),
		@ApiResponse(responseCode = "404", description = "Não foi encontrado uma resposta com esse id")
	})
    public EntityModel<Resposta> show(@PathVariable Long id) {
        log.info("Buscar Resposta " + id);
        var resposta = findByResposta(id);
        return resposta.toEntityModel();
    }

    @PostMapping
    @Operation(
		summary = "Cadastrar resposta", 
		description = "Cadastrando a resposta com os campos requisitados"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Resposta criada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Resposta inválida")
	})
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
