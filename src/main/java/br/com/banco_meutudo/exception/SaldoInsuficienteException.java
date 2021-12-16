package br.com.banco_meutudo.exception;

public class SaldoInsuficienteException extends BusinessException {

    public SaldoInsuficienteException() {
        super();
    }

    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaldoInsuficienteException(Throwable cause) {
        super(cause);
    }

}
