package pl.radekbrandt.booking.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Room entity.
 * @author Radek
 *
 */
@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private String roomName;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private Set<Reservation> reservations = new HashSet<>();

    private Room() { }
    
    public Room(final String roomName) {
        this.roomName = roomName;
    }

    public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }
}