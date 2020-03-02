package bstu.BI.service.v1.imp.v1;

import bstu.BI.service.v1.UserService;
import bstu.BI.web.v1.dto.UserOperation;
import bstu.BI.web.v1.dto.UserRequisites;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImp implements UserService {
    @Override
    public UserOperation getUserId(UserRequisites requisites) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserOperation> responseEntity =
                restTemplate.getForEntity("api/books", UserOperation.class, requisites);
        return responseEntity.getBody();
    }

    @Override
    public UserOperation withdrawingMoneyFromAnAccount(UserRequisites requisites, Double _cost) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserOperation> responseEntity =
                restTemplate.postForEntity("api/books", new Object() {
                    UserRequisites userRequisites = requisites;
                    Double cost = -_cost;
                }, UserOperation.class);
        return responseEntity.getBody();
    }

    @Override
    public UserOperation returnOfMoneyToAccount(Integer _userId, Double _cost) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserOperation> responseEntity =
                restTemplate.postForEntity("api/books", new Object() {
                    Integer userId = _userId;
                    Double cost = _cost;
                }, UserOperation.class);
        return responseEntity.getBody();
    }
}
