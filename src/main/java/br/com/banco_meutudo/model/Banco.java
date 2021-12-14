package br.com.banco_meutudo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "BANCO", schema = "SIMULADOR")
public class Banco {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "DESCRICAO")
    private String descricao;

}
