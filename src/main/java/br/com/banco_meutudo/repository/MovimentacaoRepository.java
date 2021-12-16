package br.com.banco_meutudo.repository;

import br.com.banco_meutudo.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("SELECT SUM(m.valor) FROM Movimentacao m WHERE m.conta.id = :idConta")
    double getSomatorioByConta(@Param("idConta") long idConta);

}
