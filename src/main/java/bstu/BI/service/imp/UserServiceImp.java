package bstu.BI.service.imp;

import bstu.BI.service.ExternalUserService;
import bstu.BI.service.UserService;
import bstu.BI.web.dto.DTO_UserOperation;
import bstu.BI.web.dto.DTO_UserService_Transaction;
import bstu.BI.web.dto.UserRequisites;
import bstu.BI.web.dto.UserServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {
    @Autowired
    ExternalUserService userService;

    @Override
    public UserServiceResponse getInfo(String username) {
        ResponseEntity<DTO_UserOperation> operation = userService.getInfo(username);
        return UserServiceResponse.create(operation.getStatusCode(), operation.getBody());
    }

    @Override
    public UserServiceResponse transactions(UserRequisites requisites, Double cost) {
        DTO_UserService_Transaction data = new DTO_UserService_Transaction();
        data.setRequisites(requisites);
        data.setCost(cost);

        try {
            ResponseEntity<DTO_UserOperation> operation = userService.transactions(data);
            return UserServiceResponse.create(operation.getStatusCode(), operation.getBody());
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
            logger.warn(e.getMessage());
        } finally {
            return null;
        }

    }
}
