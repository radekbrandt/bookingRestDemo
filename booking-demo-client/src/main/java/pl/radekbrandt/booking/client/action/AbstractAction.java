package pl.radekbrandt.booking.client.action;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.radekbrandt.booking.client.menu.ConsoleSupport;

public abstract class AbstractAction implements ActionIf {
	
	private static final Logger log = LoggerFactory.getLogger(AbstractAction.class);
	
	public AbstractAction() {
		super();
	}

	protected void handleHttpError(HttpStatusCodeException e) {
		log.debug("error consuming REST service: ", e);
		String body = e.getResponseBodyAsString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(body);
		} catch (IOException e1) {
			log.debug("error parsing json", e1);
		}
		String errorMsg = jsonNode.get("message").asText();
		ConsoleSupport.printLine("Problem with executing action: " + errorMsg);
	}

}