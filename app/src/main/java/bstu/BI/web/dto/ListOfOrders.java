package bstu.BI.web.dto;

import bstu.BI.entity.domain.RentalTicket;
import bstu.BI.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class ListOfOrders {
    private Status status;
    private String explanation;
    private Collection<RentalTicket> orders;

    ListOfOrders(Collection<RentalTicket> orders) {
        this.status = Status.SUCCESS;
        this.orders = orders;
    }

    ListOfOrders(String explanation) {
        this.status = Status.FAIL;
        this.explanation = explanation;
    }

    @JsonIgnore
    public static ListOfOrders success(RentalTicket order) {
        List<RentalTicket> list = new ArrayList<>();
        list.add(order);
        return success(list);
    }

    @JsonIgnore
    public static ListOfOrders success(Collection<RentalTicket> orders) {
        return new ListOfOrders(orders);
    }

    @JsonIgnore
    public static ListOfOrders error(String explanation) {
        return new ListOfOrders(explanation);
    }
}
