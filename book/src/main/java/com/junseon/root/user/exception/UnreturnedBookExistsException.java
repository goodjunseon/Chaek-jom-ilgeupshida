package com.junseon.root.user.exception;

public class UnreturnedBookExistsException extends RuntimeException{
    private final Long userId;

    public UnreturnedBookExistsException(Long userId, String message) {
        super(message);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
