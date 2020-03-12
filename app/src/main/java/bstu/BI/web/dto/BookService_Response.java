package bstu.BI.web.dto;

import bstu.BI.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookService_Response {
    private Status status;
    private Double rentPrice;
    private Double purchasePrice;

    public boolean isFail() {
        return status.equals(Status.FAIL);
    }

    public boolean isSuccess() {
        return status.equals(Status.SUCCESS);
    }
}
