package bstu.BI.service.v1.imp.v1;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.repository.RentalTickerRepository;
import bstu.BI.service.v1.RentalTickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class RentalTicketServiceImp implements RentalTickerService {
    @Autowired
    RentalTickerRepository tickerRepository;

    @Override
    public Optional<RentalTicket> findById(Integer ticketId) {
        return tickerRepository.findById(ticketId);
    }

    @Override
    public Collection<RentalTicket> findByUserId(Integer userId) {
        return tickerRepository.findByUserId(userId);
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
