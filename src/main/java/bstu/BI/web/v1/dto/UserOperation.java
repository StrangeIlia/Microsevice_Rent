package bstu.BI.web.v1.dto;

import bstu.BI.entity.enums.Status;
import lombok.Data;

@Data
//@Getter
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
