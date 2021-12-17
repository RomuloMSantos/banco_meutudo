package br.com.banco_meutudo.service;

import br.com.banco_meutudo.enums.TipoTransacaoEnum;
import br.com.banco_meutudo.exception.TransferenciaNaoInformadaException;
import br.com.banco_meutudo.model.Conta;
import br.com.banco_meutudo.model.Transferencia;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MovimentacaoServiceTest {

    @TestConfiguration
    static class MovimentacaoServiceTestConfiguration {

        @Bean
        public MovimentacaoService movimentacaoService() {
            return new MovimentacaoService();
        }
    }

    @Autowired
    private MovimentacaoService movimentacaoService;

    Conta conta;
    Transferencia transferencia;

    @Before
    public void setUp() {
        conta = new Conta();
        conta.setId(1L);
        transferencia = new Transferencia();
        transferencia.setId(1L);
    }

    @Test
    public void deveCriarDespesaSaqueSemErro() {
        movimentacaoService.criarDespesa(10.0, conta, TipoTransacaoEnum.SAQUE);
    }

    @Test
    public void deveCriarDespesaInvestimentoSemErro() {
        movimentacaoService.criarDespesa(10.0, conta, TipoTransacaoEnum.INVESTIMENTO);
    }

    @Test
    public void deveCriarReceitaDepositoSemErro() {
        movimentacaoService.criarReceita(10.0, conta, TipoTransacaoEnum.DEPOSITO);
    }

    @Test
    public void deveCriarReceitaSalarioSemErro() {
        movimentacaoService.criarReceita(10.0, conta, TipoTransacaoEnum.SALARIO);
    }

    @Test(expected = TransferenciaNaoInformadaException.class)
    public void deveRetornarExcecaoTransferenciaNaoInformadaPoisEDespesaTipoTransferencia() {
        movimentacaoService.criarDespesa(10.0, conta, TipoTransacaoEnum.TRANSFERENCIA, null);
    }

    @Test(expected = TransferenciaNaoInformadaException.class)
    public void deveRetornarExcecaoTransferenciaNaoInformadaPoisEDespesaTipoEstorno() {
        movimentacaoService.criarDespesa(10.0, conta, TipoTransacaoEnum.ESTORNO, null);
    }

    @Test
    public void deveCriarDespesaTransferenciaSemErro() {
        movimentacaoService.criarDespesa(10.0, conta, TipoTransacaoEnum.TRANSFERENCIA, transferencia);
    }

    @Test
    public void deveCriarDespesaEstornoSemErro() {
        movimentacaoService.criarDespesa(10.0, conta, TipoTransacaoEnum.ESTORNO, transferencia);
    }

    @Test(expected = TransferenciaNaoInformadaException.class)
    public void deveRetornarExcecaoTransferenciaNaoInformadaPoisEReceitaTipoTransferencia() {
        movimentacaoService.criarReceita(10.0, conta, TipoTransacaoEnum.TRANSFERENCIA, null);
    }

    @Test(expected = TransferenciaNaoInformadaException.class)
    public void deveRetornarExcecaoTransferenciaNaoInformadaPoisEReceitaTipoEstorno() {
        movimentacaoService.criarReceita(10.0, conta, TipoTransacaoEnum.ESTORNO, null);
    }

    @Test
    public void deveCriarReceitaTransferenciaSemErro() {
        movimentacaoService.criarReceita(10.0, conta, TipoTransacaoEnum.TRANSFERENCIA, transferencia);
    }

    @Test
    public void deveCriarReceitaEstornoSemErro() {
        movimentacaoService.criarReceita(10.0, conta, TipoTransacaoEnum.ESTORNO, transferencia);
    }

}
