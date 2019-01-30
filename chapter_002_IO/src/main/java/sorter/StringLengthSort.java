package sorter;

import com.google.common.base.Joiner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StringLengthSort implements ExternalSort {
    private final int portion;
    private int index = 0;
    private String outPath;

    public StringLengthSort(int portion) {
        this.portion = portion;
    }

    public void sort(File source, File dist) throws Exception {
        ArrayList<String> strings = new ArrayList<>(portion);
        outPath = dist.getParent() + System.getProperty("file.separator");
        String leftFilename = outPath + "left.tmp";
        String rightFilename = outPath + "right.tmp";
        String outFilename = outPath + "out.tmp";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(source.getPath()))) {
            String line = br.readLine();
            Files.createFile(Paths.get(leftFilename));
            while (line != null) {
                while (index < portion && line != null) {
                    strings.add(line);
                    index++;
                    line = br.readLine();
                }
                Collections.sort(strings, Comparator.comparingInt(String::length));
                try (FileWriter rightFileWriter = new FileWriter(rightFilename)) {
                    rightFileWriter.write(Joiner.on("\n").join(strings));
                }
                mergeFiles(leftFilename, rightFilename, outFilename);
                Files.deleteIfExists(Paths.get(leftFilename));
                Files.deleteIfExists(Paths.get(rightFilename));
                Files.move(Paths.get(outFilename), Paths.get(leftFilename));
                index = 0;
                strings = new ArrayList<>();
            }
        }
        Files.move(Paths.get(leftFilename), Paths.get(dist.getPath()));
    }

    private void mergeFiles(String leftFile, String rightFile, String outFile) throws IOException {
        if (Files.exists(Paths.get(leftFile)) && Files.exists(Paths.get(rightFile))) {
            boolean finish = false;
            try (BufferedReader left = Files.newBufferedReader(Paths.get(leftFile));
                 BufferedReader right = Files.newBufferedReader(Paths.get(rightFile));
                 BufferedWriter out = Files.newBufferedWriter(Paths.get(outFile))) {
                String leftLine = left.readLine();
                String rightLine = right.readLine();
                boolean fileEmpty = true;
                while (!finish) {
                    if (leftLine == null && rightLine == null) {
                        finish = true;
                        continue;
                    }
                    if (!fileEmpty) {
                        out.newLine();
                    }
                    if (leftLine == null) {
                        out.write(rightLine);
                        rightLine = right.readLine();
                    } else if (rightLine == null) {
                        out.write(leftLine);
                        leftLine = left.readLine();
                    } else {
                        if (leftLine.length() <= rightLine.length()) {
                            out.write(leftLine);
                            leftLine = left.readLine();
                        } else {
                            out.write(rightLine);
                            rightLine = right.readLine();
                        }
                    }
                    fileEmpty = false;
                }
            }
        }
    }
}
