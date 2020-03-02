package bstu.BI.service.v1;

import bstu.BI.entity.domain.RentalTicket;

import java.util.Collection;
import java.util.Optional;

public interface RentalTickerService {
    Optional<RentalTicket> findById(Integer ticketId);

    Collection<RentalTicket> findByUserId(Integer userId);

    boolean save(RentalTicket rentalTicket);

    void delete(RentalTicket rentalTicket);

    void delete(Collection<RentalTicket> rentalTickets);
}
