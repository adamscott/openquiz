package ca.openquiz.comms.util;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

import org.codehaus.jackson.io.NumberInput;
import org.codehaus.jackson.map.util.StdDateFormat;

public class PatchedStdDateFormat extends StdDateFormat {

    @Override
    public Date parse(String dateStr, ParsePosition pos)
    {
        int i = dateStr.length();
        // if the date string starts with a minus
        if (i > 0 && dateStr.charAt(0) == '-') {

            // remove the minus and reduce the length
            String posDateStr = dateStr.substring(1);
            --i;

            // check that the rest of the date string is numeric
            while (--i >= 0) {
                char ch = posDateStr.charAt(i);
                if (ch < '0' || ch > '9') break;
            }
            if (i < 0) { // all digits
                if (NumberInput.inLongRange(posDateStr, true)) {
                    return new Date(Long.parseLong(dateStr));
                }
            }
        }
        return super.parse(dateStr, pos);
    }

    @Override
    public PatchedStdDateFormat clone() {
        /* Although there is that much state to share, we do need to
         * orchestrate a bit, mostly since timezones may be changed
         */
        return new PatchedStdDateFormat();
    }
    
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo,
    		FieldPosition fieldPosition) {
    	return toAppendTo.append(date.getTime());
    }
}