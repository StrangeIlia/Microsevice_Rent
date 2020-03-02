package bstu.BI.web.v2.dto;

import bstu.BI.entity.domain.RentalTicket;
import lombok.Data;

@Data
public class BookService_TransactionInfo {
    RentalTicket rentalTicket;
    Integer transactionId;
}
