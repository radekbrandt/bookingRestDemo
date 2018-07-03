package pl.radekbrandt.booking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import pl.radekbrandt.booking.AppConstants;

/**
 * Reservation entity.
 * @author Radek
 *
 */
@Entity
public class Reservation {

	@Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Room room;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DATE_FORMAT)
    private LocalDate bookingBegin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DATE_FORMAT)
    private LocalDate bookingEnd;
    
    private String number;
    
    private LocalDateTime createdAt; 
    private LocalDateTime canceledAt; 
    private CancellationReason cancelReason;

    private Reservation() { } 
    
    public Reservation(Room room, LocalDate bookingBegin, LocalDate bookingEnd) {
		this.room = room;
		this.bookingBegin = bookingBegin;
		this.bookingEnd = bookingEnd;
		this.number = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
		this.createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public Room getRoom() {
		return room;
	}
	
	public void setCancelReason(CancellationReason cancelReason) {
		this.canceledAt = LocalDateTime.now();
		this.cancelReason = cancelReason;
	}

	public CancellationReason getCancelReason() {
		return cancelReason;
	}
	
	public String getNumber()
	{
		return number;
	}

	public LocalDate getBookingBegin() {
		return bookingBegin;
	}

	public LocalDate getBookingEnd() {
		return bookingEnd;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getCanceledAt() {
		return canceledAt;
	}

}
