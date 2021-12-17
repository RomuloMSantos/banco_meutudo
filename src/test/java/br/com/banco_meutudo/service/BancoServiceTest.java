package br.com.banco_meutudo.service;

import br.com.banco_meutudo.exception.BancoNaoEncontradoException;
import br.com.banco_meutudo.model.Banco;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
public class BancoServiceTest {

    @TestConfiguration
    static class BancoServiceTestConfiguration {

        @Bean
        public BancoService bancoService() {
            return new BancoService();
        }
    }

    @Autowired
    private BancoService bancoService;

    @Test(expected = BancoNaoEncontradoException.class)
    public void deveRetornarExcecaoBancoNaoEncontrado() {
        bancoService.getById(0);
    }

    @Test
    public void deveRetornarBancoCorreto() {
        Banco banco =  bancoService.getById(1L);
        Banco bancoInter = buildBancoInter();
        assertEquals(bancoInter, banco);
    }

    @Test
    public void deveRetornarBancoDiferente() {
        Banco banco =  bancoService.getById(2L);
        Banco bancoInter = buildBancoInter();
        assertNotEquals(bancoInter, banco);
    }

    private Banco buildBancoInter() {
        Banco banco = new Banco();
        banco.setId(1L);
        banco.setCodigo("077");
        banco.setDescricao("BANCO INTER");
        return banco;
    }

}
