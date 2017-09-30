package br.com.musicasparamissa.mpmjadmin.backend.exception;

public class InvalidEntityException extends Exception {

    private static final long serialVersionUID = 7986779241257271990L;

    public InvalidEntityException(String msg) {
        super(msg);
    }

}
