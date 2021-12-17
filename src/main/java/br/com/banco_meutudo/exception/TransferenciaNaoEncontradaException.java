package br.com.banco_meutudo.exception;

public class TransferenciaNaoEncontradaException extends BusinessException {

    private static String mensagemPadrao = "Transferência não encontrada!";

    public TransferenciaNaoEncontradaException() {
        super(mensagemPadrao);
    }

    public TransferenciaNaoEncontradaException(String message) {
        super(message);
    }

    public TransferenciaNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferenciaNaoEncontradaException(Throwable cause) {
        super(mensagemPadrao,cause);
    }

    public TransferenciaNaoEncontradaException(long id) {
        super("Transferência ID: " + id + " não encontrada!");
    }

}
