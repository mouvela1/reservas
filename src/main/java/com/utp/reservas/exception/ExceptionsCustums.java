package com.utp.reservas.exception;

public class ExceptionsCustums {

    public static class UsuarioNoEncontradoException extends RuntimeException {
        public UsuarioNoEncontradoException(String message) {
            super(message);
        }
    }

    public static class EspacioNoValidoException extends RuntimeException {
        public EspacioNoValidoException(String message) {
            super(message);
        }
    }

    public static class EspacioNoEncontradoException extends RuntimeException {
        public EspacioNoEncontradoException(String message) {
            super(message);
        }
    }

    public static class EspacioNoDisponibleException extends RuntimeException {
        public EspacioNoDisponibleException(String message) {
            super(message);
        }
    }

    public static class FechasInvalidasException extends RuntimeException {
        public FechasInvalidasException(String message) {
            super(message);
        }
    }


}
