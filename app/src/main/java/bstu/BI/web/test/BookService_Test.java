package bstu.BI.web.test;

import bstu.BI.entity.enums.Status;
import bstu.BI.web.dto.BookService_Response;
import bstu.BI.web.dto.DTO_RentBook;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/test/book-service")
public class BookService_Test {
    @PutMapping("/operation")
    BookService_Response operation(@RequestBody @Valid DTO_RentBook rentBook) {
        BookService_Response response = new BookService_Response();
        response.setStatus(Status.SUCCESS);
        if (rentBook.getAmountBooks() < 0) {
            response.setPurchasePrice(1000.0);
            response.setRentPrice(300.0);
        }
        return response;
    }
}
