package pl.radekbrandt.booking.client.action;

import static pl.radekbrandt.booking.client.action.ActionConstants.BOOKING_BEGIN;
import static pl.radekbrandt.booking.client.action.ActionConstants.BOOKING_END;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import pl.radekbrandt.booking.client.entity.Room;
import pl.radekbrandt.booking.client.exception.ValidationException;
import pl.radekbrandt.booking.client.menu.ConsoleSupport;

/**
 * Consumes REST endpoint and list all free rooms.
 * 
 * @author Radek
 *
 */
public class ListFreeRooms extends AbstractAction {

	private static final String URI = "http://localhost:8080/room?begin={begin}&end={end}";
	private static final Logger log = LoggerFactory.getLogger(ListFreeRooms.class);

	@Override
	public void execute(Map<String, String> args) throws ValidationException {
		validateArgs(args);

		log.debug("reading free rooms from " + URI + " with params: " + getParams(args).toString());
		ResponseEntity<List<Room>> response = consumeRest(args);
		if (response.getStatusCode().series().equals(Series.SUCCESSFUL)) {
			List<Room> result = response.getBody();
			log.debug("response status: " + response.getStatusCode());
			ConsoleSupport.printLine("== Available rooms ==");
			result.forEach(r -> ConsoleSupport.printLine("ID: " + r.getId() + " Name: " + r.getRoomName()));
		} else {
			log.error("error consuming REST service, returned HTTP status: " + response.getStatusCode());
			ConsoleSupport.printLine("Something went wrong, returned HTTP status: " + response.getStatusCode());
		}
	}

	private ResponseEntity<List<Room>> consumeRest(Map<String, String> args) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Room>> response = null;
		try {
			response = restTemplate.exchange(URI, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Room>>() {
				}, getParams(args));
		}
		catch (ResourceAccessException | HttpStatusCodeException e)
		{
			log.error("error consuming REST service: ", e);
			ConsoleSupport.printLine("Lost connection, try again later.");
			throw new IllegalStateException(e);
		}
		return response;
	}

	private Map<String, String> getParams(Map<String, String> args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("begin", args.get(BOOKING_BEGIN));
		params.put("end", args.get(BOOKING_END));
		return params;
	}

	private void validateArgs(Map<String, String> args) throws ValidationException {
		try {
			LocalDate.parse(args.get(BOOKING_BEGIN), DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate.parse(args.get(BOOKING_END), DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (DateTimeParseException e) {
			log.error("validation exception ", e);
			throw new ValidationException();
		}
	}

	@Override
	public String getLabel() {
		return "List available rooms";
	}

	@Override
	public Map<String, String> getArgumentsForMenu() {
		Map<String, String> args = new LinkedHashMap<>();
		args.put(BOOKING_BEGIN, "Please enter a booking begin date in format [YYYY-mm-DD]");
		args.put(BOOKING_END, "Please enter a booking end date in format [YYYY-mm-DD]");
		return args;
	}
}
