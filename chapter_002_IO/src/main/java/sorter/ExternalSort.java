package sorter;

import java.io.File;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface ExternalSort {
    void sort(File source, File dist) throws Exception;
}

