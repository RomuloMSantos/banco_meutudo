package br.com.banco_meutudo.dto;

import br.com.banco_meutudo.model.Transferencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class TransferenciaRetornoDto {

    private Long id;
    private LocalDateTime dataTransferencia;
    private double valor;
    private ContaRetornoDto contaDestino;

    public static TransferenciaRetornoDto valueOf(Transferencia transferencia) {
        return new TransferenciaRetornoDto(transferencia.getId(), transferencia.getDataTransferencia(),
                transferencia.getValor(), ContaRetornoDto.valueOf(transferencia.getContaDestino()));
    }

}
