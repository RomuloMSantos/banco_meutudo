package br.com.banco_meutudo.exception;

public class TransferenciaNaoExecutadaException extends BusinessException {

    private static String mensagemPadrao = "Transferência ainda não foi executada!";

    public TransferenciaNaoExecutadaException() {
        super(mensagemPadrao);
    }

    public TransferenciaNaoExecutadaException(String message) {
        super(message);
    }

    public TransferenciaNaoExecutadaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferenciaNaoExecutadaException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

}
