package br.com.banco_meutudo.exception;

public class ContaNaoEncontradaException extends BusinessException {

    private static String mensagemPadrao = "Conta não econtrada";

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
        super(mensagemPadrao, cause);
    }

    public ContaNaoEncontradaException(long id) {
        super("Conta ID: " + id + " não encontrada!");
    }

}
