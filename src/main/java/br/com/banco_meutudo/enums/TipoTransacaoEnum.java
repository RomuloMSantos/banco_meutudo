package br.com.banco_meutudo.enums;

import lombok.Getter;

@Getter
public enum TipoTransacaoEnum {

    SAQUE(1, "SAQUE"),
    DEPOSITO(2, "DEPÓSITO"),
    TRANSFERENCIA(3, "TRANSFERÊNCIA"),
    INVESTIMENTO(4, "INVESTIMENTO"),
    SALARIO(5, "SALÁRIO"),
    ESTORNO(6, "ESTORNO");

    private Integer codigo;
    private String descricao;

    TipoTransacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

}
