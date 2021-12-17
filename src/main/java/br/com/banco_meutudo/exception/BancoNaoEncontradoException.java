package br.com.banco_meutudo.exception;

public class BancoNaoEncontradoException extends BusinessException {

    private static String mensagemPadrao = "Banco não encontrado.";

    public BancoNaoEncontradoException() {
        super(mensagemPadrao);
    }

    public BancoNaoEncontradoException(String message) {
        super(message);
    }

    public BancoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public BancoNaoEncontradoException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

    public BancoNaoEncontradoException(long id) {
        super("Banco ID: " + id + " não encontrado.");
    }


}
