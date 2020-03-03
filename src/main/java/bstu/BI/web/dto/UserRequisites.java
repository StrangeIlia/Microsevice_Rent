package bstu.BI.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
@EqualsAndHashCode
public class UserRequisites {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
