package br.com.fiap.seniorsmart.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.seniorsmart.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Page<Usuario> findByNomeContaining(String busca, Pageable pageable);

    Optional<Usuario> findByEmail(String email);

    // Consulta personalizada para buscar um usuário pelo ID do plano
    @Query("SELECT u FROM Usuario u WHERE u.plano.id = :idPlano")
    Optional<Usuario> findByPlanoId(@Param("idPlano") Long idPlano);

}
