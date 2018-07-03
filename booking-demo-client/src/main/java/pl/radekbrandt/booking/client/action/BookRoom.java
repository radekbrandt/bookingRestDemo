package pl.radekbrandt.booking.client.action;

import static pl.radekbrandt.booking.client.action.ActionConstants.BOOKING_BEGIN;
import static pl.radekbrandt.booking.client.action.ActionConstants.BOOKING_END;
import static pl.radekbrandt.booking.client.action.ActionConstants.ROOM_ID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import pl.radekbrandt.booking.client.entity.Reservation;
import pl.radekbrandt.booking.client.exception.ValidationException;
import pl.radekbrandt.booking.client.menu.ConsoleSupport;

/**
 * Booking a room action.
 * 
 * @author Radek
 *
 */
public class BookRoom extends AbstractAction implements ActionIf {

	private static final String URI = "http://localhost:8080/room/{roomId}/reservation";
	static final Logger log = LoggerFactory.getLogger(BookRoom.class);

	@Override
	public void execute(Map<String, String> args) throws ValidationException {
		validateArgs(args);
		log.debug("booking a room at " + URI + " with params: " + getParams(args).toString());
		Reservation response = consumeRest(args);
		ConsoleSupport.printLine("== Room has been booked succesfully ==");
		ConsoleSupport.printLine("== Your reservation number: " + response.getNumber() + " ==");
	}

	@Override
	public String getLabel() {
		return "Book a room";
	}

	@Override
	public Map<String, String> getArgumentsForMenu() {
		Map<String, String> argsForMenu = new LinkedHashMap<>();
		argsForMenu.put(ROOM_ID, "Please enter a room ID.");
		argsForMenu.put(BOOKING_BEGIN, "Please enter a booking begin date in format [YYYY-mm-DD]");
		argsForMenu.put(BOOKING_END, "Please enter a booking end date in format [YYYY-mm-DD]");
		return argsForMenu;
	}

	private Reservation consumeRest(Map<String, String> args) {
		LocalDate begin = LocalDate.parse(args.get(BOOKING_BEGIN), DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate end = LocalDate.parse(args.get(BOOKING_END), DateTimeFormatter.ISO_LOCAL_DATE);
		Reservation reservation = new Reservation(begin, end);
		Reservation response = null;
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpEntity<Reservation> request = new HttpEntity<>(reservation);
			response = restTemplate.postForObject(URI, request, Reservation.class, getParams(args));
		} catch (ResourceAccessException e) {
			log.error("error consuming REST service: ", e);
			ConsoleSupport.printLine("Conneection problem, try again later.");
			throw new IllegalStateException(e);
		} catch (HttpStatusCodeException e) {
			handleHttpError(e);
			throw new IllegalStateException(e);
		}
		return response;
	}

	private Map<String, String> getParams(Map<String, String> args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("roomId", args.get(ROOM_ID));
		return params;
	}

	private void validateArgs(Map<String, String> args) throws ValidationException {
		try {
			LocalDate.parse(args.get(BOOKING_BEGIN), DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate.parse(args.get(BOOKING_END), DateTimeFormatter.ISO_LOCAL_DATE);
			Integer.parseInt(args.get((ROOM_ID)));
		} catch (DateTimeParseException | NumberFormatException e) {
			log.error("validation exception ", e);
			throw new ValidationException();
		}
	}
}
