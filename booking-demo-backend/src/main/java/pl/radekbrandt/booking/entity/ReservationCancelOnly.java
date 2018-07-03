package pl.radekbrandt.booking.entity;

/**
 * Used for partial update for cancellation.
 * @author Radek
 *
 */
public class ReservationCancelOnly {
	private CancellationReason cancelReason;
	
	ReservationCancelOnly(){}
	
	public ReservationCancelOnly(CancellationReason cancelReason) {
		super();
		this.cancelReason = cancelReason;
	}

	public CancellationReason getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(CancellationReason cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	
}
