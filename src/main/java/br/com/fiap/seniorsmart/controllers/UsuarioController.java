package br.com.fiap.seniorsmart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import br.com.fiap.seniorsmart.models.Usuario;
import br.com.fiap.seniorsmart.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/usuarios")
@Slf4j
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping
    public Page<Usuario> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){        
        log.info("Buscar Usuários");
        if (busca == null) 
            return usuarioRepository.findAll(pageable);
        return usuarioRepository.findByNomeContaining(busca, pageable);
    }

	@GetMapping("{id}")
	public ResponseEntity<Object> show(@PathVariable Long id) {
		log.info("Buscar Usuário " + id);
		return ResponseEntity.ok(findByUsuario(id));
	}

	@PostMapping
	public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario usuario) {
		log.info("Cadastrando Usuário" + usuario);
		usuarioRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		log.info("Deletando Usuário");

		usuarioRepository.delete(findByUsuario(id));
		return ResponseEntity.noContent().build();
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> update(@PathVariable @Valid Long id, @RequestBody Usuario usuario) {
        log.info("Alterar Usuário " + id);
		findByUsuario(id);

		usuario.setId(id);
		usuarioRepository.save(usuario);
		return ResponseEntity.ok(usuario);
	}

	private Usuario findByUsuario(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
	}

}
