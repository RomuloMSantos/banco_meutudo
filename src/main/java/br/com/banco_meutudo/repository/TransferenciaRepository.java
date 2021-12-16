package br.com.banco_meutudo.repository;

import br.com.banco_meutudo.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByContaOrigemIdAndDataTransferenciaAfter(long id, LocalDateTime dataTransferencia);
    List<Transferencia> findByContaOrigemIdAndExecutada(long id, boolean executada);
    Optional<Transferencia> findByTransferenciaEstornadaId(long id);
}
