package bstu.BI.service.imp;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.repository.RentalTickerRepository;
import bstu.BI.service.RentalTickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RentalTicketServiceImp implements RentalTickerService {
    @Autowired
    RentalTickerRepository tickerRepository;

    @Override
    public Optional<RentalTicket> findById(Integer ticketId) {
        return tickerRepository.findById(ticketId);
    }

    @Override
    public Collection<RentalTicket> findByUserId(Long userId) {
        return tickerRepository.findByUserId(userId);
    }

    @Override
    public Optional<RentalTicket> findAndRemoveOld(Long userId, Long bookTypeId) {
        Collection<RentalTicket> rentalBooks = tickerRepository.findByUserId(userId);
        Iterator<RentalTicket> iterator = rentalBooks.stream()
                .filter(x -> x.getBookTypeId().equals(bookTypeId))
                .sorted(Comparator.comparing(RentalTicket::getRentalFinish))
                .iterator();
        Optional<RentalTicket> optionalRentalTicket = Optional.empty();
        Date currentDate = new Date();
        while(iterator.hasNext())
        {
            RentalTicket ticket = iterator.next();
            Date finish = ticket.getRentalFinish();
            int result = currentDate.compareTo(finish);
            if (result > 0)
                delete(ticket);
            else {
                optionalRentalTicket = Optional.of(ticket);
                break;
            }
        }
        return optionalRentalTicket;
    }

    @Override
    public boolean save(RentalTicket rentalTicket) {
        try {
            tickerRepository.save(rentalTicket);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void delete(RentalTicket rentalTicket) {
        tickerRepository.delete(rentalTicket);
    }

    @Override
    public void delete(Collection<RentalTicket> rentalTickets) {
        for (RentalTicket rentalTicket : rentalTickets)
            tickerRepository.delete(rentalTicket);
    }
}
