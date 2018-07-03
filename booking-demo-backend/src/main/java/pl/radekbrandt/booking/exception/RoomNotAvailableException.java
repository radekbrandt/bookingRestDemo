package pl.radekbrandt.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Send "404 Not Found." when a book is not available.
 * @author Radek
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoomNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 2342343284398085L;

	public RoomNotAvailableException(Long id) {
		super("Room with ID " + id + " is not available at the particular dates.");
	}
}
