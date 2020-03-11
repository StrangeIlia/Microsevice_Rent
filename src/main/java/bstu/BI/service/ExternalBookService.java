package bstu.BI.service;

import bstu.BI.entity.enums.BookService_Operation;
import bstu.BI.web.dto.BookService_Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-service", url = "${book_service}")
public interface ExternalBookService {
    @PostMapping("/operation")
    BookService_Response operation(@RequestParam("bookTypeId") Integer bookTypeId,
                                   @RequestParam("operation") BookService_Operation operation);

    @PutMapping("/transaction")
    void transaction(@RequestParam("transactionId") Integer transactionId);
}
