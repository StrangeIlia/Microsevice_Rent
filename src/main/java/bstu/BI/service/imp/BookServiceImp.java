package bstu.BI.service.imp;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.BookService;
import bstu.BI.service.ExternalBookService;
import bstu.BI.web.dto.BookService_Response;
import bstu.BI.web.dto.DTO_RentBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    ExternalBookService externalBookService;

    @Override
    public Optional<RentalTicket> buyOne(Long bookTypeId) {
        DTO_RentBook rentBook = new DTO_RentBook(bookTypeId, -1L);
        BookService_Response response = externalBookService.operation(rentBook);
        RentalTicket ticket = new RentalTicket();
        ticket.setBookTypeId(bookTypeId);
        ticket.setRentPrice(response.getRentPrice());
        ticket.setPurchasePrice(response.getPurchasePrice());
        return Optional.of(ticket);
    }

    @Override
    public boolean returnOne(Long bookTypeId) {
        DTO_RentBook rentBook = new DTO_RentBook(bookTypeId, 1L);
        BookService_Response response = externalBookService.operation(rentBook);
        return response.isSuccess();
    }
}
