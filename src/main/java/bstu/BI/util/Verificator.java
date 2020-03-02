package bstu.BI.util;

import java.util.Date;

public class Verificator {
    public static boolean validPeriod(Date start, Date finish) {
        long long_period = finish.getTime() - start.getTime();
        if (long_period < 0) return false;
        long max_long_period = 31 * 24 * 60 * 60 * 1000;
        return long_period <= max_long_period;
    }
}
