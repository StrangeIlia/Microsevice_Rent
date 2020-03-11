package bstu.BI.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ServiceResponse {
    HttpStatus status;

    public boolean isFail() {
        return !status.equals(HttpStatus.OK);
    }

    public boolean isSuccess() {
        return status.equals(HttpStatus.OK);
    }

    static public ServiceResponse create(HttpStatus status) {
        return new ServiceResponse(status);
    }
}
