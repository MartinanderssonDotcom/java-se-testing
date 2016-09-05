package martinandersson.com.jdk8time;

import static java.lang.System.out;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class PeriodTest
{
    public static void main(String... ignored) {
        // End is "exclusive", so what's the period between these two dates?
        LocalDate a = LocalDate.parse("2015-01-01"),
                  b = LocalDate.parse("2015-01-02");
        
        // A: P1D
        out.println(Period.between(a, b));
        
        // From day 1 until day 1 is 0:
        out.println(a.until(a));
        out.println(a.until(LocalDate.parse("2015-01-01")));
        
        // Both is true:
        out.println(a.until(a).equals(Period.ZERO));
        out.println(a.until(LocalDate.parse("2015-01-01")) == Period.ZERO);
        
        // Period of more than 365 days may still report to be 0 years, even if normalized:
        // ("normalization" doesn't bother shit about number of days)
        out.println(Period.ofDays(999).getYears());
        out.println(Period.ofDays(999).normalized().getYears());
    }
}