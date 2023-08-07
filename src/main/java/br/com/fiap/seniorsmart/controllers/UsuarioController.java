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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import br.com.fiap.seniorsmart.models.Credencial;
import br.com.fiap.seniorsmart.models.Token;
import br.com.fiap.seniorsmart.models.Usuario;
import br.com.fiap.seniorsmart.repository.UsuarioRepository;
import br.com.fiap.seniorsmart.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/usuarios")
@Tag(name = "Usuário", description = "Manipulação de dados dos usuários cadastrados")
@Slf4j
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
    PagedResourcesAssembler<Object> assembler;

	@Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

	@GetMapping
	@SecurityRequirement(name = "bearer-key")
	@Operation(
		summary = "Listar usuários", 
		description = "Retorna todos os usuários cadastrados"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Listagem feita com sucesso"),
		@ApiResponse(responseCode = "404", description = "Lista não encontrada"),
	})
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @ParameterObject @PageableDefault(size = 5) Pageable pageable){        
        log.info("Buscar Usuários");

		Page<Usuario> usuarios = (busca == null) ?
            usuarioRepository.findAll(pageable):
            usuarioRepository.findByNomeContaining(busca, pageable);

        return assembler.toModel(usuarios.map(Usuario::toEntityModel));
    }

	@GetMapping("{id}")
	@SecurityRequirement(name = "bearer-key")
	@Operation(
		summary = "Detalhes usuário", 
		description = "Retorna o usuário cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Os dados foram retornados com sucesso"),
		@ApiResponse(responseCode = "404", description = "Não foi encontrado um usuário com esse id"),
	})
	public EntityModel<Usuario> show(@PathVariable Long id) {
		log.info("Buscar Usuário " + id);
		var usuario = findByUsuario(id);
		return usuario.toEntityModel();
	}

	@GetMapping("/por-plano/{idPlano}")
	@SecurityRequirement(name = "bearer-key")
	@Operation(
		summary = "Encontrar usuário por id do plano", 
		description = "Retorna o usuário cadastrado com o id do plano informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Os dados foram retornados com sucesso"),
		@ApiResponse(responseCode = "404", description = "Não foi encontrado um usuário com o id desse plano"),
	})
    public ResponseEntity<EntityModel<Usuario>> getByPlanoId(@PathVariable Long idPlano) {
        log.info("Buscar Usuário pelo ID do Plano");

        Optional<Usuario> usuario = usuarioRepository.findByPlanoId(idPlano);

        return usuario.map(u -> ResponseEntity.ok(u.toEntityModel())).orElse(ResponseEntity.notFound().build());
    }

	@GetMapping("/email")
	@SecurityRequirement(name = "bearer-key")
	@Operation(
		summary = "Encontrar usuário pelo email", 
		description = "Retorna o usuário cadastrado com o email informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Os dados foram retornados com sucesso"),
		@ApiResponse(responseCode = "404", description = "Não foi encontrado um usuário com esse email"),
	})
    public ResponseEntity<Usuario> buscaUsuarioPorEmail(@RequestParam String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	

	@PostMapping("/cadastro")
	@Operation(
		summary = "Cadastrar usuário", 
		description = "Cadastrando o usuário com os campos requisitados"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Usuário criada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Usuário invalidos"),
		@ApiResponse(responseCode = "409", description = "Já existe um usuário com o e-mail fornecido"),
	})
	public ResponseEntity<Object> create(@ParameterObject @RequestBody @Valid Usuario usuario) {
		log.info("Cadastrando Usuário" + usuario);
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
		return ResponseEntity
            .created(usuario.toEntityModel().getRequiredLink("self").toUri())
            .body(usuario.toEntityModel());
	}

	@DeleteMapping("{id}")
	@SecurityRequirement(name = "bearer-key")
	@Operation(
		summary = "Excluindo usuário", 
		description = "Exclui o usuário cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Requisição bem-sucedida"),
		@ApiResponse(responseCode = "404", description = "Conteúdo não encontrado"),
	})
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		log.info("Deletando Usuário");

		usuarioRepository.delete(findByUsuario(id));
		return ResponseEntity.noContent().build();
	}

	@PutMapping("{id}")
	@SecurityRequirement(name = "bearer-key")
	@Operation(
		summary = "Alterar dados do usuário", 
		description = "Alteração de dados do usuário cadastrado com o id informado"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Alteração inválida"),
		@ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
	})
	public EntityModel<Usuario> update(@PathVariable @Valid Long id, @ParameterObject @RequestBody Usuario usuario) {
        log.info("Alterar Usuário " + id);
		findByUsuario(id);

		usuario.setId(id);
		usuarioRepository.save(usuario);
		return usuario.toEntityModel();
	}


    @PostMapping("/login")
	@Operation(
		summary = "Login do usuário", 
		description = "Loga o usuário retornando o token do mesmo"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Login realizada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Campos inválidos")
	})
    public ResponseEntity<Token> login(@RequestBody @ParameterObject Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

	private Usuario findByUsuario(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
	}

}
