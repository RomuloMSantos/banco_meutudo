package br.com.banco_meutudo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class TransferenciaFuturaDto extends TransferenciaDto {

    @NotNull(message = "Data de transferência não pode ser nula!")
    @Future(message = "Data de transferência não pode ser menor ou igual à data atual!")
    private LocalDate dataTransferencia;
    @Min(value = 1, message = "Quantidade de parcelas não pode ser menor que 1!")
    private int quantidadeParcelas;

}
