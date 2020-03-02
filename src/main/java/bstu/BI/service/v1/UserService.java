package bstu.BI.service.v1;

import bstu.BI.web.v1.dto.UserOperation;
import bstu.BI.web.v1.dto.UserRequisites;

public interface UserService {
    UserOperation getUserId(UserRequisites requisites);

    UserOperation withdrawingMoneyFromAnAccount(UserRequisites requisites, Double cost);

    UserOperation returnOfMoneyToAccount(Integer userId, Double cost);
}
