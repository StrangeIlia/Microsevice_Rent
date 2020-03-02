package bstu.BI.web.test;

import bstu.BI.entity.enums.BookService_Operation;
import bstu.BI.entity.enums.Status;
import bstu.BI.web.v2.dto.BookService_Response;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("api/test/book-service")
public class BookService_Test {
    Random random = new Random();

    @PostMapping("/operation")
    public BookService_Response operation(@RequestParam Integer bookTypeId,
                                          @RequestParam BookService_Operation operation) {
        if (operation.equals(BookService_Operation.GET_BOOK)) {
            BookService_Response response = new BookService_Response();
            response.setStatus(Status.SUCCESS);
            response.setTransactionId(random.nextInt());
            response.setPurchasePrice(1000.0);
            response.setRentPrice(300.0);
            return response;
        }
        BookService_Response response = new BookService_Response();
        response.setStatus(Status.SUCCESS);
        return response;
    }

    @PutMapping("/transaction")
    public void transaction(@RequestParam Integer transactionId) {
        // Идеально
    }
}
