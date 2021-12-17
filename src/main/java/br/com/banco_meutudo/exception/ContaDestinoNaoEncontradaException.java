package br.com.banco_meutudo.exception;

public class ContaDestinoNaoEncontradaException extends BusinessException {

    public ContaDestinoNaoEncontradaException() {
        super("Conta Destino não encontrada!");
    }

    public ContaDestinoNaoEncontradaException(String message) {
        super(message);
    }

    public ContaDestinoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContaDestinoNaoEncontradaException(Throwable cause) {
        super(cause);
    }

}
