package br.com.banco_meutudo.exception;

public class ContaOrigemNaoEncontradaException extends BusinessException {

    public ContaOrigemNaoEncontradaException() {
        super("Conta Origem não encontrada!");
    }

    public ContaOrigemNaoEncontradaException(String message) {
        super(message);
    }

    public ContaOrigemNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContaOrigemNaoEncontradaException(Throwable cause) {
        super(cause);
    }

}
