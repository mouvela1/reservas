package com.utp.reservas.exception;

public class ExceptionsCustums {

    public class UsuarioNoEncontradoException extends RuntimeException {
        public UsuarioNoEncontradoException(String message) {
            super(message);
        }
    }

    public class EspacioNoValidoException extends RuntimeException {
        public EspacioNoValidoException(String message) {
            super(message);
        }
    }

    public class EspacioNoEncontradoException extends RuntimeException {
        public EspacioNoEncontradoException(String message) {
            super(message);
        }
    }

    public class EspacioNoDisponibleException extends RuntimeException {
        public EspacioNoDisponibleException(String message) {
            super(message);
        }
    }

    public class FechasInvalidasException extends RuntimeException {
        public FechasInvalidasException(String message) {
            super(message);
        }
    }


}
