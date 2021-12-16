package br.com.banco_meutudo.dto;

import br.com.banco_meutudo.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ContaRetornoDto {

    private long id;
    private String banco;
    private String agencia;
    private String numero;
    private String digito;

    public static ContaRetornoDto valueOf(Conta conta) {
        return new ContaRetornoDto(conta.getId(), conta.getBanco().getDescricao(), conta.getAgencia(), conta.getNumero(), conta.getDigito());
    }

}
