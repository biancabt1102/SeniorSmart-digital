package br.com.fiap.seniorsmart.controllers;

import java.util.Optional;

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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/usuarios")
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
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){        
        log.info("Buscar Usuários");

		Page<Usuario> usuarios = (busca == null) ?
            usuarioRepository.findAll(pageable):
            usuarioRepository.findByNomeContaining(busca, pageable);

        return assembler.toModel(usuarios.map(Usuario::toEntityModel));
    }

	@GetMapping("{id}")
	public EntityModel<Usuario> show(@PathVariable Long id) {
		log.info("Buscar Usuário " + id);
		var usuario = findByUsuario(id);
		return usuario.toEntityModel();
	}

	@GetMapping("/por-plano/{idPlano}")
    public ResponseEntity<EntityModel<Usuario>> getByPlanoId(@PathVariable Long idPlano) {
        log.info("Buscar Usuário pelo ID do Plano");

        Optional<Usuario> usuario = usuarioRepository.findByPlanoId(idPlano);

        return usuario.map(u -> ResponseEntity.ok(u.toEntityModel())).orElse(ResponseEntity.notFound().build());
    }

	@GetMapping("/email")
    public ResponseEntity<Usuario> buscaUsuarioPorEmail(@RequestParam String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	

	@PostMapping("/cadastro")
	public ResponseEntity<Object> create(@RequestBody @Valid Usuario usuario) {
		log.info("Cadastrando Usuário" + usuario);
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
		return ResponseEntity
            .created(usuario.toEntityModel().getRequiredLink("self").toUri())
            .body(usuario.toEntityModel());
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		log.info("Deletando Usuário");

		usuarioRepository.delete(findByUsuario(id));
		return ResponseEntity.noContent().build();
	}

	@PutMapping("{id}")
	public EntityModel<Usuario> update(@PathVariable @Valid Long id, @RequestBody Usuario usuario) {
        log.info("Alterar Usuário " + id);
		findByUsuario(id);

		usuario.setId(id);
		usuarioRepository.save(usuario);
		return usuario.toEntityModel();
	}


    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

	private Usuario findByUsuario(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
	}

}
