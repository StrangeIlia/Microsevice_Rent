package bstu.BI.service.imp;

import bstu.BI.service.ExternalUserService;
import bstu.BI.service.UserService;
import bstu.BI.web.dto.user_service.DTO_UserService_Transaction;
import bstu.BI.web.dto.user_service.DTO_UserService_UserRequisites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    ExternalUserService userService;

    @Override
    public Optional<Long> getInfo(String username) {
        ResponseEntity<Long> responseEntity = userService.getInfo(username);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK))
            return Optional.ofNullable(responseEntity.getBody());
        else
            return Optional.empty();
    }

    @Override
    public Optional<Long> transactions(DTO_UserService_UserRequisites requisites, Double cost) {
        DTO_UserService_Transaction data = new DTO_UserService_Transaction();
        data.setRequisites(requisites);
        data.setCost(cost);
        ResponseEntity<Long> responseEntity = userService.transactions(data);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK))
            return Optional.ofNullable(responseEntity.getBody());
        else
            return Optional.empty();
    }
}
