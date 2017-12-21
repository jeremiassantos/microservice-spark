package br.com.spark.exception;

/**
 * Created on 21/12/2017.
 */
public class MessageException extends RuntimeException {

    public MessageException(String cause) {
        super(cause);
    }
}
