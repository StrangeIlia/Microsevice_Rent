package bstu.BI.web.dto;

import bstu.BI.entity.enums.Status;
import lombok.Getter;

@Getter
public class UserOperation {
    private Status status;
    private Integer userId;
    private String explanation;

    public boolean isFail() {
        return status == Status.FAIL;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }
}
