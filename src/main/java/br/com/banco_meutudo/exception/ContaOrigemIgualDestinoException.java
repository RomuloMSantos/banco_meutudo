package br.com.banco_meutudo.exception;

public class ContaOrigemIgualDestinoException extends BusinessException {

    public ContaOrigemIgualDestinoException() {
        super("Conta Origem igual a Conta Destino!");
    }

    public ContaOrigemIgualDestinoException(String message) {
        super(message);
    }

    public ContaOrigemIgualDestinoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContaOrigemIgualDestinoException(Throwable cause) {
        super(cause);
    }

}
