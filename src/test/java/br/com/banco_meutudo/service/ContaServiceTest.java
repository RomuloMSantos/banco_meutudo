package br.com.banco_meutudo.service;

import br.com.banco_meutudo.dto.ContaDto;
import br.com.banco_meutudo.dto.ContaRetornoDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaRetornoDto;
import br.com.banco_meutudo.dto.TransferenciaRetornoDto;
import br.com.banco_meutudo.exception.ContaNaoEncontradaException;
import br.com.banco_meutudo.model.Banco;
import br.com.banco_meutudo.model.Conta;
import br.com.banco_meutudo.repository.ContaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ContaServiceTest {

    @Autowired
    private ContaService contaService;
    @MockBean
    private ContaRepository contaRepository;
    @MockBean
    private MovimentacaoService movimentacaoService;
    @MockBean
    private BancoService bancoService;
    @MockBean
    private TransferenciaService transferenciaService;

    @TestConfiguration
    static class ContaServiceTestConfiguration {

        @Bean
        public ContaService contaService() {
            return new ContaService();
        }
    }

    @Test
    public void deveRetornarConta() {
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Optional<Conta> contaOptional = contaService.findById(1L);
        assertEquals(true, contaOptional.isPresent());
    }

    @Test
    public void naoDeveRetornarConta() {
        Optional<Conta> contaOptional = contaService.findById(10);
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(false, contaOptional.isPresent());
    }

    @Test
    public void deveRetornarSaldoCorreto() {
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(movimentacaoService.getSomatorioByConta(1L)).thenReturn(5000.00);
        double saldo = contaService.getSaldoById(1L);
        assertEquals(5000.00, saldo);
    }

    @Test
    public void deveRetornarSaldoDiferente() {
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(movimentacaoService.getSomatorioByConta(1)).thenReturn(10000.00);
        double saldo = contaService.getSaldoById(1);
        assertNotEquals(5000.00, saldo);
    }

    @Test(expected = ContaNaoEncontradaException.class)
    public void deveRetornarExcecaoContaNaoEncontrada() {
        contaService.getSaldoById(20);
    }

    @Test
    public void deveRetornarContaRetornoDtoNulo() {
        ContaDto contaDto = new ContaDto();
        contaDto.setIdBanco(3L);
        contaDto.setAgencia("123456");
        contaDto.setNumero("1234567");
        contaDto.setDigito("2");
        ContaRetornoDto contaRetornoDto = contaService.getBy(contaDto);
        Banco banco = new Banco();
        banco.setId(3L);
        Mockito.when(contaRepository.findByAgenciaAndNumeroAndDigitoAndBanco(contaDto.getAgencia(), contaDto.getNumero(), contaDto.getDigito(), banco)).thenReturn(Optional.empty());
        assertEquals(null, contaRetornoDto);
    }

    @Test
    public void deveRetornarContaRetornoDtoDiferente() {
        ContaDto contaDto = new ContaDto();
        contaDto.setIdBanco(3L);
        contaDto.setAgencia("1234");
        contaDto.setNumero("1234567");
        contaDto.setDigito("2");
        Banco banco = new Banco();
        banco.setId(3L);
        ContaRetornoDto contaRetornoDto = contaService.getBy(contaDto);
        ContaRetornoDto contaRetornoTeste = buildContaRetornoId1();
        Mockito.when(contaRepository.findByAgenciaAndNumeroAndDigitoAndBanco(contaDto.getAgencia(), contaDto.getNumero(), contaDto.getDigito(), banco)).thenReturn(Optional.of(buildContaId1()));

        assertNotEquals(contaRetornoTeste, contaRetornoDto);
    }

    @Test
    public void deveRetornarContaRetornoDtoCorreta() {
        ContaDto contaDto = new ContaDto();
        contaDto.setIdBanco(1L);
        contaDto.setAgencia("0001");
        contaDto.setNumero("123456");
        contaDto.setDigito("1");
        Banco banco = new Banco();
        banco.setId(1L);

        Mockito.when(bancoService.getById(1L)).thenReturn(banco);
        Mockito.when(contaRepository.findByAgenciaAndNumeroAndDigitoAndBanco(contaDto.getAgencia(), contaDto.getNumero(), contaDto.getDigito(), banco)).thenReturn(Optional.of(buildContaId1()));
        ContaRetornoDto contaRetornoDto = contaService.getBy(contaDto);
        ContaRetornoDto contaRetornoTeste = buildContaRetornoId1();

        assertEquals(contaRetornoTeste, contaRetornoDto);
    }

    @Test
    public void deveRetornarListaTransferenciaCorreta() {
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));

        List<TransferenciaRetornoDto> listaTransferencias = buildListaTransferenciaConta1();
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(transferenciaService.getByConta(1L)).thenReturn(listaTransferencias);
        List<TransferenciaRetornoDto> listaTransferenciasRetorno = contaService.getTransferenciasById(1L);

        assertEquals(listaTransferencias, listaTransferenciasRetorno);
    }

    @Test
    public void deveRetornarListaTransferenciaVazia() {
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));

        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(transferenciaService.getByConta(1L)).thenReturn(new ArrayList<TransferenciaRetornoDto>());
        List<TransferenciaRetornoDto> listaTransferenciasRetorno = contaService.getTransferenciasById(1L);

        assertEquals(new ArrayList<TransferenciaRetornoDto>(), listaTransferenciasRetorno);
    }

    @Test
    public void deveRetornarListaTransferenciaFuturaCorreta() {
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));

        List<TransferenciaFuturaRetornoDto> listaTransferencias = buildListaTransferenciaFuturaConta1();
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(transferenciaService.getFuturasByConta(1L)).thenReturn(listaTransferencias);
        List<TransferenciaFuturaRetornoDto> listaTransferenciasRetorno = contaService.getTransferenciasFuturasById(1L);

        assertEquals(listaTransferencias, listaTransferenciasRetorno);
    }

    @Test
    public void deveRetornarListaTransferenciaFuturaVazia() {
        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.of(buildContaId1()));
        Mockito.when(transferenciaService.getFuturasByConta(1L)).thenReturn(new ArrayList<TransferenciaFuturaRetornoDto>());
        List<TransferenciaFuturaRetornoDto> listaTransferenciasRetorno = contaService.getTransferenciasFuturasById(1L);

        assertEquals(new ArrayList<TransferenciaFuturaRetornoDto>(), listaTransferenciasRetorno);
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

    public ContaRetornoDto buildContaRetornoId1() {
        return new ContaRetornoDto(1, "BANCO INTER", "0001", "123456", "1");
    }

    public List<TransferenciaRetornoDto> buildListaTransferenciaConta1() {
        ContaRetornoDto contaRetornoDto = buildContaRetornoId1();
        List<TransferenciaRetornoDto> retorno = new ArrayList<>();
        retorno.add(new TransferenciaRetornoDto(1L, LocalDateTime.now(),
                100.00, contaRetornoDto));
        retorno.add(new TransferenciaRetornoDto(2L, LocalDateTime.now(),
                200.00, contaRetornoDto));
        retorno.add(new TransferenciaRetornoDto(3L, LocalDateTime.now(),
                300.00, contaRetornoDto));
        return retorno;
    }

    public List<TransferenciaFuturaRetornoDto> buildListaTransferenciaFuturaConta1() {
        ContaRetornoDto contaRetornoDto = buildContaRetornoId1();
        List<TransferenciaFuturaRetornoDto> retorno = new ArrayList<>();
        retorno.add(new TransferenciaFuturaRetornoDto(1L, LocalDateTime.now(),
                LocalDateTime.of(2021, 12,16,17,0), 100.00, contaRetornoDto));
        retorno.add(new TransferenciaFuturaRetornoDto(2L, LocalDateTime.now(),
                LocalDateTime.of(2021, 12,16,17,0), 200.00, contaRetornoDto));
        retorno.add(new TransferenciaFuturaRetornoDto(3L, LocalDateTime.now(),
                LocalDateTime.of(2021, 12,16,17,0), 300.00, contaRetornoDto));
        return retorno;
    }

}
