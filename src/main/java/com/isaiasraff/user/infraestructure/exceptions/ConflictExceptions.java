package com.isaiasraff.user.infraestructure.exceptions;

public class ConflictExceptions extends RuntimeException {
    public ConflictExceptions(String mensagem){
        super(mensagem);
    }
    public ConflictExceptions(String mensagem, Throwable throwable){
        super(mensagem);

    }
}
