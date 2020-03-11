package bstu.BI.service;

import bstu.BI.web.dto.DTO_BookService_Info;
import bstu.BI.web.dto.DTO_RentBook;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-service", url = "${book_service}")
public interface ExternalBookService {
    @PutMapping("/")
    ResponseEntity<DTO_BookService_Info> bookOperation(@RequestBody DTO_RentBook rentBook);
}
