package br.com.banco_meutudo.repository;

import br.com.banco_meutudo.model.Banco;
import br.com.banco_meutudo.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByAgenciaAndNumeroAndDigitoAndBancoId(String agencia, String numero, String digito, long id);
}
