package pl.radekbrandt.booking.client.menu;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import pl.radekbrandt.booking.client.action.ActionIf;
import pl.radekbrandt.booking.client.action.BookRoom;
import pl.radekbrandt.booking.client.action.CancelRoom;
import pl.radekbrandt.booking.client.action.ListFreeRooms;
import pl.radekbrandt.booking.client.action.Quit;

/**
 * Basic implementation of menu items provider.
 * @author Radek
 *
 */
@Component 
public class BasicMenuProvider implements MenuItemsProviderIf {

	@Override
	public Map<Integer, ActionIf> getActions() {
		Map<Integer, ActionIf> actions = new HashMap<>();
		actions.put(1, new ListFreeRooms());
		actions.put(2, new BookRoom());
		actions.put(3, new CancelRoom());
		actions.put(4, new Quit());
		return actions;
	}

}
