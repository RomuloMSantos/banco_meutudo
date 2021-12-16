package br.com.banco_meutudo.exception;

public class ContaNaoEncontradaException extends BusinessException {

    public ContaNaoEncontradaException() {
        super();
    }

    public ContaNaoEncontradaException(String message) {
        super(message);
    }

    public ContaNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContaNaoEncontradaException(Throwable cause) {
        super(cause);
    }

}
