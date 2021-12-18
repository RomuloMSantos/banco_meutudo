package br.com.banco_meutudo.controller;

import br.com.banco_meutudo.BancoMeutudoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BancoMeutudoApplication.class)
@AutoConfigureMockMvc
public class ContaControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void deveRetornarSaldo() {
        try {
            mockMvc.perform(get("/api/v1/contas/1/saldo")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deveRetornarTransferencias() {
        try {
            mockMvc.perform(get("/api/v1/contas/1/transferencias")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deveRetornarTransferenciasFuturas() {
        try {
            mockMvc.perform(get("/api/v1/contas/1/transferencias/futuras")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deveRetornarConta() {

        try {
            mockMvc.perform(get("/api/v1/contas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("idBanco", "1")
                            .param("agencia", "0001")
                            .param("numero", "123456")
                            .param("digito", "1"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
