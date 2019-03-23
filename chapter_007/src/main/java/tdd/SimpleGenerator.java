package tdd;

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
    public String generate(String template, Object[] data) throws Exception {
        String result = template;
        for (Object o : data) {
            if (o instanceof Pair) {
                String keyRegEx = String.format("\\$\\{%s\\}", ((Pair) o).getKey());
                String key = String.format("${%s}", ((Pair) o).getKey());
                String value = ((Pair) o).getValue();
                if (result.indexOf(key) == -1) {
                    throw new Exception();
                }
                result = result.replaceAll(keyRegEx, value);
            }
        }
        return result;
    }
}
