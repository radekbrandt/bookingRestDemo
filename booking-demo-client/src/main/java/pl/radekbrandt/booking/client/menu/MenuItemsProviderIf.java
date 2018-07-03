package pl.radekbrandt.booking.client.menu;

import java.util.Map;

import pl.radekbrandt.booking.client.action.ActionIf;

/**
 * 
 * Provides actions for menu generation.
 * @author Radek
 *
 */
public interface MenuItemsProviderIf {
	/**
	 * Returns actions.
	 * @return map of <action id; action>
	 */
	Map<Integer, ActionIf> getActions();
}
