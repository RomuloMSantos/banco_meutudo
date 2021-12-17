package br.com.banco_meutudo.exception;

public class TransferenciaJaEstornadaException extends BusinessException {

    public TransferenciaJaEstornadaException() {
        super("Transferência já estornada!");
    }

    public TransferenciaJaEstornadaException(String message) {
        super(message);
    }

    public TransferenciaJaEstornadaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferenciaJaEstornadaException(Throwable cause) {
        super(cause);
    }

}
