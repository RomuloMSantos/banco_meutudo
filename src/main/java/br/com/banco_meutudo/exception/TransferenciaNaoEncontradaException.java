package br.com.banco_meutudo.exception;

public class TransferenciaNaoEncontradaException extends BusinessException {

    public TransferenciaNaoEncontradaException() {
        super();
    }

    public TransferenciaNaoEncontradaException(String message) {
        super(message);
    }

    public TransferenciaNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferenciaNaoEncontradaException(Throwable cause) {
        super(cause);
    }

    public TransferenciaNaoEncontradaException(long id) {
        super("Transferência ID: " + id + " não encontrada!");
    }

}
