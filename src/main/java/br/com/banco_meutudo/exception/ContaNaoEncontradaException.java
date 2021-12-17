package br.com.banco_meutudo.exception;

public class ContaNaoEncontradaException extends BusinessException {

    private static String mensagemPadrao = "Conta não encontrada!";

    public ContaNaoEncontradaException() {
        super(mensagemPadrao);
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

    public ContaNaoEncontradaException(long id) {
        super("Conta ID: " + id + " não encontrada!");
    }

}
