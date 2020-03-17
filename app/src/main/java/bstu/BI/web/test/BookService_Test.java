package bstu.BI.web.test;

import bstu.BI.web.dto.book_service.DTO_BookService_Operation;
import bstu.BI.web.dto.book_service.DTO_BookService_Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test/book-service")
public class BookService_Test {
    @PutMapping("/books/sub")
    ResponseEntity<DTO_BookService_Response> bookSub(@RequestBody DTO_BookService_Operation data)
    {
        DTO_BookService_Response response = new DTO_BookService_Response();
        response.setPurchasePrice(1000.0);
        response.setRentPrice(300.0);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/books/ref")
    ResponseEntity<DTO_BookService_Response> bookRef(@RequestBody DTO_BookService_Operation data)
    {
        return ResponseEntity.ok().build();
    }
}
