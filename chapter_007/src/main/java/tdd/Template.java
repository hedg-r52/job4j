package tdd;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 23.03.2018
 */
public interface Template {
    /**
     * generate text
     * @param template
     * @param data
     * @return
     */
    String generate(String template, Object[] data) throws Exception;
}
