package br.com.banco_meutudo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MOVIMENTACAO", schema = "SIMULADOR")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "DATA_CRIACAO", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "TIPO", updatable = false, nullable = false)
    private int tipo;

    @Column(name = "TIPO_TRANSACAO", updatable = false, nullable = false)
    private int tipoTransacao;

    @Column(name = "VALOR", updatable = false, nullable = false)
    private double valor;

    @ManyToOne
    @JoinColumn(name = "CONTA_FK", updatable = false, nullable = false)
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "TRANSFERENCIA_FK", updatable = false)
    private Transferencia transferencia;

    @Version
    private Long version;
}
