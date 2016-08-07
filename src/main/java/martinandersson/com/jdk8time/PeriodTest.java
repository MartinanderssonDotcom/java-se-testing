package martinandersson.com.jdk8time;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class PeriodTest
{
    public static void main(String... ignored) {
        LocalDate a = LocalDate.parse("2015-01-01"),
                  b = LocalDate.parse("2015-01-02");
        
        // End is "exclusive", so what's the period between these two dates?
        // A: P1D
        System.out.println(Period.between(a, b));
        
        // From day 1 until day 1 is 0:
        System.out.println(a.until(a));
        System.out.println(a.until(LocalDate.parse("2015-01-01")));
        
        // Both is true:
        System.out.println(a.until(a).equals(Period.ZERO));
        System.out.println(a.until(LocalDate.parse("2015-01-01")) == Period.ZERO);
        
        // Period of more than 365 days may still report to be 0 years, even if normalized:
        // ("normalization" doesn't bother shit about number of days)
        System.out.println(Period.ofDays(400).getYears());
        System.out.println(Period.ofDays(400).normalized().getYears());
    }
}