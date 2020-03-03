package bstu.BI.web;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.BookService;
import bstu.BI.service.RentalTickerService;
import bstu.BI.service.UserService;
import bstu.BI.util.Verificator;
import bstu.BI.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public UserResponse createOrder(@RequestBody @Valid DTO_Rent_CreateOrder data) {
        UserRequisites requisites = data.getRequisites();
        Integer bookTypeId = data.getBookTypeId();
        Date rentalFinish = data.getRentalFinish();
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
        rentalTicket.setUserId(userOperation.getUserId());
        tickerService.save(rentalTicket);
        bookService.finishTransaction(info);
        return UserResponse.success();
    }

    @PostMapping("/refund-of-deposit")
    public UserResponse refundOfDeposit(@RequestBody @Valid DTO_Rent_RefundOfDeposit data) {
        UserRequisites requisites = data.getRequisites();
        Integer bookTypeId = data.getBookTypeId();
        UserOperation userOperation = userService.getInfo(requisites.getUsername());
        if (userOperation.isFail()) return UserResponse.error(userOperation.getExplanation());
        Collection<RentalTicket> rentalBooks = tickerService.findByUserId(userOperation.getUserId());
        Optional<RentalTicket> optionalRentalTicket = rentalBooks.stream()
                .filter(x -> x.getBookTypeId().equals(bookTypeId))
                .min(Comparator.comparing(RentalTicket::getRentalFinish));
        if (optionalRentalTicket.isEmpty()) return UserResponse.error("Ошибка: не найден заказ с такой книгой");
        RentalTicket rentalTicket = optionalRentalTicket.get();
        userOperation = userService.transactions(requisites, rentalTicket.getPurchasePrice() - rentalTicket.getRentPrice());
        if (userOperation.isFail()) return UserResponse.error(userOperation.getExplanation());
        bookService.bookReturn(rentalTicket.getBookTypeId());
        tickerService.delete(rentalTicket);
        return UserResponse.success();
    }

    @GetMapping("list-of-orders")
    public ListOfOrders getListOfOrders(@RequestParam String username,
                                        @RequestParam(required = false) Integer ticketId) {
        UserOperation userOperation = userService.getInfo(username);
        if (userOperation.isFail()) return ListOfOrders.error(userOperation.getExplanation());
        if (Optional.ofNullable(ticketId).isPresent()) {
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
