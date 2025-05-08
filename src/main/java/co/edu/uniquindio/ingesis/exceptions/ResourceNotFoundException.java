package co.edu.uniquindio.ingesis.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String usuarioNoEncontrado) {
        super(usuarioNoEncontrado);
    }
}