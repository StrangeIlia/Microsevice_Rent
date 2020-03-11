package bstu.BI.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserServiceResponse extends ServiceResponse {
    private Long userId;
    private String explanation;

    public static UserServiceResponse create(HttpStatus status, DTO_UserOperation operation) {
        UserServiceResponse response = new UserServiceResponse();
        response.setStatus(status);
        if (operation != null) {
            response.setUserId(operation.getUserId());
            response.setExplanation(operation.getExplanation());
        }
        return response;
    }
}
