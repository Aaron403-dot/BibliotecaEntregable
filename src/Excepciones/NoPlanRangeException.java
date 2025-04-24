package Excepciones;

/**
 * <H1>Clase NoPlanRangeException</H1>
 * @category Excepcion
 * Es La clase Para la excepcio en caso de que no se cumplan las condiciones para el alquiler
 * @author Aaron del Cristo Suárez Suárez
 * */

public class NoPlanRangeException extends Exception {

	public NoPlanRangeException() {
	}

	public NoPlanRangeException(String message) {
		super(message);
	}

	public NoPlanRangeException(Throwable cause) {
		super(cause);
	}

	public NoPlanRangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPlanRangeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
