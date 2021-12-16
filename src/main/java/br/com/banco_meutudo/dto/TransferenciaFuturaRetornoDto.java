package br.com.banco_meutudo.dto;

import br.com.banco_meutudo.model.Transferencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class TransferenciaFuturaRetornoDto {

    private Long id;
    private LocalDateTime dataTransferencia;
    private LocalDateTime dataCriacao;
    private double valor;
    private ContaRetornoDto contaDestino;

    public static TransferenciaFuturaRetornoDto valueOf(Transferencia transferencia) {
        return new TransferenciaFuturaRetornoDto(transferencia.getId(), transferencia.getDataTransferencia(),
                transferencia.getDataCriacao(), transferencia.getValor(), ContaRetornoDto.valueOf(transferencia.getContaDestino()));
    }

}
