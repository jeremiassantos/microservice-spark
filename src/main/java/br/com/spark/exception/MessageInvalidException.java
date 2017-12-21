package br.com.spark.exception;

/**
 * Created on 21/12/2017.
 */
public class MessageInvalidException extends RuntimeException {

    public MessageInvalidException(String cause) {
        super(cause);
    }
}
