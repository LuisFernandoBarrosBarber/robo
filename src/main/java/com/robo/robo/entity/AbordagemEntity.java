package com.robo.robo.entity;

import com.robo.robo.enumerator.EtapaAbordagem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "abordagem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbordagemEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDateTime criadoEm;

    private LocalDateTime ultimaTentativa;

    private String telefone;

    private String telefoneNaoFormatado;

    private String erro;

    @Column(name = "ativo")
    private boolean isAtivo;

    @Column(name = "sucesso")
    private boolean isSucesso;

    @Column
    @Enumerated(EnumType.STRING)
    private EtapaAbordagem etapaAbordagem;
}
