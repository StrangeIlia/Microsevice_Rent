package bstu.BI.service.v2;

import bstu.BI.entity.enums.BookService_Operation;
import bstu.BI.web.v2.dto.BookService_Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-service", url = "localhost:50001/api/test/book-service")
public interface ExternalBookService {
    @PostMapping("/operation")
    BookService_Response operation(@RequestParam Integer bookTypeId,
                                   @RequestParam BookService_Operation operation);

    @PutMapping("/transaction")
    void transaction(@RequestParam Integer transactionId);
}
