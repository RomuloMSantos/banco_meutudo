package br.com.banco_meutudo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TRANSFERENCIA", schema = "SIMULADOR")
public class Transferencia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "DATA_TRANSFERENCIA", updatable = false, nullable = false)
    private LocalDateTime dataTransferencia;

    @CreationTimestamp
    @Column(name = "DATA_CRIACAO", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "VALOR", updatable = false, nullable = false)
    private double valor;

    @Column(name = "EXECUTADA", updatable = false)
    private boolean executada;

    @Column(name = "ESTORNO", updatable = false)
    private boolean estorno;

    @ManyToOne
    @JoinColumn(name="CONTA_ORIGEM_FK", updatable = false, nullable = false)
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name="CONTA_DESTINO_FK", updatable = false, nullable = false)
    private Conta contaDestino;

    @OneToOne
    @JoinColumn(name = "TRANSFERENCIA_ESTORNADA_FK", updatable = false)
    private Transferencia transferenciaEstornada;

}
