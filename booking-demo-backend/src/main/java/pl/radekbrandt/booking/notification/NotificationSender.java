package pl.radekbrandt.booking.notification;

/**
 * Interface for notification sending mechanisms.
 * @author Radek
 *
 */
public interface NotificationSender {
	/**
	 * Sends notification.
	 * @param address 
	 * @param message
	 */
	void send(String address, String message);
}
