package to.uk.rem11.mctun.mc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 22.03.2015
 * 11:18
 */
public class EnvironmentVariableReplacer {
    String replace(String source) {
        Pattern patt = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher m = patt.matcher(source);
        StringBuffer sb = new StringBuffer(source.length());
        while (m.find()) {
            String text = m.group(1);
            m.appendReplacement(sb, Matcher.quoteReplacement(System.getenv(text)));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
