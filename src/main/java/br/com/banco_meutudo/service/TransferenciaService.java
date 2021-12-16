package br.com.banco_meutudo.service;

import br.com.banco_meutudo.dto.TransferenciaDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaRetornoDto;
import br.com.banco_meutudo.dto.TransferenciaRetornoDto;
import br.com.banco_meutudo.exception.BusinessException;
import br.com.banco_meutudo.exception.SaldoInsuficienteException;
import br.com.banco_meutudo.exception.TransferenciaNaoEncontradaException;
import br.com.banco_meutudo.model.Conta;
import br.com.banco_meutudo.model.Transferencia;
import br.com.banco_meutudo.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service responsável pelas operações com Transferencia.
 */
@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository transferenciaRepository;
    @Autowired
    private MovimentacaoService movimentacaoService;
    @Autowired
    private ContaService contaService;

    /**
     * Método responsável por criar uma transferência comum.
     * @param transferenciaDto Dados da transferência. Conta Origem, Conta Destino e Valor.
     */
    @Transactional
    public void criar(TransferenciaDto transferenciaDto) {
        validarTransferencia(transferenciaDto);

        Conta contaOrigem = contaService.getById(transferenciaDto.getIdContaOrigem());
        Conta contaDestino = contaService.getById(transferenciaDto.getIdContaDestino());

        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDateTime.now());
        transferencia.setExecutada(true);
        transferencia.setEstorno(false);
        transferencia.setValor(transferenciaDto.getValor());
        transferencia.setContaOrigem(contaOrigem);
        transferencia.setContaDestino(contaDestino);

        transferenciaRepository.save(transferencia);

        movimentacaoService.criarDespesa(transferencia.getValor(), transferencia.getContaOrigem());
        movimentacaoService.criarReceita(transferencia.getValor(), transferencia.getContaDestino());
    }

    /**
     * Método responsável por criar um estorno de uma transferência.
     * É criada uma nova transferência com a flag estorno = true. Os dados da transferência
     * estornada são setados na transferência de estorno, trocando Conta Origem por Conta Destino e vice-versa.
     * @param id Id da transferência que será estornada.
     */
    @Transactional
    public void estornar(long id) {
        Optional<Transferencia> transferenciaOptional = transferenciaRepository.findById(id);

        if (transferenciaOptional.isEmpty())
            throw new TransferenciaNaoEncontradaException("Transferência ID: " + id + " não encontrada!");

        Transferencia transferencia = transferenciaOptional.get();

        validarEstornoTransferencia(transferencia);

        Transferencia transferenciaEstorno = new Transferencia();
        transferenciaEstorno.setEstorno(true);
        transferenciaEstorno.setValor(transferencia.getValor());
        transferenciaEstorno.setDataTransferencia(LocalDateTime.now());
        transferenciaEstorno.setTransferenciaEstornada(transferencia);
        transferenciaEstorno.setContaOrigem(transferencia.getContaDestino());
        transferenciaEstorno.setContaDestino(transferencia.getContaOrigem());
        transferenciaEstorno.setExecutada(true);

        transferenciaRepository.save(transferenciaEstorno);

        movimentacaoService.criarDespesa(transferenciaEstorno.getValor(), transferenciaEstorno.getContaOrigem());
        movimentacaoService.criarReceita(transferenciaEstorno.getValor(), transferenciaEstorno.getContaDestino());
    }

    /**
     * Método responsável por criar transferência(s) futura(s). Dividindo o valor em parcelas.
     * @param transferenciaFuturaDto Dados de uma transferência comum, quantidade de parcelas e data da transferência.
     */
    @Transactional
    public void criarFutura(TransferenciaFuturaDto transferenciaFuturaDto) {
        Conta contaOrigem = contaService.getById(transferenciaFuturaDto.getIdContaOrigem());
        Conta contaDestino = contaService.getById(transferenciaFuturaDto.getIdContaDestino());

        BigDecimal valorTotal = BigDecimal.valueOf(transferenciaFuturaDto.getValor());
        BigDecimal valorParcelado = valorTotal.divide(BigDecimal.valueOf(transferenciaFuturaDto.getQuantidadeParcelas()), 2, RoundingMode.DOWN);
        BigDecimal totalParcelado = valorParcelado.multiply(BigDecimal.valueOf(transferenciaFuturaDto.getQuantidadeParcelas()));

        boolean deveAlterarValorPrimeiraParcela = valorTotal.compareTo(totalParcelado) == 1;

        for(int parcela = 0; parcela < transferenciaFuturaDto.getQuantidadeParcelas(); parcela++) {
            BigDecimal valor = valorParcelado;

            if (deveAlterarValorPrimeiraParcela && parcela == 0)
                valor = valor.add(valorTotal.subtract(totalParcelado));

            LocalDate dataTransferencia = transferenciaFuturaDto.getDataTransferencia().plusMonths(parcela);
            Transferencia transferencia = new Transferencia();
            transferencia.setDataTransferencia(LocalDateTime.of(dataTransferencia, LocalTime.now()));
            transferencia.setExecutada(false);
            transferencia.setEstorno(false);
            transferencia.setValor(valor.doubleValue());
            transferencia.setContaOrigem(contaOrigem);
            transferencia.setContaDestino(contaDestino);
            transferenciaRepository.save(transferencia);
        }
    }

    /**
     * Método responsável por buscar as transferências futuras de uma conta.
     * @param idConta Id da conta que será usada na consulta.
     * @return Lista com todas as transferências futuras da conta.
     */
    public List<TransferenciaFuturaRetornoDto> getFuturasByConta(long idConta) {
        List<Transferencia> listaTransferencia = transferenciaRepository.findByContaOrigemIdAndDataTransferenciaAfter(idConta, LocalDateTime.now());
        return listaTransferencia.stream().map(TransferenciaFuturaRetornoDto::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por buscar as transferências de uma conta.
     * @param idConta Id da conta que será usada na consulta.
     * @return Lista com todas as transferências da conta.
     */
    public List<TransferenciaRetornoDto> getByConta(long idConta) {
        List<Transferencia> listaTransferencia = transferenciaRepository.findByContaOrigemIdAndExecutada(idConta, true);
        return listaTransferencia.stream().map(TransferenciaRetornoDto::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por validar uma transferência.
     * @param transferenciaDto Dados da transferência que será validada.
     */
    private void validarTransferencia(TransferenciaDto transferenciaDto) {
        if (transferenciaDto.getValor() > contaService.getSaldoById(transferenciaDto.getIdContaOrigem()))
            throw new SaldoInsuficienteException("Saldo insuficiente!");
    }

    /**
     * Método responsável por validar se é valido estornar uma transferência.
     * @param transferenciaEstornada Transferência que será estornada e deve ser validada.
     */
    private void validarEstornoTransferencia(Transferencia transferenciaEstornada) {

        if (!transferenciaEstornada.isExecutada())
            throw new BusinessException("Transferência ainda não foi executada!");

        Optional<Transferencia> transferenciaOptional = transferenciaRepository.findByTransferenciaEstornadaId(transferenciaEstornada.getId());

        if (transferenciaOptional.isPresent())
            throw new BusinessException("Transferência já estornada!");

        validarTransferencia(new TransferenciaDto(transferenciaEstornada.getValor(), transferenciaEstornada.getContaDestino().getId(), transferenciaEstornada.getContaOrigem().getId() ));
    }

}
