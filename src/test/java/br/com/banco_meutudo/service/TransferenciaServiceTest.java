package br.com.banco_meutudo.service;

import br.com.banco_meutudo.repository.TransferenciaRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransferenciaServiceTest {

    @Autowired
    @InjectMocks
    TransferenciaService transferenciaService;
    @MockBean
    TransferenciaRepository transferenciaRepository;
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

}
