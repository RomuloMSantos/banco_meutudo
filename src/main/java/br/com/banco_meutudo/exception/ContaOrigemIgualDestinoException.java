package br.com.banco_meutudo.exception;

public class ContaOrigemIgualDestinoException extends BusinessException {

    private static String mensagemPadrao = "Conta Origem igual a Conta Destino";

    public ContaOrigemIgualDestinoException() {
        super(mensagemPadrao);
    }

    public ContaOrigemIgualDestinoException(String message) {
        super(message);
    }

    public ContaOrigemIgualDestinoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContaOrigemIgualDestinoException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

}
