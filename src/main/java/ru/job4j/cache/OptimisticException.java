package ru.job4j.cache;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String massage) {
        super(massage);
    }

}
