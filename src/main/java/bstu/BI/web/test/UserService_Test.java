package bstu.BI.web.test;

import bstu.BI.web.dto.DTO_UserOperation;
import bstu.BI.web.dto.DTO_UserService_Transaction;
import bstu.BI.web.dto.UserRequisites;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("api/test/user-service")
public class UserService_Test {
    HashMap<String, Long> userRequisites = null;

    private void init() {
        if (Optional.ofNullable(userRequisites).isEmpty()) {
            userRequisites = new HashMap<>(3);
            userRequisites.put("Пользователь", 1L);
            userRequisites.put("Админ", 2L);
            userRequisites.put("Гость", 3L);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<DTO_UserOperation> getInfo(@RequestParam String username) {
        this.init();
        Long id = userRequisites.get(username);
        DTO_UserOperation userOperation = new DTO_UserOperation();
        if (Optional.ofNullable(id).isEmpty()) {
            userOperation.setExplanation("Нет пользователя с таким логином");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(userOperation);
        } else {
            userOperation.setUserId(id);
            return ResponseEntity
                    .ok(userOperation);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<DTO_UserOperation> transactions(@RequestBody @Valid DTO_UserService_Transaction data) {
        UserRequisites requisites = data.getRequisites();
        this.init();
        Long id = userRequisites.get(requisites.getUsername());
        DTO_UserOperation userOperation = new DTO_UserOperation();
        if (Optional.ofNullable(id).isEmpty()) {
            userOperation.setExplanation("Нет пользователя с таким логином");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(userOperation);
        } else {
            userOperation.setUserId(id);
            return ResponseEntity
                    .ok(userOperation);
        }
    }
}
