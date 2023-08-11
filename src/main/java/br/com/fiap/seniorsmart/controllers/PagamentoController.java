package br.com.fiap.seniorsmart.controllers;

import java.util.Optional;

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

import br.com.fiap.seniorsmart.models.Pagamento;
import br.com.fiap.seniorsmart.models.Usuario;
import br.com.fiap.seniorsmart.repository.PagamentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/pagamentos")
@Tag(name = "Pagamento", description = "Manipulação de dados dos pagamentos feitos pelos usuários cadastrados")
@Slf4j
public class PagamentoController {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Listar pagamentos", 
		description = "Retorna todos os pagamentos cadastrados"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Listagem feita com sucesso"),
		@ApiResponse(responseCode = "404", description = "Lista não encontrada"),
	})
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @ParameterObject @PageableDefault(size = 5) Pageable pageable){
        log.info("Buscar Pagamentos");
        Page<Pagamento> pagamentos = (busca == null) ?
            pagamentoRepository.findAll(pageable):
            pagamentoRepository.findByNomeNoCartaoContaining(busca, pageable);

        return assembler.toModel(pagamentos.map(Pagamento::toEntityModel));
    }

    @GetMapping("{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Detalhes pagamento", 
		description = "Retorna o pagamento cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Os dados foram retornados com sucesso"),
		@ApiResponse(responseCode = "404", description = "Não foi encontrado um pagamento com esse id"),
	})
    public EntityModel<Pagamento> show(@PathVariable Long id) {
        log.info("Buscar Pagamento " + id);
        var pagamento = findByPagamento(id);
        return pagamento.toEntityModel();
    }

    @GetMapping("/buscarPorPlano/{planoId}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Encontrar pagamento por id do plano", 
		description = "Retorna o pagamento cadastrado com o id do plano informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Os dados foram retornados com sucesso"),
		@ApiResponse(responseCode = "404", description = "Não foi encontrado um pagamento com o id desse plano"),
	})
    public ResponseEntity<EntityModel<Pagamento>> buscaPagamentosPorPlano(@PathVariable Long planoId) {
        log.info("Buscar Pagamentos pelo ID do Plano");
        Optional<Pagamento> pagamento = pagamentoRepository.findByPlanoId(planoId);
        
        return pagamento.map(p -> ResponseEntity.ok(p.toEntityModel())).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cadastro")
	@Operation(
		summary = "Cadastrar pagamento", 
		description = "Cadastrando o pagamento com os campos requisitados"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Pagamento invalidos"),
		@ApiResponse(responseCode = "409", description = "Já existe um pagamento para o usuário informado"),
	})
    public ResponseEntity<Object> create(@RequestBody @Valid Pagamento pagamento) {
        log.info("Cadastrando Pagamento " + pagamento);
        pagamentoRepository.save(pagamento);
        return ResponseEntity
            .created(pagamento.toEntityModel().getRequiredLink("self").toUri())
            .body(pagamento.toEntityModel());
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Excluindo pagamento", 
		description = "Exclui o pagamento cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Requisição bem-sucedida"),
		@ApiResponse(responseCode = "404", description = "Conteúdo não encontrado"),
	})
    public ResponseEntity<Pagamento> delete(@PathVariable Long id) {
        log.info("Deletando Pagamento");

        pagamentoRepository.delete(findByPagamento(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
		summary = "Alterar dados do pagamento", 
		description = "Alteração de dados do pagamento cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Alteração inválida"),
		@ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
	})
    public EntityModel<Pagamento> update(@PathVariable @Valid Long id, @RequestBody Pagamento pagamento) {
        log.info("Alterar Pagamento " + id);
        findByPagamento(id);

        pagamento.setId(id);
        pagamentoRepository.save(pagamento);
        return pagamento.toEntityModel();
    }

    private Pagamento findByPagamento(Long id) {
        return pagamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado"));
    }
}