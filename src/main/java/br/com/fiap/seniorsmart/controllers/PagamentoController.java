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

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        log.info("Buscar Pagamentos");
        Page<Pagamento> pagamentos = (busca == null) ?
            pagamentoRepository.findAll(pageable):
            pagamentoRepository.findByNomeNoCartaoContaining(busca, pageable);

        return assembler.toModel(pagamentos.map(Pagamento::toEntityModel));
    }

    @GetMapping("{id}")
    public EntityModel<Pagamento> show(@PathVariable Long id) {
        log.info("Buscar Pagamento " + id);
        var pagamento = findByPagamento(id);
        return pagamento.toEntityModel();
    }

    @GetMapping("/buscarPorPlano/{planoId}")
    public PagedModel<EntityModel<Object>> buscaPagamentosPorPlano(@PathVariable Long planoId, @PageableDefault(size = 5) Pageable pageable) {
        log.info("Buscar Pagamentos pelo ID do Plano: " + planoId);
        Page<Pagamento> pagamentos = pagamentoRepository.findByPlanoId(planoId, pageable);
        return assembler.toModel(pagamentos.map(Pagamento::toEntityModel));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> create(@RequestBody @Valid Pagamento pagamento) {
        log.info("Cadastrando Pagamento " + pagamento);
        pagamentoRepository.save(pagamento);
        return ResponseEntity
            .created(pagamento.toEntityModel().getRequiredLink("self").toUri())
            .body(pagamento.toEntityModel());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Pagamento> delete(@PathVariable Long id) {
        log.info("Deletando Pagamento");

        pagamentoRepository.delete(findByPagamento(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
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