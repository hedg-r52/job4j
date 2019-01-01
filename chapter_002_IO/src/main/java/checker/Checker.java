package checker;

import java.io.*;
import org.apache.log4j.*;

public class Checker {
    private final static Logger LOG = LogManager.getLogger(Checker.class);

    public boolean isEvenNumber(InputStream in) {
        boolean result = false;
        try (InputStreamReader isr = new InputStreamReader(in);
                BufferedReader br  = new BufferedReader(isr)) {
            String line = br.readLine();
            result = Integer.valueOf(line) % 2 == 0;
        } catch (IOException e) {
            LOG.warn(e.toString());
        }
        return result;
    }

}
