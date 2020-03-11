package bstu.BI.web.dto;

import bstu.BI.entity.domain.RentalTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookServiceResponse extends ServiceResponse {
    RentalTicket rentalTicket;

    static public BookServiceResponse create(HttpStatus status) {
        return create(status, null);
    }

    static public BookServiceResponse create(RentalTicket ticket) {
        return create(HttpStatus.OK, ticket);
    }

    static public BookServiceResponse create(HttpStatus status, RentalTicket ticket) {
        BookServiceResponse response = new BookServiceResponse();
        response.setStatus(status);
        response.setRentalTicket(ticket);
        return response;
    }
}
