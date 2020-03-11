package bstu.BI.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
//@Getter
public class DTO_UserOperation {
    @NotNull
    private Long userId;
    private String explanation;

    public boolean isFail() {
        return explanation.isEmpty();
    }

    public boolean isSuccess() {
        return !explanation.isEmpty();
    }
}
