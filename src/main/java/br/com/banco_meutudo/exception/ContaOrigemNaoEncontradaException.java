package br.com.banco_meutudo.exception;

public class ContaOrigemNaoEncontradaException extends BusinessException {

    private static String mensagemPadrao = "Conta Origem não encontrada!";

    public ContaOrigemNaoEncontradaException() {
        super(mensagemPadrao);
    }

    public ContaOrigemNaoEncontradaException(String message) {
        super(message);
    }

    public ContaOrigemNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContaOrigemNaoEncontradaException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

}
