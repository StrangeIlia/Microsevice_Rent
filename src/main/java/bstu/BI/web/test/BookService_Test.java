package bstu.BI.web.test;

import bstu.BI.web.dto.DTO_BookService_Info;
import bstu.BI.web.dto.DTO_RentBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test/book-service")
public class BookService_Test {
    @PutMapping("/")
    public ResponseEntity<DTO_BookService_Info> bookOperation(@RequestBody DTO_RentBook rentBook) {
        if (rentBook.getAmountBooks() < 0) {
            DTO_BookService_Info response = new DTO_BookService_Info();
            response.setPurchasePrice(1000.0);
            response.setRentPrice(300.0);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok().build();
    }
}
