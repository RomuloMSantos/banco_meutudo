package br.com.banco_meutudo.exception;

public class TransferenciaNaoExecutadaException extends BusinessException {

    public TransferenciaNaoExecutadaException() {
        super("Transferência ainda não foi executada!");
    }

    public TransferenciaNaoExecutadaException(String message) {
        super(message);
    }

    public TransferenciaNaoExecutadaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferenciaNaoExecutadaException(Throwable cause) {
        super(cause);
    }

}
