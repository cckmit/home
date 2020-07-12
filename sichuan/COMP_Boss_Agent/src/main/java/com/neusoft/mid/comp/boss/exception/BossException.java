package com.neusoft.mid.comp.boss.exception;

/**
 * li-lei
 */
public class BossException extends Exception {

    private static final long serialVersionUID = 1L;

    public BossException() {
        super();
    }

    public BossException(Exception e) {
        super(e);
    }

    public BossException(String msg) {
        super(msg);
    }

    public BossException(String msg, Throwable ex) {
        super(msg, ex);
    }
    
    public BossException(Throwable cause) {
        super(cause);
    }
}
