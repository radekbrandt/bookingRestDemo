package pl.radekbrandt.booking.client.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation {
	@JsonDeserialize(using = LocalDateDeserializer.class)  
	@JsonSerialize(using = LocalDateSerializer.class)  
	private LocalDate bookingBegin;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)  
	@JsonSerialize(using = LocalDateSerializer.class)  
	private LocalDate bookingEnd;
	
	private LocalDateTime canceledAt; 
	private CancelationReason cancelReason;

	private String number;

	public Reservation(LocalDate bookingBegin, LocalDate bookingEnd, String number) {
		super();
		this.bookingBegin = bookingBegin;
		this.bookingEnd = bookingEnd;
		this.number = number;
	}

	public Reservation(LocalDate bookingBegin, LocalDate bookingEnd) {
		super();
		this.bookingBegin = bookingBegin;
		this.bookingEnd = bookingEnd;
	}
	
	public Reservation() {
    }
	
	public LocalDate getBookingBegin() {
		return bookingBegin;
	}

	public void setBookingBegin(LocalDate bookingBegin) {
		this.bookingBegin = bookingBegin;
	}

	public LocalDate getBookingEnd() {
		return bookingEnd;
	}

	public void setBookingEnd(LocalDate bookingEnd) {
		this.bookingEnd = bookingEnd;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public LocalDateTime getCanceledAt() {
		return canceledAt;
	}

	public void setCanceledAt(LocalDateTime canceledAt) {
		this.canceledAt = canceledAt;
	}

	public CancelationReason getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(CancelationReason cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Override
	public String toString() {
		return "Reservation [bookingBegin=" + bookingBegin + ", bookingEnd=" + bookingEnd + ", number=" + number + "]";
	}
	
	public enum CancelationReason {
		UNKNOWN, BY_GUEST, NOT_PAID
	}

}
