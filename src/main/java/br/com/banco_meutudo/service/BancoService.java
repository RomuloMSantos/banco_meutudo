package br.com.banco_meutudo.service;

import br.com.banco_meutudo.exception.BancoNaoEncontradoException;
import br.com.banco_meutudo.model.Banco;
import br.com.banco_meutudo.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsável pelas operações com Banco.
 */
@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    /**
     * Método responsável por consultar um Banco usando o id.
     *
     * @param id Id que será consultado
     * @return Banco encontrado caso exista. Será criada uma exceção caso não encontre.
     */
    public Banco getById(long id) {
        Optional<Banco> bancoOptional = bancoRepository.findById(id);

        if (bancoOptional.isEmpty())
            throw new BancoNaoEncontradoException(id);

        return bancoOptional.get();
    }

}
