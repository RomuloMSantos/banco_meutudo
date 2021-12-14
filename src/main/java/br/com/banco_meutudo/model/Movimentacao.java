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
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

    @Column(name = "TIPO")
    private int tipo;

    @Column(name = "VALOR")
    private double valor;

    @ManyToOne
    @JoinColumn(name = "CONTA_FK")
    private Conta conta;
}
