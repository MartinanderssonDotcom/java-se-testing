package martinandersson.com.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * The real deal.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class PrettyFormatter extends Formatter
{
    /**
     * {@inheritDoc}
     */
    @Override
    public String format(LogRecord record) {
        String thread = '[' + padLeft(Integer.toString(record.getThreadID()), 3) + "]  ";

        StringBuilder sb = new StringBuilder()
                .append(thread)
                .append(Instant.ofEpochMilli(record.getMillis()).toString())
                .append("  ")
                .append(padRight(record.getLevel().getLocalizedName(), 9));

        String source;

        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();

            if (record.getSourceMethodName() != null) {
               source += " " + record.getSourceMethodName();
            }
        }
        else {
            source = record.getLoggerName();
        }

        sb.append(padRight(source, 53))
          .append(" | ").append(formatMessage(record))
          .append(System.lineSeparator());

        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();

            try (PrintWriter pw = new PrintWriter(sw)) {
                record.getThrown().printStackTrace(pw);
            }

            sb.append(sw)
              .append(System.lineSeparator());
        }

        return sb.toString();
    }
    
    private static String padLeft(String msg, int length) {
        return msg.length() >= length ?
                msg :
                String.format("%1$" + length + "s", msg);
    }
    
    private static String padRight(String msg, int length) {
        return msg.length() >= length ?
                msg :
                String.format("%1$-" + length + "s", msg);
    }
}