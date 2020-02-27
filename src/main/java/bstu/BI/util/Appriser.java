package bstu.BI.util;

import bstu.BI.entity.domain.RentalTicket;

import java.util.Collection;
import java.util.Date;

public class Appriser {
    public static double getCostReturnedBooks(Collection<RentalTicket> returnedBooks) {
        double cost = 0;
        for (RentalTicket ticket : returnedBooks)
            cost += ticket.getRentPrice() - ticket.getPurchasePrice();
        return cost;
    }

    public static double getCostRent(Date rentalPeriod, Double cost) {
        long days = rentalPeriod.getTime() / (24 * 60 * 60 * 1000);
        return cost / 30 * days;
    }
}
