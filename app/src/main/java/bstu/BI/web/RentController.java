package bstu.BI.web;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.BookService;
import bstu.BI.service.RentalTickerService;
import bstu.BI.service.UserService;
import bstu.BI.util.Verificator;
import bstu.BI.web.dto.rent_service.*;
import bstu.BI.web.dto.user_service.DTO_UserService_UserRequisites;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/rent-service")
public class RentController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    RentalTickerService tickerService;

    Logger logger = LoggerFactory.getLogger(RentController.class);

    @PutMapping("/create-order")
    public ResponseEntity createOrder(@RequestBody @Valid DTO_RentService_CreateOrder data) {
        DTO_UserService_UserRequisites requisites = data.getRequisites();
        Long bookTypeId = data.getBookTypeId();

        Date rentalFinish = data.getRentalFinish();
        if (!Verificator.validPeriod(new Date(), rentalFinish)){
            logger.warn("Ошибка: пользователь попытался ввести неверную дату");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<RentalTicket> infoOptional = bookService.buyOne(bookTypeId);
        if (infoOptional.isEmpty()) {
            logger.warn("Ошибка: пользователь попытался ввести книгу с несуществующим id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        RentalTicket rentalTicket = infoOptional.get();
        Optional<Long> optionalId = userService.transactions(requisites, -rentalTicket.getRentAndDepositCost());
        if (optionalId.isEmpty()) {
            logger.warn("Ошибка: не удалось совершить транзакцию с сервисом пользователей");
            bookService.returnOne(bookTypeId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        rentalTicket.setBookTypeId(bookTypeId);
        rentalTicket.setRentalFinish(rentalFinish);
        rentalTicket.setUserId(optionalId.get());
        tickerService.save(rentalTicket);

        logger.info(
                "Пользователь с id = " + rentalTicket.getUserId()
                + " арендовал книгу с id = " + bookTypeId
                + " до " + rentalFinish
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refund-of-deposit")
    public ResponseEntity refundOfDeposit(@RequestBody @Valid DTO_RentService_RefundOfDeposit data) {
        DTO_UserService_UserRequisites requisites = data.getRequisites();
        Long bookTypeId = data.getBookTypeId();
        Optional<Long> optionalId = userService.getInfo(requisites.getUsername());
        if (optionalId.isEmpty()){
            logger.warn("Ошибка: не удалось найти пользователя по реквизитам");
            return ResponseEntity.notFound().build();
        }

        Optional<RentalTicket> optionalRentalTicket = tickerService.findAndRemoveOld(optionalId.get(), bookTypeId);
        if (optionalRentalTicket.isEmpty()) {
            logger.warn("Ошибка: пользователь попытался ввести книгу с несуществующим id");
            return ResponseEntity.notFound().build();
        }

        RentalTicket rentalTicket = optionalRentalTicket.get();
        optionalId = userService.transactions(requisites, rentalTicket.getDeposit());
        if (optionalId.isEmpty()) {
            logger.warn("Ошибка: не удалось вернуть залог");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        bookService.returnOne(rentalTicket.getBookTypeId());
        tickerService.delete(rentalTicket);

        logger.info(
                "Пользователю с id = " + rentalTicket.getUserId()
                + " успешно вернули залог в размере " + rentalTicket.getDeposit()
                + " за книгу с типом id = " + rentalTicket.getBookTypeId()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list-of-orders")
    public ResponseEntity<Collection<RentalTicket>> getListOfOrders(@RequestParam String username)
    {
        Optional<Long> optionalId = userService.getInfo(username);
        if (optionalId.isEmpty()) {
            logger.warn("Ошибка: не удалось найти пользователя по реквизитам");
            return ResponseEntity.notFound().build();
        }
        Collection<RentalTicket> rentalTickets = tickerService.findByUserId(optionalId.get());
        logger.info("Произведен запрос списка заказов пользователя с id = " + optionalId.get());
        return ResponseEntity.ok(rentalTickets);
    }

    @GetMapping("/order")
    public ResponseEntity<RentalTicket> getListOfOrders(@RequestParam("id") Integer ticketId) {
        Optional<RentalTicket> optionalRentalTicket = tickerService.findById(ticketId);
        if (optionalRentalTicket.isEmpty()) {
            logger.warn("Ошибка: не найден заказ с таким id");
            return ResponseEntity.notFound().build();
        }

        RentalTicket rentalTicket = optionalRentalTicket.get();
        logger.info("Произведен запрос информации по заказу с id = " + rentalTicket.getId());
        return ResponseEntity.ok(rentalTicket);
    }
}
