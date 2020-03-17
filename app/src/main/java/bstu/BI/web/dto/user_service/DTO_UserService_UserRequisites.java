package bstu.BI.web.dto.user_service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
@EqualsAndHashCode
public class DTO_UserService_UserRequisites {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
