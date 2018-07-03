package pl.radekbrandt.booking.notification;

import org.springframework.stereotype.Component;

import pl.radekbrandt.booking.entity.Reservation;

/**
 * Utils for sending notification.
 * @author Radek
 *
 */
@Component
public class SenderUtil {
	/**
	 * Create a message for a particulat reservation.
	 * @param reservation
	 * @return
	 */
	public String getMsg(Reservation reservation)
	{
		// TODO implement translations/messages provider 
		return "Your reservation number " + reservation.getNumber() + " is beggining tomorrow.";
	}
	
	/**
	 * Obtain address from a particular reservation.
	 * @param reservation
	 * @return
	 */
	public String getAddress(Reservation reservation)
	{
		// TODO implement guest entity correlated with reservation 
		return "some_address_from_guest_entity";
	}
}
