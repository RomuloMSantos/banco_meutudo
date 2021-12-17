package br.com.banco_meutudo.exception;

public class BancoNaoEncontradoException extends BusinessException {

    public BancoNaoEncontradoException() {
        super();
    }

    public BancoNaoEncontradoException(String message) {
        super(message);
    }

    public BancoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public BancoNaoEncontradoException(Throwable cause) {
        super(cause);
    }

    public BancoNaoEncontradoException(long id) {
        super("Banco ID: " + id + " não encontrado.");
    }


}
