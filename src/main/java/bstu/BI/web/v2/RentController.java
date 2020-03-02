package bstu.BI.web.v2;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.v1.RentalTickerService;
import bstu.BI.service.v2.BookService;
import bstu.BI.service.v2.UserService;
import bstu.BI.util.Verificator;
import bstu.BI.web.v1.dto.ListOfOrders;
import bstu.BI.web.v1.dto.UserOperation;
import bstu.BI.web.v1.dto.UserRequisites;
import bstu.BI.web.v1.dto.UserResponse;
import bstu.BI.web.v2.dto.BookService_TransactionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/rent-service/v2")
public class RentController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    RentalTickerService tickerService;

    @PostMapping("/create-order")
    public UserResponse order(@RequestParam UserRequisites requisites,
                              @RequestParam Integer bookTypeId,
                              @RequestParam Date rentalFinish) {
        if (!Verificator.validPeriod(new Date(), rentalFinish))
            return UserResponse.error("Ошибка: период не может быть отрицательным или превышать месяц");
        Optional<BookService_TransactionInfo> infoOptional = bookService.startTransaction(bookTypeId);
        if (infoOptional.isEmpty()) return UserResponse.error("Ошибка: не удалось найти книгу такого типа");
        BookService_TransactionInfo info = infoOptional.get();
        RentalTicket rentalTicket = info.getRentalTicket();
        UserOperation userOperation = userService.transactions(requisites, -rentalTicket.getPurchasePrice());
        if (userOperation.isFail()) return UserResponse.error(userOperation.getExplanation());
        rentalTicket.setBookTypeId(bookTypeId);
        rentalTicket.setRentalFinish(rentalFinish);
        tickerService.save(rentalTicket);
        bookService.finishTransaction(info);
        return UserResponse.success();
    }

    @PostMapping("/refund-of-deposit")
    public UserResponse refundOfDeposit(@RequestParam UserRequisites requisites,
                                        @RequestParam Integer bookTypeId) {
        UserOperation userOperation = userService.getInfo(requisites);
        if (userOperation.isFail()) return UserResponse.error(userOperation.getExplanation());
        Collection<RentalTicket> rentalBooks = tickerService.findByUserId(userOperation.getUserId());
        Optional<RentalTicket> optionalRentalTicket = rentalBooks.stream()
                .filter(x -> x.getBookTypeId().equals(bookTypeId))
                .min(Comparator.comparing(RentalTicket::getRentalFinish));
        if (optionalRentalTicket.isEmpty()) return UserResponse.success();
        RentalTicket rentalTicket = optionalRentalTicket.get();
        userOperation = userService.transactions(requisites, rentalTicket.getPurchasePrice() - rentalTicket.getRentPrice());
        if (userOperation.isFail()) return UserResponse.error(userOperation.getExplanation());
        bookService.bookReturn(rentalTicket.getBookTypeId());
        return UserResponse.success();
    }

    @GetMapping("list-of-orders")
    public ListOfOrders getListOfOrders(@RequestParam UserRequisites requisites,
                                        @RequestParam(required = false) Integer ticketId) {
        UserOperation userOperation = userService.getInfo(requisites);
        if (userOperation.isFail()) return ListOfOrders.error(userOperation.getExplanation());
        if (Optional.ofNullable(ticketId).isEmpty()) {
            Optional<RentalTicket> optionalRentalTicket = tickerService.findById(ticketId);
            if (optionalRentalTicket.isEmpty()) return ListOfOrders.error("Ошибка: не найден заказ с таким id");
            RentalTicket rentalTicket = optionalRentalTicket.get();
            if (!rentalTicket.getUserId().equals(ticketId))
                return ListOfOrders.error("Ошибка: не найден заказ с таким id");
            else
                return ListOfOrders.success(rentalTicket);
        }
        Collection<RentalTicket> rentalTickets = tickerService.findByUserId(userOperation.getUserId());
        return ListOfOrders.success(rentalTickets);
    }
}
