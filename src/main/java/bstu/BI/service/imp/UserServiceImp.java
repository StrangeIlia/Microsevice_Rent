package bstu.BI.service.imp;

import bstu.BI.service.ExternalUserService;
import bstu.BI.service.UserService;
import bstu.BI.web.dto.DTO_UserService_Transaction;
import bstu.BI.web.dto.UserOperation;
import bstu.BI.web.dto.UserRequisites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    ExternalUserService userService;

    @Override
    public UserOperation getInfo(String username) {
        return userService.getInfo(username);
    }

    @Override
    public UserOperation transactions(UserRequisites requisites, Double cost) {
        DTO_UserService_Transaction data = new DTO_UserService_Transaction();
        data.setRequisites(requisites);
        data.setCost(cost);
        return userService.transactions(data);
    }
}
