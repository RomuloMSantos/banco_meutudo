package br.com.banco_meutudo.exception;

public class TransferenciaJaEstornadaException extends BusinessException {

    private static String mensagemPadrao = "Transferência já estornada!";

    public TransferenciaJaEstornadaException() {
        super(mensagemPadrao);
    }

    public TransferenciaJaEstornadaException(String message) {
        super(message);
    }

    public TransferenciaJaEstornadaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferenciaJaEstornadaException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

}
