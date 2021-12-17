package br.com.banco_meutudo.enums;

import lombok.Getter;

@Getter
public enum TipoTransacaoEnum {

    SAQUE(1, "SAQUE"),
    DEPOSITO(2, "DEPOSITO"),
    TRANSFERENCIA(3, "TRANSFERENCIA"),
    INVESTIMENTO(4, "INVESTIMENTO");

    private Integer codigo;
    private String descricao;

    TipoTransacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

}
