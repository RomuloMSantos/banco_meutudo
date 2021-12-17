package br.com.banco_meutudo.exception;

public class TransferenciaNaoInformadaException extends BusinessException {

    private static String mensagemPadrao = "Transferência deve ser informada!";

    public TransferenciaNaoInformadaException() {
        super(mensagemPadrao);
    }

    public TransferenciaNaoInformadaException(String message) {
        super(message);
    }

    public TransferenciaNaoInformadaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferenciaNaoInformadaException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

}
