package pl.radekbrandt.booking.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Rest notification sender. Address field is an is an endpoint URI.
 * @author Radek
 *
 */
@Component("restSender")
public class RestNotificationSender implements NotificationSender {
	
	private static final Logger log = LoggerFactory.getLogger(RestNotificationSender.class);

	@Override
	public void send(String address, String message) {
		// REST endpoint implementation
		// ..
		log.info("Sending REST notification to " + address);
	}

}
