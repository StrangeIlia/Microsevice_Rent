package bstu.BI.web;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.BookService;
import bstu.BI.service.RentalTickerService;
import bstu.BI.service.UserService;
import bstu.BI.util.Verificator;
import bstu.BI.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserResponse> createOrder(@RequestBody @Valid DTO_Rent_CreateOrder data) {
        UserRequisites requisites = data.getRequisites();
        long bookTypeId = data.getBookTypeId();
        Date rentalFinish = data.getRentalFinish();
        if (!Verificator.validPeriod(new Date(), rentalFinish)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(UserResponse.error("Ошибка: период не может быть отрицательным или превышать месяц"));
        }
        BookServiceResponse response = bookService.rentBook(bookTypeId);
        if (response.isFail()) {
            return ResponseEntity
                    .status(response.getStatus())
                    .body(UserResponse.error("Ошибка: не удалось найти книгу такого типа"));
        }
        RentalTicket rentalTicket = response.getRentalTicket();
        UserServiceResponse userOperation = userService.transactions(requisites, -rentalTicket.getPurchasePrice());
        if (userOperation.isFail()) {
            bookService.returnBook(bookTypeId);
            return ResponseEntity
                    .status(userOperation.getStatus())
                    .body(UserResponse.error(userOperation.getExplanation()));
        }
        rentalTicket.setBookTypeId(bookTypeId);
        rentalTicket.setRentalFinish(rentalFinish);
        rentalTicket.setUserId(userOperation.getUserId());
        tickerService.save(rentalTicket);
        return ResponseEntity.ok(UserResponse.success());
    }

    @PostMapping("/refund-of-deposit")
    public ResponseEntity<UserResponse> refundOfDeposit(@RequestBody @Valid DTO_Rent_RefundOfDeposit data) {
        UserRequisites requisites = data.getRequisites();
        Long bookTypeId = data.getBookTypeId();
        UserServiceResponse userOperation = userService.getInfo(requisites.getUsername());
        if (userOperation.isFail()) {
            return ResponseEntity
                    .status(userOperation.getStatus())
                    .body(UserResponse.error(userOperation.getExplanation()));
        }
        Collection<RentalTicket> rentalBooks = tickerService.findByUserId(userOperation.getUserId());
        RentalTicket[] massive = (RentalTicket[]) rentalBooks.stream()
                .filter(x -> x.getBookTypeId().equals(bookTypeId))
                .sorted(Comparator.comparing(RentalTicket::getRentalFinish))
                .toArray();
        Optional<RentalTicket> optionalRentalTicket = Optional.empty();
        Date currentDate = new Date();
        for (RentalTicket ticket : massive) {
            if (currentDate.compareTo(ticket.getRentalFinish()) <= 0)
                tickerService.delete(ticket);
            else {
                optionalRentalTicket = Optional.of(ticket);
                break;
            }
        }

        if (optionalRentalTicket.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(UserResponse.error("Ошибка: не найден заказ с такой книгой"));
        }

        userOperation = userService.transactions(requisites,
                optionalRentalTicket.get().getPurchasePrice() - optionalRentalTicket.get().getRentPrice());
        if (userOperation.isFail()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(UserResponse.error(userOperation.getExplanation()));
        }
        bookService.returnBook(optionalRentalTicket.get().getBookTypeId());
        tickerService.delete(optionalRentalTicket.get());
        return ResponseEntity.ok(UserResponse.success());
    }

    @GetMapping("list-of-orders")
    public ListOfOrders getListOfOrders(@RequestParam String username,
                                        @RequestParam(required = false) Integer ticketId) {
        UserServiceResponse userOperation = userService.getInfo(username);
        if (userOperation.isFail()) return ListOfOrders.error(userOperation.getExplanation());
        if (Optional.ofNullable(ticketId).isPresent()) {
            Optional<RentalTicket> optionalRentalTicket = tickerService.findById(ticketId);
            if (optionalRentalTicket.isEmpty()) return ListOfOrders.error("Ошибка: не найден заказ с таким id");
            RentalTicket rentalTicket = optionalRentalTicket.get();
            return ListOfOrders.success(rentalTicket);
        }
        Collection<RentalTicket> rentalTickets = tickerService.findByUserId(userOperation.getUserId());
        return ListOfOrders.success(rentalTickets);
    }
}
