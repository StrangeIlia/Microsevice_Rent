package bstu.BI.service.imp;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.BookService;
import bstu.BI.service.ExternalBookService;
import bstu.BI.web.dto.BookServiceResponse;
import bstu.BI.web.dto.DTO_BookService_Info;
import bstu.BI.web.dto.DTO_RentBook;
import bstu.BI.web.dto.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    ExternalBookService bookService;

    @Override
    public BookServiceResponse rentBook(Long bookTypeId) {
        ResponseEntity<DTO_BookService_Info> responseEntity =
                bookService.bookOperation(DTO_RentBook.buyOne(bookTypeId));
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK))
            return BookServiceResponse.create(responseEntity.getStatusCode());
        else {
            DTO_BookService_Info response = responseEntity.getBody();
            RentalTicket ticket = new RentalTicket();
            ticket.setRentPrice(response.getRentPrice());
            ticket.setPurchasePrice(response.getPurchasePrice());
            ticket.setBookTypeId(bookTypeId);
            return BookServiceResponse.create(ticket);
        }
    }

    @Override
    public ServiceResponse returnBook(Long bookTypeId) {
        ResponseEntity<DTO_BookService_Info> responseEntity =
                bookService.bookOperation(DTO_RentBook.buyOne(bookTypeId));
        return ServiceResponse.create(responseEntity.getStatusCode());
    }
}
