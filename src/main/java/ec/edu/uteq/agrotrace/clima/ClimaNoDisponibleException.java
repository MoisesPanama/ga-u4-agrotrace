package ec.edu.uteq.agrotrace.clima;

// Excepcion para cuando el servicio meteorologico no esta disponible
public class ClimaNoDisponibleException extends RuntimeException {

    public ClimaNoDisponibleException(String mensaje) {
        super(mensaje);
    }

    public ClimaNoDisponibleException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
