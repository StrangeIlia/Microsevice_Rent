package bstu.BI.web.v1.dto;

import bstu.BI.entity.enums.Status;
import lombok.Getter;

@Getter
public class BookRentInfo {
    Status status;
    Integer id;
    private Double rentPrice;
    private Double purchasePrice;

    public boolean isFail() {
        return status == Status.FAIL;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }
}
