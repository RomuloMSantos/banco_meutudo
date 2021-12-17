package br.com.banco_meutudo.exception;

public class ContaDestinoNaoEncontradaException extends BusinessException {

    private static String mensagemPadrao = "Conta Destino não encontrada!";

    public ContaDestinoNaoEncontradaException() {
        super(mensagemPadrao);
    }

    public ContaDestinoNaoEncontradaException(String message) {
        super(message);
    }

    public ContaDestinoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContaDestinoNaoEncontradaException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

}
