package bstu.BI.service;

import bstu.BI.web.dto.user_service.DTO_UserService_Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@FeignClient(name = "user-service", url = "${user_service}")
public interface ExternalUserService {
    @GetMapping("/info")
    ResponseEntity<Long> getInfo(@RequestParam String username);

    @PutMapping("/transactions")
    ResponseEntity<Long> transactions(@RequestBody @Valid DTO_UserService_Transaction data);
}
