package bstu.BI.service;

import bstu.BI.web.dto.BookService_Response;
import bstu.BI.web.dto.DTO_RentBook;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-service", url = "${book_service}")
public interface ExternalBookService {
    @PutMapping("/operation")
    BookService_Response operation(@RequestBody DTO_RentBook rentBook);
}
