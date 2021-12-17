package br.com.banco_meutudo.service;

import br.com.banco_meutudo.enums.TipoMovimentacaoEnum;
import br.com.banco_meutudo.enums.TipoTransacaoEnum;
import br.com.banco_meutudo.model.Conta;
import br.com.banco_meutudo.model.Movimentacao;
import br.com.banco_meutudo.model.Transferencia;
import br.com.banco_meutudo.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsável pelas operações com Movimentação.
 */
@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    /**
     * Método responsável por somar todas as movimentações de uma Conta usando o id.
     *
     * @param idConta Id que será consultado
     * @return Somatorio de todas as movimentações da conta.
     */
    public double getSomatorioByConta(long idConta) {
        return movimentacaoRepository.getSomatorioByConta(idConta);
    }

    /**
     * Método responsável por salvar uma nova movimentação
     * @param movimentacao Movimentação que será salva na base.
     */
    public void criar(Movimentacao movimentacao) {
        movimentacaoRepository.save(movimentacao);
    }

    /**
     * Método responsável por criar uma nova movimentação de despesa e salvar na base.
     * @param valor Valor que será movimentado.
     * @param conta Conta onde será feita a movimentação.
     * @param tipoTransacao Tipo de transação que será feita.
     * @param transferencia Transferência que originou movimentação ou nulo caso seja outro tipo de transação.
     */
    public void criarDespesa(double valor, Conta conta, TipoTransacaoEnum tipoTransacao, Transferencia transferencia) {
        criar(-valor, conta, TipoMovimentacaoEnum.DESPESA, tipoTransacao, transferencia);
    }

    /**
     * Método responsável por criar uma nova movimentação de receita e salvar na base.
     * @param valor Valor que será movimentado.
     * @param conta Conta onde será feita a movimentação.
     * @param tipoTransacao Tipo de transação que será feita.
     * @param transferencia Transferência que originou movimentação ou nulo caso seja outro tipo de transação.
     */
    public void criarReceita(double valor, Conta conta, TipoTransacaoEnum tipoTransacao, Transferencia transferencia) {
        criar(valor, conta, TipoMovimentacaoEnum.RECEITA, tipoTransacao, transferencia);
    }

    /**
     * Método responsável por criar uma nova movimentação e salvar na base.
     * @param valor Valor que será movimentado.
     * @param conta Conta onde será feita a movimentação.
     * @param tipo Tipo da movimentação.
     * @param tipoTransacao Tipo de transação que será feita.
     * @param transferencia Transferência que originou movimentação ou nulo caso seja outro tipo de transação.
     */
    private void criar(double valor, Conta conta, TipoMovimentacaoEnum tipo, TipoTransacaoEnum tipoTransacao, Transferencia transferencia) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setValor(valor);
        movimentacao.setConta(conta);
        movimentacao.setTipo(tipo.getCodigo());
        movimentacao.setTipoTransacao(tipoTransacao.getCodigo());
        movimentacao.setTransferencia(transferencia);
        criar(movimentacao);
    }

}
