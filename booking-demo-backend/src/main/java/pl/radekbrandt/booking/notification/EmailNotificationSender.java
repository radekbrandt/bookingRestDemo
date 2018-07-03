package pl.radekbrandt.booking.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Email notification sender. Address field is an email address.
 * @author Radek
 *
 */
@Component("emailSender")
public class EmailNotificationSender implements NotificationSender {

	private static final Logger log = LoggerFactory.getLogger(EmailNotificationSender.class);

	@Override
	public void send(String address, String message) {
		// EMAIL endpoint implementation
		// ..
		log.info("Sending EMAIL notification to" + address);
	}

}
