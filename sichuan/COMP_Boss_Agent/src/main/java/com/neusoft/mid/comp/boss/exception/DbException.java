
package com.neusoft.mid.comp.boss.exception;

/**
 * li-lei
 */
public class DbException extends Exception {

    private static final long serialVersionUID = 1L;

    public DbException() {
        super();
    }

    public DbException(Exception e) {
        super(e);
    }

    public DbException(String msg) {
        super(msg);
    }

    public DbException(String msg, Throwable ex) {
        super(msg, ex);
    }
    
    public DbException(Throwable cause) {
        super(cause);
    }
}
