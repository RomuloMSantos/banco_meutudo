package br.com.banco_meutudo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CONTA", schema = "SIMULADOR")
public class Conta {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "AGENCIA")
    private String agencia;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "DIGITO")
    private String digito;

    @ManyToOne
    @JoinColumn(name="BANCO_FK")
    private Banco banco;

    @OneToMany(mappedBy = "conta", targetEntity = Movimentacao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Movimentacao> listaMovimentacao;

}
