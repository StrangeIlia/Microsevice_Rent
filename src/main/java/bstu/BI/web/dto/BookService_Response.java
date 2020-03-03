package bstu.BI.web.dto;

import bstu.BI.entity.enums.Status;
import lombok.Data;

//@Getter
@Data
public class BookService_Response {
    private Status status;
    private Double rentPrice;
    private Double purchasePrice;
    private Integer transactionId;

    public boolean isFail() {
        return status.equals(Status.FAIL);
    }

    public boolean isSuccess() {
        return status.equals(Status.SUCCESS);
    }
}
