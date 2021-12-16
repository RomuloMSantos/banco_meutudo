package br.com.banco_meutudo.enums;

import lombok.Getter;

@Getter
public enum TipoMovimentacaoEnum {

    RECEITA(1, "RECEITA"),
    DESPESA(2, "DESPESA");

    private Integer codigo;
    private String descricao;

    TipoMovimentacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

}
