package org.example.exception;

public class NumberLimitException extends RuntimeException{

    public NumberLimitException() {
        super("Превышен лимит выдачи номеров!!!");
    }
}
