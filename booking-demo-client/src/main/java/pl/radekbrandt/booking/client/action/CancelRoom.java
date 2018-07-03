package pl.radekbrandt.booking.client.action;

import static pl.radekbrandt.booking.client.action.ActionConstants.RESERVATION_NUMBER;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import pl.radekbrandt.booking.client.entity.Reservation;
import pl.radekbrandt.booking.client.entity.ReservationCancelOnly;
import pl.radekbrandt.booking.client.entity.Reservation.CancelationReason;
import pl.radekbrandt.booking.client.menu.ConsoleSupport;

/**
 * Cancels the booking.
 * 
 * @author Radek
 *
 */
public class CancelRoom extends AbstractAction {

	private static final String URI = "http://localhost:8080/reservation/{number}";
	private static final Logger log = LoggerFactory.getLogger(BookRoom.class);

	@Override
	public void execute(Map<String, String> args) {
		Reservation response = consumeRest(args);
		ConsoleSupport.printLine("== Reservation " + response.getNumber() + " has been cancelled succesfully ==");
	}

	@Override
	public String getLabel() {
		return "Cancel a reservation";
	}

	@Override
	public Map<String, String> getArgumentsForMenu() {
		Map<String, String> args = new HashMap<>();
		args.put(RESERVATION_NUMBER, "Please enter the reservation number");
		return args;
	}

	private Reservation consumeRest(Map<String, String> args) {
		ReservationCancelOnly reservationCancel = new ReservationCancelOnly(CancelationReason.BY_GUEST);
		Reservation response = null;
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		try {
			HttpEntity<ReservationCancelOnly> request = new HttpEntity<>(reservationCancel);
			response = restTemplate.patchForObject(URI, request, Reservation.class, getParams(args));
		} catch (ResourceAccessException  e) {
			log.debug("error consuming REST service: ", e);
			ConsoleSupport.printLine("Lost connection, try again later.");
			throw new IllegalStateException(e);
		} catch (HttpStatusCodeException e) {
			handleHttpError(e);
			throw new IllegalStateException(e);
		}
		return response;
	}

	private Map<String, String> getParams(Map<String, String> args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("number", args.get(RESERVATION_NUMBER));
		return params;
	}

}
