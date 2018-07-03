package pl.radekbrandt.booking.client.action;

import java.util.HashMap;
import java.util.Map;

import pl.radekbrandt.booking.client.exception.ExitApplicationException;

/**
 * Quit the application action.
 * @author Radek
 *
 */
public class Quit implements ActionIf {

	@Override
	public void execute(Map<String, String> args) {
		throw new ExitApplicationException();
	}

	@Override
	public String getLabel() {
		return "Quit";
	}

	@Override
	public Map<String, String> getArgumentsForMenu() {
		return new HashMap<>();
	}

}
