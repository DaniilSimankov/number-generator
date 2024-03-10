package org.example.exception;

public class NumberLimitException extends Exception{

    public NumberLimitException() {
        super("Превышен лимит выдачи номеров!!!");
    }
}
