package fs;

import com.google.common.base.Joiner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Search {

    public List<File> files(String parent, List<String> exts) {
        ArrayList<File> result = new ArrayList<>();
        File rootFile = new File(parent);
        String matchString = ".*\\.[" + Joiner.on("|").join(exts) + "]+";
        for (File file : rootFile.listFiles()) {
            if (file.isDirectory()) {
                for (File f : this.files(file.getPath(), exts)) {
                    result.add(f);
                }
            } else {
                if (file.getName().matches(matchString)) {
                    result.add(file);
                }
            }
        }
        return result;
    }
}
