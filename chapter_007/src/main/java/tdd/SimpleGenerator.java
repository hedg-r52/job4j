package tdd;

import java.util.List;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 23.03.2018
 */
public class SimpleGenerator implements Template {
    /**
     * generate text
     * @param template
     * @param data
     * @return
     */
    @Override
    public String generate(String template, List<Pair> data) throws Exception {
        String result = template;
        for (Pair d : data) {
                String keyRegEx = String.format("\\$\\{%s\\}", d.getKey());
                String key = String.format("${%s}", d.getKey());
                String value = d.getValue();
                if (result.indexOf(key) == -1) {
                    throw new Exception("Too many parameters.");
                }
                result = result.replaceAll(keyRegEx, value);
        }
        if (result.matches(".*\\$\\{.*\\}.*")) {
            throw new Exception("Not enough parameters.");
        }
        return result;
    }
}
