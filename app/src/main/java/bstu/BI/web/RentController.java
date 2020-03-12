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
import java.util.*;

@RestController
@RequestMapping("api/rent-service/v2")
public class RentController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    RentalTickerService tickerService;

    @PutMapping("/create-order")
    public UserResponse createOrder(@RequestBody @Valid DTO_Rent_CreateOrder data) {
        UserRequisites requisites = data.getRequisites();
        Long bookTypeId = data.getBookTypeId();
        Date rentalFinish = data.getRentalFinish();
        if (!Verificator.validPeriod(new Date(), rentalFinish))
            return UserResponse.error("Ошибка: период не может быть отрицательным или превышать месяц");
        Optional<RentalTicket> infoOptional = bookService.buyOne(bookTypeId);
        if (infoOptional.isEmpty()) return UserResponse.error("Ошибка: не удалось найти книгу такого типа");
        RentalTicket rentalTicket = infoOptional.get();
        UserOperation userOperation = userService.transactions(requisites, -rentalTicket.getPurchasePrice());
        if (userOperation.isFail()) {
            bookService.returnOne(bookTypeId);
            return UserResponse.error(userOperation.getExplanation());
        }
        rentalTicket.setBookTypeId(bookTypeId);
        rentalTicket.setRentalFinish(rentalFinish);
        rentalTicket.setUserId(userOperation.getUserId());
        tickerService.save(rentalTicket);
        return UserResponse.success();
    }

    @PutMapping("/refund-of-deposit")
    public UserResponse refundOfDeposit(@RequestBody @Valid DTO_Rent_RefundOfDeposit data) {
        UserRequisites requisites = data.getRequisites();
        Long bookTypeId = data.getBookTypeId();
        UserOperation userOperation = userService.getInfo(requisites.getUsername());
        if (userOperation.isFail()) return UserResponse.error(userOperation.getExplanation());
        Collection<RentalTicket> rentalBooks = tickerService.findByUserId(userOperation.getUserId());
        Iterator<RentalTicket> iterator = rentalBooks.stream()
                .filter(x -> x.getBookTypeId().equals(bookTypeId))
                .sorted(Comparator.comparing(RentalTicket::getRentalFinish))
                .iterator();
        Optional<RentalTicket> optionalRentalTicket = Optional.empty();
        Date currentDate = new Date();
        while (iterator.hasNext()) {
            RentalTicket ticket = iterator.next();
            Date finish = ticket.getRentalFinish();
            int result = currentDate.compareTo(finish);
            if (result > 0)
                tickerService.delete(ticket);
            else {
                optionalRentalTicket = Optional.of(ticket);
                break;
            }
        }
        if (optionalRentalTicket.isEmpty()) return UserResponse.error("Ошибка: не найден заказ с такой книгой");
        userOperation = userService.transactions(requisites,
                optionalRentalTicket.get().getPurchasePrice() - optionalRentalTicket.get().getRentPrice());
        if (userOperation.isFail()) return UserResponse.error(userOperation.getExplanation());
        bookService.returnOne(optionalRentalTicket.get().getBookTypeId());
        tickerService.delete(optionalRentalTicket.get());
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
            return ListOfOrders.error("Ошибка: не найден заказ с таким id");
        }
        Collection<RentalTicket> rentalTickets = tickerService.findByUserId(userOperation.getUserId());
        return ListOfOrders.success(rentalTickets);
    }
}