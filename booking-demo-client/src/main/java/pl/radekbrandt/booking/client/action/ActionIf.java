package pl.radekbrandt.booking.client.action;

import java.util.Map;

import pl.radekbrandt.booking.client.exception.ValidationException;

/**
 * Interface for strategy pattern, provides menu entries actions.
 * 
 * @author Radek
 *
 */
public interface ActionIf {
	/**
	 * Action to execute.
	 */
	/**
	 * @param args Arguments entered by a user.
	 * @throws ValidationException 
	 */
	void execute(Map<String, String> args) throws ValidationException;

	/**
	 * Returns label for menu.
	 * 
	 * @return label
	 */
	String getLabel();

	/**
	 * Returns argument names and description for menu.
	 * 
	 * @return map of <argument name; description>
	 */
	Map<String, String> getArgumentsForMenu();
}
