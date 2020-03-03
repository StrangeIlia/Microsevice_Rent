package bstu.BI.web.dto;

import bstu.BI.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
//@Getter
public class UserOperation {
    private Status status;
    private Integer userId;
    private String explanation;

    @JsonIgnore
    public boolean isFail() {
        return status == Status.FAIL;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }
}
