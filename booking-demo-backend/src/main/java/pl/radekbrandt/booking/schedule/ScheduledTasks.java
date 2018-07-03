package pl.radekbrandt.booking.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.radekbrandt.booking.entity.Reservation;
import pl.radekbrandt.booking.notification.NotificationSender;
import pl.radekbrandt.booking.notification.SenderUtil;
import pl.radekbrandt.booking.persistent.ReservationRepository;

/**
 * Defines scheduled operations.
 * 
 * @author Radek
 *
 */
@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	@Qualifier("emailSender")
	private NotificationSender sender;

	@Autowired
	private ReservationRepository resRepository;

	@Autowired
	private SenderUtil util;

	/**
	 * Executes at 12.00 every day for sending notification about upcoming
	 * reservations. 
	 */
	@Scheduled(cron = "0 0 12 * * * ")
	public void sendNotification() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		Optional<List<Reservation>> tomorrowResOpt = resRepository.findByBookingBegin(tomorrow);
		if (tomorrowResOpt.isPresent()) {
			tomorrowResOpt.get().stream()
					.peek(r -> log.info("sending notification for reservation number: " + r.getNumber()))
					.forEach(this::doSend);
		}
	}

	private void doSend(Reservation reservation) {
		sender.send(util.getAddress(reservation), util.getMsg(reservation));
	}
}
