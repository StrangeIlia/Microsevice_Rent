package bstu.BI.service;

import bstu.BI.entity.domain.RentalTicket;

import java.util.Collection;
import java.util.Optional;

public interface RentalTickerService {
    Optional<RentalTicket> findById(Integer ticketId);

    Collection<RentalTicket> findByUserId(Long userId);

    boolean save(RentalTicket rentalTicket);

    void delete(RentalTicket rentalTicket);

    void delete(Collection<RentalTicket> rentalTickets);
}
