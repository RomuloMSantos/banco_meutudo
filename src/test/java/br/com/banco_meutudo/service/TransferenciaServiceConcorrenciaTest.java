package br.com.banco_meutudo.service;

import br.com.banco_meutudo.dto.TransferenciaDto;
import br.com.banco_meutudo.repository.TransferenciaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransferenciaServiceConcorrenciaTest {
    @Autowired
    TransferenciaService transferenciaService;

    @Autowired
    ContaService contaService;

    @Autowired
    TransferenciaRepository transferenciaRepository;

    @Test
    void deveRealizarAsDuasTransferencias() throws InterruptedException {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(2L);
        transferenciaDto.setValor(2000.00);

        TransferenciaDto transferenciaDto2 = new TransferenciaDto();
        transferenciaDto2.setIdContaOrigem(3L);
        transferenciaDto2.setIdContaDestino(2L);
        transferenciaDto2.setValor(10000.00);

        TransferenciaDto transferenciaDto3 = new TransferenciaDto();
        transferenciaDto3.setIdContaOrigem(2L);
        transferenciaDto3.setIdContaDestino(3L);
        transferenciaDto3.setValor(5000.00);

        double saldoConta1 = contaService.getSaldoById(1L);
        double saldoConta2 = contaService.getSaldoById(2L);
        double saldoConta3 = contaService.getSaldoById(3L);

        Thread thread = new Thread(() -> {
            transferenciaService.criar(transferenciaDto);
        });

        Thread thread2 = new Thread(() -> {
            transferenciaService.criar(transferenciaDto2);
        });

        Thread thread3 = new Thread(() -> {
            transferenciaService.criar(transferenciaDto3);
        });

        thread.start();
        thread2.start();
        thread3.start();

        Thread.sleep(1000L);

        long transferenciasExecutadas = transferenciaRepository.count();
        double saldoNovoConta1 = contaService.getSaldoById(1L);
        double saldoNovoConta2 = contaService.getSaldoById(2L);
        double saldoNovoConta3 = contaService.getSaldoById(3L);

        assertEquals(3, transferenciasExecutadas);
        assertEquals(saldoConta1 - 2000, saldoNovoConta1);
        assertEquals(saldoConta2 + 10000 + 2000 - 5000, saldoNovoConta2);
        assertEquals(saldoConta3 - 10000 + 5000, saldoNovoConta3);
    }
}
