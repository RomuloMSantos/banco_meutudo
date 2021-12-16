package br.com.banco_meutudo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ContaDto {

    @NotNull(message = "Id do banco não pode ser nulo")
    private Long idBanco;
    @NotNull(message = "Agência não pode ser nula")
    @NotBlank(message = "Agência não pode ser vazia")
    private String agencia;
    @NotNull(message = "Número não pode ser nulo")
    @NotBlank(message = "Número não pode ser vazio")
    private String numero;
    @NotNull(message = "Digito não pode ser nulo")
    @NotBlank(message = "Digito não pode ser vazio")
    private String digito;

}
