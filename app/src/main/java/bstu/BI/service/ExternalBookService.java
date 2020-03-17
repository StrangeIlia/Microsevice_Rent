package bstu.BI.service;

import bstu.BI.web.dto.book_service.DTO_BookService_Operation;
import bstu.BI.web.dto.book_service.DTO_BookService_Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-service", url = "${book_service}")
public interface ExternalBookService {
    @PutMapping("/books/sub")
    ResponseEntity<DTO_BookService_Response> bookSub(@RequestBody DTO_BookService_Operation data);

    @PutMapping("/books/ref") //Зачем возращать null? Вообще хз, но я не ответсвеннен за это ХД
    ResponseEntity<DTO_BookService_Response> bookRef(@RequestBody DTO_BookService_Operation data);
}
