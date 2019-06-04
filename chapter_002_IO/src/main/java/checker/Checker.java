package checker;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import org.apache.log4j.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Checker {
    private final static Logger LOG = LogManager.getLogger(Checker.class);

    /**
     * Метод проверяет что в байтовом потоке записано четное число
     * @param in входной поток
     * @return true если число четное, false - нечетное
     */
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

    /**
     * Удаление запрещенных слов
     * @param in входной поток
     * @param out выходной поток
     * @param abuse массив запрещенных слов
     */
    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) throws IOException {
        byte[] buffer = new byte[1024];
        while (in.read(buffer) != -1) {
            List<String> words = Arrays.asList((new String(buffer)).split(" "));
            words = words.stream().filter(p -> !Arrays.asList(abuse).contains(p)).collect(Collectors.toList());
            out.write(
                    Joiner.on(" ")
                            .join(words.toArray())
                            .getBytes()
            );
        }
    }

}
