package br.com.fiap.seniorsmart.models;

import org.springframework.hateoas.EntityModel;
import org.springframework.data.domain.Pageable;

import br.com.fiap.seniorsmart.controllers.PerguntaController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "T_SS_PERGUNTAS")
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_pergunta")
    private Long id;
    @NotNull
    @Column(name = "ds_pergunta", length = 2049)
    private String pergunta;

    public EntityModel<Pergunta> toEntityModel() {
        return EntityModel.of(
            this,
            linkTo(methodOn(PerguntaController.class).show(id)).withSelfRel(),
            linkTo(methodOn(PerguntaController.class).index(null, Pageable.unpaged())).withRel("all")
            );
    }
}
