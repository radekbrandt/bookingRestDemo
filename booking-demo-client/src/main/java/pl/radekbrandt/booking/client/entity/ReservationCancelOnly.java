package pl.radekbrandt.booking.client.entity;

import pl.radekbrandt.booking.client.entity.Reservation.CancelationReason;

/**
 * Used for partial update for cancellation.
 * @author Radek
 *
 */
public class ReservationCancelOnly {
	private CancelationReason cancelReason;
	
	ReservationCancelOnly(){}
	
	public ReservationCancelOnly(CancelationReason cancelReason) {
		super();
		this.cancelReason = cancelReason;
	}

	public CancelationReason getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(CancelationReason cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	
}
