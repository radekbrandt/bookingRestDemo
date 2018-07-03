package pl.radekbrandt.booking.client.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.radekbrandt.booking.client.action.ActionIf;
import pl.radekbrandt.booking.client.exception.ExitApplicationException;
import pl.radekbrandt.booking.client.exception.ValidationException;

@Component
public class ConsoleSupport {

	@Autowired
	private MenuItemsProviderIf menuItemsProvider;
	private Map<Integer, ActionIf> actions;
	private Scanner consoleReader;
	
	private static final Logger log = LoggerFactory.getLogger(ConsoleSupport.class);
	
	public ConsoleSupport() {
	}
	
	/**
	 * Generates menu according provided actions and handle menu operations.
	 */
	public void startMenu()
	{
		log.debug("menu generation");
		actions = menuItemsProvider.getActions();	
		consoleReader = new Scanner(System.in);
		boolean repeat = true;
		while (repeat)
		{
			printMenu();
			repeat = handleUserInput(repeat);
		}
		consoleReader.close();
	}
	
	public static void printLine(String line)
	{
		System.out.println(line);
	}

	private boolean handleUserInput(boolean repeat) {
		int selected = readConsole();
		ActionIf action = actions.get(selected);
		if (action != null)
		{
			log.debug("handling action " + action.getLabel());
			repeat = handleAction(action);
		}
		else
		{
			printRepeat();
		}
		return repeat;
	}


	private boolean handleAction(ActionIf action) {
		try
		{
			action.execute(askForArguments(action));
			return true;
		}
		catch (ExitApplicationException e)
		{
			return false;
		} catch (ValidationException e) {
			printLine("Wrong argument, try again");
			return true;
		}catch (IllegalStateException e) {
			return true;
		}
	}
	
	private Map<String, String> askForArguments(ActionIf action) {
		Map<String, String> args = new HashMap<>();
		action.getArgumentsForMenu().forEach( (key, desc) ->{
			printLine(desc);
			String input = System.console().readLine();
			args.put(key, input);
		});
		log.debug("obtained agruments from user " +  args.toString());
		return args;
	}

	private void printMenu() {
		printLine("\n\nRoom reservation - choose an option");
		printLine("-----------------------------------\n");
		menuItemsProvider.getActions().forEach((key, action) -> printLine(key + " - " + action.getLabel()));
	}

	private int readConsole() {
		int selection;
		
		selection = consoleReader.nextInt();
		return selection;
	}
	
	private void printRepeat() {
		printLine("Unrecognized option, please repeat");
	}
}
