package bstu.BI.service.imp;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.BookService;
import bstu.BI.service.ExternalBookService;
import bstu.BI.web.dto.book_service.DTO_BookService_Operation;
import bstu.BI.web.dto.book_service.DTO_BookService_Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    ExternalBookService externalBookService;

    @Override
    public Optional<RentalTicket> buyOne(Long bookTypeId) {
        DTO_BookService_Operation operation =
                new DTO_BookService_Operation(bookTypeId, 1L);
        ResponseEntity<DTO_BookService_Response> responseEntity =
                externalBookService.bookSub(operation);
        if(!responseEntity.getStatusCode().equals(HttpStatus.OK))
            return Optional.empty();
        DTO_BookService_Response response = responseEntity.getBody();
        if(response == null)
            return Optional.empty();
        RentalTicket ticket = new RentalTicket();
        ticket.setBookTypeId(bookTypeId);
        ticket.setRentPrice(response.getRentPrice());
        ticket.setPurchasePrice(response.getPurchasePrice());
        return Optional.of(ticket);
    }

    @Override
    public boolean returnOne(Long bookTypeId) {
        DTO_BookService_Operation operation =
                new DTO_BookService_Operation(bookTypeId, 1L);
        return externalBookService.bookSub(operation).getStatusCode().equals(HttpStatus.OK);
    }
}
