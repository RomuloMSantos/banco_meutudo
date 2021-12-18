package br.com.banco_meutudo.service;

import br.com.banco_meutudo.dto.TransferenciaDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaRetornoDto;
import br.com.banco_meutudo.exception.*;
import br.com.banco_meutudo.model.Banco;
import br.com.banco_meutudo.model.Conta;
import br.com.banco_meutudo.model.Transferencia;
import br.com.banco_meutudo.repository.TransferenciaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransferenciaServiceTest {

    @Autowired
    private TransferenciaService transferenciaService;
    @MockBean
    private TransferenciaRepository transferenciaRepository;
    @MockBean
    private MovimentacaoService movimentacaoService;
    @MockBean
    private ContaService contaService;

    @TestConfiguration
    static class TransferenciaServiceTestConfiguration {

        @Bean
        public TransferenciaService transferenciaService() {
            return new TransferenciaService();
        }
    }

    @Test
    public void deveSalvarUmaTransferencia() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(2L);
        transferenciaDto.setValor(1000.00);
        Mockito.when(contaService.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(contaService.findById(2L)).thenReturn(Optional.of(buildContaId2()));
        Mockito.when(contaService.getSaldoById(1L)).thenReturn(5000.00);

        transferenciaService.criar(transferenciaDto);
    }

    @Test
    public void deveSalvarUmaTransferenciaFutura() {
        TransferenciaFuturaDto transferenciaFuturaDto = new TransferenciaFuturaDto();
        transferenciaFuturaDto.setIdContaOrigem(1L);
        transferenciaFuturaDto.setIdContaDestino(2L);
        transferenciaFuturaDto.setValor(1000.00);
        transferenciaFuturaDto.setQuantidadeParcelas(2);
        transferenciaFuturaDto.setDataTransferencia(LocalDate.now().plusDays(1));

        Conta contaOrigem = buildContaId1();
        Conta contaDestino = buildContaId2();

        Mockito.when(contaService.findById(1L)).thenReturn(Optional.of(contaOrigem));
        Mockito.when(contaService.findById(2L)).thenReturn(Optional.of(contaDestino));

        BigDecimal valorTotal = BigDecimal.valueOf(transferenciaFuturaDto.getValor());
        BigDecimal valorParcelado = valorTotal.divide(BigDecimal.valueOf(transferenciaFuturaDto.getQuantidadeParcelas()), 2, RoundingMode.DOWN);

        if (valorParcelado.doubleValue() == 0.0)
            throw new ValorParceladoIgualZeroException();

        BigDecimal totalParcelado = valorParcelado.multiply(BigDecimal.valueOf(transferenciaFuturaDto.getQuantidadeParcelas()));

        boolean deveAlterarValorPrimeiraParcela = valorTotal.compareTo(totalParcelado) == 1;

        List<Transferencia> listaTransferencias = new ArrayList<>();

        OngoingStubbing<Transferencia> stubbing = Mockito.when(transferenciaRepository.save(Mockito.any()));

        for(int parcela = 0; parcela < transferenciaFuturaDto.getQuantidadeParcelas(); parcela++) {
            BigDecimal valor = valorParcelado;

            if (parcela == 0 && deveAlterarValorPrimeiraParcela)
                valor = valor.add(valorTotal.subtract(totalParcelado));

            LocalDate dataTransferencia = transferenciaFuturaDto.getDataTransferencia().plusMonths(parcela);
            Transferencia transferencia = new Transferencia();
            transferencia.setDataTransferencia(LocalDateTime.of(dataTransferencia, LocalTime.now()));
            transferencia.setExecutada(false);
            transferencia.setEstorno(false);
            transferencia.setValor(valor.doubleValue());
            transferencia.setContaOrigem(contaOrigem);
            transferencia.setContaDestino(contaDestino);
            listaTransferencias.add(transferencia);
            stubbing = stubbing.thenReturn(transferencia);
        }

        List<TransferenciaFuturaRetornoDto> listaSalva = transferenciaService.criarFutura(transferenciaFuturaDto);
        List<TransferenciaFuturaRetornoDto> listaTeste = listaTransferencias.stream().map(TransferenciaFuturaRetornoDto::valueOf)
                .collect(Collectors.toList());

        verify(transferenciaRepository, times(transferenciaFuturaDto.getQuantidadeParcelas())).save(Mockito.any(Transferencia.class));
        assertEquals(listaTeste, listaSalva);

    }

    @Test
    public void deveSalvarUmEstorno() {

        Mockito.when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(buildTransferenciaId1()));
        Mockito.when(contaService.getSaldoById(2L)).thenReturn(5000.00);

        transferenciaService.estornar(1L);
    }

    @Test(expected = SaldoInsuficienteException.class)
    public void deveRetornarExcecaoSaldoInsuficiente() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(2L);
        transferenciaDto.setValor(1000.00);
        Mockito.when(contaService.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(contaService.findById(2L)).thenReturn(Optional.of(buildContaId2()));
        Mockito.when(contaService.getSaldoById(1L)).thenReturn(500.00);

        transferenciaService.criar(transferenciaDto);
    }

    @Test(expected = ContaOrigemNaoEncontradaException.class)
    public void deveRetornarExcecaoContaOrigemNaoEncontrada() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(2L);
        transferenciaDto.setValor(1000.00);
        Mockito.when(contaService.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(contaService.findById(2L)).thenReturn(Optional.of(buildContaId2()));
        Mockito.when(contaService.getSaldoById(1L)).thenReturn(500.00);

        transferenciaService.criar(transferenciaDto);
    }

    @Test(expected = ContaDestinoNaoEncontradaException.class)
    public void deveRetornarExcecaoContaDestinoNaoEncontrada() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(2L);
        transferenciaDto.setValor(1000.00);
        Mockito.when(contaService.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(contaService.findById(2L)).thenReturn(Optional.empty());
        Mockito.when(contaService.getSaldoById(1L)).thenReturn(500.00);

        transferenciaService.criar(transferenciaDto);
    }

    @Test(expected = ContaOrigemIgualDestinoException.class)
    public void deveRetornarExcecaoContaOrigemIgualDestino() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(1L);
        transferenciaDto.setValor(1000.00);
        Mockito.when(contaService.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(contaService.getSaldoById(1L)).thenReturn(500.00);

        transferenciaService.criar(transferenciaDto);
    }

    @Test(expected = TransferenciaNaoEncontradaException.class)
    public void estornoDeveRetornarExcecaoTransferenciaNaoEncontrada() {
        Mockito.when(transferenciaRepository.findById(1L)).thenReturn(Optional.empty());

        transferenciaService.estornar(1L);
    }

    @Test(expected = SaldoInsuficienteException.class)
    public void estornoDeveRetornarExcecaoSaldoInsuficiente() {
        Mockito.when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(buildTransferenciaId1()));
        Mockito.when(contaService.getSaldoById(2L)).thenReturn(500.00);

        transferenciaService.estornar(1L);
    }

    @Test(expected = TransferenciaNaoExecutadaException.class)
    public void estornoDeveRetornarExcecaoTransferenciaNaoExecutada() {
        Transferencia transferencia = buildTransferenciaId1();
        transferencia.setExecutada(false);
        Mockito.when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(transferencia));
        Mockito.when(contaService.getSaldoById(2L)).thenReturn(500.00);

        transferenciaService.estornar(1L);
    }

    @Test(expected = TransferenciaJaEstornadaException.class)
    public void estornoDeveRetornarExcecaoTransferenciaJaEstornada() {
        Mockito.when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(buildTransferenciaId1()));
        Mockito.when(transferenciaRepository.findByTransferenciaEstornadaId(1L)).thenReturn(Optional.of(buildTransferenciaEstornoId2()));
        Mockito.when(contaService.getSaldoById(2L)).thenReturn(500.00);

        transferenciaService.estornar(1L);
    }

    @Test(expected = ContaOrigemIgualDestinoException.class)
    public void transferenciaFuturaDeveRetornarExcecaoContaOrigemIgualDestino() {
        TransferenciaFuturaDto transferenciaFuturaDto = new TransferenciaFuturaDto();
        transferenciaFuturaDto.setIdContaOrigem(1L);
        transferenciaFuturaDto.setIdContaDestino(1L);
        transferenciaFuturaDto.setValor(1000.00);
        transferenciaFuturaDto.setQuantidadeParcelas(2);
        transferenciaFuturaDto.setDataTransferencia(LocalDate.now().plusDays(1));

        Conta conta = buildContaId1();

        Mockito.when(contaService.findById(1L)).thenReturn(Optional.of(conta));

        transferenciaService.criarFutura(transferenciaFuturaDto);
    }

    @Test(expected = ValorParceladoIgualZeroException.class)
    public void transferenciaFuturaDeveRetornarExcecaoValorParceladoIgualZero() {
        TransferenciaFuturaDto transferenciaFuturaDto = new TransferenciaFuturaDto();
        transferenciaFuturaDto.setIdContaOrigem(1L);
        transferenciaFuturaDto.setIdContaDestino(2L);
        transferenciaFuturaDto.setValor(0.01);
        transferenciaFuturaDto.setQuantidadeParcelas(2);
        transferenciaFuturaDto.setDataTransferencia(LocalDate.now().plusDays(1));

        Conta contaOrigem = buildContaId1();
        Conta contaDestino = buildContaId2();

        Mockito.when(contaService.findById(1L)).thenReturn(Optional.of(contaOrigem));
        Mockito.when(contaService.findById(2L)).thenReturn(Optional.of(contaDestino));

        transferenciaService.criarFutura(transferenciaFuturaDto);
    }

    public Conta buildContaId1() {
        Banco banco = new Banco();
        banco.setId(1L);
        banco.setCodigo("077");
        banco.setDescricao("BANCO INTER");
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setBanco(banco);
        conta.setAgencia("0001");
        conta.setNumero("123456");
        conta.setDigito("1");

        return conta;
    }

    public Conta buildContaId2() {
        Banco banco = new Banco();
        banco.setId(3L);
        banco.setCodigo("237");
        banco.setDescricao("BRADESCO");
        Conta conta = new Conta();
        conta.setId(2L);
        conta.setBanco(banco);
        conta.setAgencia("1234");
        conta.setNumero("1234567");
        conta.setDigito("2");

        return conta;
    }

    public Transferencia buildTransferenciaId1() {
        Conta contaOrigem = buildContaId1();
        Conta contaDestino = buildContaId2();
        Transferencia transferencia = new Transferencia();
        transferencia.setId(1L);
        transferencia.setEstorno(false);
        transferencia.setValor(1000.00);
        transferencia.setDataTransferencia(LocalDateTime.of(2021, 12,16, 19, 0));
        transferencia.setContaOrigem(contaOrigem);
        transferencia.setContaDestino(contaDestino);
        transferencia.setExecutada(true);

        return transferencia;
    }

    public Transferencia buildTransferenciaEstornoId2() {
        Conta contaOrigem = buildContaId2();
        Conta contaDestino = buildContaId1();
        Transferencia transferencia = new Transferencia();
        transferencia.setId(2L);
        transferencia.setEstorno(true);
        transferencia.setValor(1000.00);
        transferencia.setDataTransferencia(LocalDateTime.of(2021, 12,17, 10, 0));
        transferencia.setTransferenciaEstornada(buildTransferenciaId1());
        transferencia.setContaOrigem(contaOrigem);
        transferencia.setContaDestino(contaDestino);
        transferencia.setExecutada(true);

        return transferencia;
    }

}
