package br.com.fiap.seniorsmart.models;

import org.springframework.hateoas.EntityModel;

import br.com.fiap.seniorsmart.controllers.PerguntaController;
import br.com.fiap.seniorsmart.controllers.RespostaController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "T_SS_RESPOSTA")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_resposta")
    private long id;
    @NotNull
    @Column(name = "ds_resposta", length = 2049)
    private String resposta;

    @OneToOne
    @JoinColumn(name = "cd_pergunta")
    private Pergunta pergunta;

    public EntityModel<Resposta> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(RespostaController.class).show(id)).withSelfRel(),
            linkTo(methodOn(RespostaController.class).index(null, Pageable.unpaged())).withRel("all"),

            linkTo(methodOn(PerguntaController.class).show(this.getPergunta().getId())).withRel("pergunta")
        );
    }
}
