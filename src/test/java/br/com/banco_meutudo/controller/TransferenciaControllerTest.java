package br.com.banco_meutudo.controller;

import br.com.banco_meutudo.BancoMeutudoApplication;
import br.com.banco_meutudo.dto.TransferenciaDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaDto;
import br.com.banco_meutudo.model.Banco;
import br.com.banco_meutudo.model.Conta;
import br.com.banco_meutudo.model.Transferencia;
import br.com.banco_meutudo.repository.TransferenciaRepository;
import br.com.banco_meutudo.service.TransferenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BancoMeutudoApplication.class)
@AutoConfigureMockMvc
public class TransferenciaControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransferenciaService transferenciaService;

    @MockBean
    private TransferenciaRepository transferenciaRepository;

    @Test
    public void deveSalvarTransferencia() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(2L);
        transferenciaDto.setValor(1000);
        Mockito.when(transferenciaRepository.save(Mockito.any())).thenReturn(buildTransferenciaId1());


        try {
            mockMvc.perform(post("/api/v1/transferencias")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(transferenciaDto)))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deveSalvarTransferenciaFutura() {
        TransferenciaFuturaDto transferenciaFuturaDto = new TransferenciaFuturaDto();
        transferenciaFuturaDto.setIdContaOrigem(1L);
        transferenciaFuturaDto.setIdContaDestino(2L);
        transferenciaFuturaDto.setValor(1000);
        transferenciaFuturaDto.setQuantidadeParcelas(2);
        transferenciaFuturaDto.setDataTransferencia(LocalDate.now().plusDays(1));
        Transferencia retorno = new Transferencia();
        Conta conta = new Conta();
        conta.setId(2L);
        conta.setBanco(new Banco());
        retorno.setContaDestino(conta);
        Mockito.when(transferenciaRepository.save(Mockito.any())).thenReturn(retorno, retorno);

        try {
            mockMvc.perform(post("/api/v1/transferencias/futura")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(transferenciaFuturaDto)))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deveSalvarEstornoTransferencia() {
        try {

            Mockito.when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(buildTransferenciaId1()));
            Mockito.when(transferenciaRepository.save(Mockito.any())).thenReturn(buildTransferenciaEstornoId2());

            mockMvc.perform(post("/api/v1/transferencias/1/estornar")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferenciaDeveRetornarErro() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(10L);
        transferenciaDto.setValor(1000);

        try {
            mockMvc.perform(post("/api/v1/transferencias")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(transferenciaDto)))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferenciaFuturaDeveRetornarErro() throws Exception {
        TransferenciaFuturaDto transferenciaFuturaDto = new TransferenciaFuturaDto();
        transferenciaFuturaDto.setIdContaOrigem(1L);
        transferenciaFuturaDto.setIdContaDestino(2L);
        transferenciaFuturaDto.setValor(0.01);
        transferenciaFuturaDto.setQuantidadeParcelas(2);
        transferenciaFuturaDto.setDataTransferencia(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/api/v1/transferencias/futura")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferenciaFuturaDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void estornoDeveRetornarErro() {
        try {

            Mockito.when(transferenciaRepository.findById(1L)).thenReturn(Optional.empty());

            mockMvc.perform(post("/api/v1/transferencias/1/estornar")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferenciaDeveRetornarErroParametros() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIdContaOrigem(0L);
        transferenciaDto.setIdContaDestino(0L);
        transferenciaDto.setValor(0);

        try {
            MvcResult result = mockMvc.perform(post("/api/v1/transferencias")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(transferenciaDto)))
                    .andExpect(status().is4xxClientError())
                    .andReturn();
            assertEquals(result.getResponse().getContentAsString().split("\",\"").length, 3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferenciaFuturaDeveRetornarErroParametros() throws Exception {
        TransferenciaFuturaDto transferenciaFuturaDto = new TransferenciaFuturaDto();
        transferenciaFuturaDto.setIdContaOrigem(0L);
        transferenciaFuturaDto.setIdContaDestino(0L);
        transferenciaFuturaDto.setValor(0.00);
        transferenciaFuturaDto.setQuantidadeParcelas(0);
        transferenciaFuturaDto.setDataTransferencia(LocalDate.now());

        MvcResult result = mockMvc.perform(post("/api/v1/transferencias/futura")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferenciaFuturaDto)))
                        .andExpect(status().is4xxClientError())
                        .andReturn();

        assertEquals(result.getResponse().getContentAsString().split("\",\"").length, 5);
    }

    @Test
    public void estornoDeveRetornarErroParametros() {
        try {

            MvcResult result = mockMvc.perform(post("/api/v1/transferencias/0/estornar")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is5xxServerError())
                    .andReturn();
            assertEquals(result.getResponse().getContentAsString().split("\",\"").length, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        transferencia.setDataTransferencia(LocalDateTime.now());
        transferencia.setTransferenciaEstornada(buildTransferenciaId1());
        transferencia.setContaOrigem(contaOrigem);
        transferencia.setContaDestino(contaDestino);
        transferencia.setExecutada(true);

        return transferencia;
    }

}
