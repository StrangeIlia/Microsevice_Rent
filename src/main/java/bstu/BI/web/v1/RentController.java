package bstu.BI.web.v1;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.service.v1.BookService;
import bstu.BI.service.v1.RentalTickerService;
import bstu.BI.service.v1.UserService;
import bstu.BI.util.Appriser;
import bstu.BI.web.v1.dto.ListOfOrders;
import bstu.BI.web.v1.dto.UserOperation;
import bstu.BI.web.v1.dto.UserRequisites;
import bstu.BI.web.v1.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/rent/v1")
public class RentController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    RentalTickerService tickerService;

    @PostMapping("/order")
    public UserResponse rentBook(@RequestParam UserRequisites requisites,
                                 @RequestParam Integer bookTypeId,
                                 @RequestParam Date rentalPeriod) {
        Optional<RentalTicket> optionalRentalTicket = bookService.startTransaction(bookTypeId);
        if (optionalRentalTicket.isEmpty())
            return UserResponse.error("Ошибка: не удалось найти книгу такого типа");
        RentalTicket rentalTicket = optionalRentalTicket.get();
        UserOperation transaction = userService.withdrawingMoneyFromAnAccount(requisites, rentalTicket.getPurchasePrice());
        if (transaction.isFail())
            return UserResponse.error(transaction.getExplanation());
        rentalTicket.setUserId(transaction.getUserId());
        rentalTicket.setRentalFinish(rentalPeriod);
        tickerService.save(rentalTicket);
        bookService.finishTransaction(rentalTicket);
        return UserResponse.success();
    }

    @PostMapping("/refund_of_deposit")
    public UserResponse refundOfDeposit(UserRequisites requisites) {
        UserOperation user = userService.getUserId(requisites);
        if (user.isFail())
            return UserResponse.error(user.getExplanation());
        Collection<RentalTicket> rentalBooks = tickerService.findByUserId(user.getUserId());
        if (rentalBooks.isEmpty()) return UserResponse.success();
        Collection<RentalTicket> returnedBooks = bookService.getReturnedBooks(rentalBooks);
        UserOperation returnMoney = userService.returnOfMoneyToAccount(user.getUserId(), Appriser.getCostReturnedBooks(returnedBooks));
        if (returnMoney.isFail())
            return UserResponse.error(user.getExplanation());
        else {
            bookService.returnBooks(rentalBooks);
            return UserResponse.success();
        }
    }

    @GetMapping("list_of_orders")
    public ListOfOrders getListOfOrders(UserRequisites requisites, Integer ticketId) {
        UserOperation user = userService.getUserId(requisites);
        if (user.isFail())
            return ListOfOrders.error(user.getExplanation());
        if (ticketId != null) {
            Optional<RentalTicket> optionalRentalTicket = tickerService.findById(ticketId);
            if (optionalRentalTicket.isEmpty())
                return ListOfOrders.error("Не найден заказ с таким id");
            else
                return ListOfOrders.success(optionalRentalTicket.get());
        } else {
            Collection<RentalTicket> rentalTickets = tickerService.findByUserId(user.getUserId());
            return ListOfOrders.success(rentalTickets);
        }
    }
}
