package br.com.fiap.seniorsmart.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "T_SS_USUARIO_PERGUNTAS")
public class UsuarioPergunta {
    
    @EmbeddedId
    @ManyToOne
    @JoinColumn(name = "cd_usuario")
    private Usuario usuario;

    @EmbeddedId
    @ManyToOne
    @JoinColumn(name = "cd_pergunta")
    private Pergunta pergunta;
}
