package br.com.banco_meutudo.service;

import br.com.banco_meutudo.dto.ContaDto;
import br.com.banco_meutudo.dto.ContaRetornoDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaRetornoDto;
import br.com.banco_meutudo.dto.TransferenciaRetornoDto;
import br.com.banco_meutudo.exception.ContaNaoEncontradaException;
import br.com.banco_meutudo.model.Banco;
import br.com.banco_meutudo.model.Conta;
import br.com.banco_meutudo.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service responsável pelas operações com Conta.
 */
@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private MovimentacaoService movimentacaoService;
    @Autowired
    private BancoService bancoService;
    @Autowired
    private TransferenciaService transferenciaService;

    /**
     * Método responsável por consultar uma Conta usando o id.
     *
     * @param id Id que será consultado
     * @return Optional contendo Conta encontrada caso exista.
     */
    public Optional<Conta> findById(long id) {
        return contaRepository.findById(id);
    }

    /**
     * Método responsável por consultar o saldo de uma Conta usando o id.
     *
     * @param id Id que será consultado
     * @return Saldo da conta encontrada caso exista.
     */
    public Double getSaldoById(long id) {
        verificaContaExiste(id);

        return movimentacaoService.getSomatorioByConta(id);
    }

    /**
     * Método responsável por consultar uma Conta usando os dados Banco, Agência, Número e Digito.
     *
     * @param contaDto Objeto com os dados da conta que será usado na consulta.
     * @return Conta encontrada caso exista. Será criada uma exceção caso não encontre.
     */
    public ContaRetornoDto getBy(ContaDto contaDto) {
        Banco banco = bancoService.getById(contaDto.getIdBanco());
        Optional<Conta> contaOptional = contaRepository.findByAgenciaAndNumeroAndDigitoAndBanco(contaDto.getAgencia(), contaDto.getNumero(), contaDto.getDigito(), banco);

        if (contaOptional.isEmpty())
            return null;

        return ContaRetornoDto.valueOf(contaOptional.get());
    }

    /**
     * Método responsável por consultar as transferências futuras de uma Conta.
     *
     * @param id Id da Conta que será usada na consulta.
     * @return Lista contendo as transferências futuras da conta.
     */
    public List<TransferenciaFuturaRetornoDto> getTransferenciasFuturasById(long id) {
        verificaContaExiste(id);

        return transferenciaService.getFuturasByConta(id);
    }

    /**
     * Método responsável por consultar as transferências executadas de uma Conta.
     *
     * @param id Id da Conta que será usada na consulta.
     * @return Lista contendo as transferências executadas da conta.
     */
    public List<TransferenciaRetornoDto> getTransferenciasById(long id) {
        verificaContaExiste(id);

        return transferenciaService.getByConta(id);
    }

    private void verificaContaExiste(long id) {
        Optional<Conta> contaOptional = contaRepository.findById(id);

        if (contaOptional.isEmpty())
            throw new ContaNaoEncontradaException(id);
    }

}
