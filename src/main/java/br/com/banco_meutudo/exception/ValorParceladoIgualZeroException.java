package br.com.banco_meutudo.exception;

public class ValorParceladoIgualZeroException extends BusinessException {

    private static String mensagemPadrao = "Valor parcelado não pode ser igual à 0";

    public ValorParceladoIgualZeroException() {
        super(mensagemPadrao);
    }

    public ValorParceladoIgualZeroException(String message) {
        super(message);
    }

    public ValorParceladoIgualZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValorParceladoIgualZeroException(Throwable cause) {
        super(mensagemPadrao, cause);
    }

}
