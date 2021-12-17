package br.com.banco_meutudo.exception;

public class SaldoInsuficienteException extends BusinessException {

    private static String mensagemPadrao = "Saldo insuficiente.";

    public SaldoInsuficienteException() {
        super(mensagemPadrao);
    }

    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaldoInsuficienteException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

}
