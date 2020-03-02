package bstu.BI.service.v1;

import bstu.BI.entity.domain.RentalTicket;

import java.util.Collection;
import java.util.Optional;

public interface BookService {
    Optional<RentalTicket> startTransaction(Integer bookTypeId);

    void finishTransaction(RentalTicket rentalTicket);

    Collection<RentalTicket> getReturnedBooks(Collection<RentalTicket> rentalTickets);

    void returnBooks(Collection<RentalTicket> rentalTickets);
}
