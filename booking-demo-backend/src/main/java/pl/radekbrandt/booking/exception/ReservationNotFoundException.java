package pl.radekbrandt.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Send "404 Not Found." when reservation does not exist.
 * @author Radek
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReservationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4686816228224398085L;

	public ReservationNotFoundException(String number) {
		super("Reservation number " + number + " not found.");
	}
}
