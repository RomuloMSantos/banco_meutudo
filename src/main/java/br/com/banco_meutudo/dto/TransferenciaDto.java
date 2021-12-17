package br.com.banco_meutudo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferenciaDto {

    @DecimalMin(value = "0.01", message = "Valor inválido! Mínimo é R$0,01")
    private double valor;
    @Min(value = 1, message = "Conta Origem inválida!")
    private long idContaOrigem;
    @Min(value = 1, message = "Conta Origem inválida!")
    private long idContaDestino;

}
