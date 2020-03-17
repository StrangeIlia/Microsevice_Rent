package bstu.BI.web.test;

import bstu.BI.web.dto.user_service.DTO_UserService_Transaction;
import bstu.BI.web.dto.user_service.DTO_UserService_UserRequisites;
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
    public ResponseEntity<Long> getInfo(@RequestParam String username) {
        this.init();
        try {
            return ResponseEntity.ok(userRequisites.get(username));
        } catch (Exception ignored) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/transactions")
    public ResponseEntity<Long> transactions(@RequestBody @Valid DTO_UserService_Transaction data) {
        DTO_UserService_UserRequisites requisites = data.getRequisites();
        Double cost = data.getCost();
        try {
            return ResponseEntity.ok(userRequisites.get(requisites.getUsername()));
        } catch (Exception ignored) {
            return ResponseEntity.notFound().build();
        }
    }
}
