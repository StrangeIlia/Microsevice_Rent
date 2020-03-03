package bstu.BI.util;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.web.dto.BookService_Response;
import bstu.BI.web.dto.BookService_TransactionInfo;

public class Converter {
    public static BookService_TransactionInfo convert(BookService_Response response) {
        if (response.isFail()) return null;
        RentalTicket rentalTicket = new RentalTicket();
        rentalTicket.setRentPrice(response.getRentPrice());
        rentalTicket.setPurchasePrice(response.getPurchasePrice());
        BookService_TransactionInfo transactionInfo = new BookService_TransactionInfo();
        transactionInfo.setRentalTicket(rentalTicket);
        transactionInfo.setTransactionId(response.getTransactionId());
        return transactionInfo;
    }
}
