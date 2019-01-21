package sorter;

import com.google.common.base.Joiner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StringLengthSort implements ExternalSort {
    private static final String LN = System.getProperty("line.separator");
    private final int portion;
    private int index = 0;
    private int fileIndex = 0;
    private String outPath;

    public StringLengthSort(int portion) {
        this.portion = portion;
    }

    public void sort(File source, File dist) throws Exception {
        ArrayList<String> strings = new ArrayList<>(portion);
        outPath = dist.getParent() + System.getProperty("file.separator");
        try (RandomAccessFile in = new RandomAccessFile(source, "r")) {
            String line = in.readLine();
            while (line != null) {
                while (index < portion && line != null) {
                    strings.add(line);
                    index++;
                    line = in.readLine();
                }
                Collections.sort(strings, (o1, o2) -> o1.length() - o2.length());
                try (FileWriter temp = new FileWriter(String.format("%s0.%s.tmp", outPath, fileIndex))) {
                    temp.write(Joiner.on("\n").join(strings));
                }
                fileIndex++;
                index = 0;
                strings = new ArrayList<>();
            }
        }
        int level = 0;
        int countFiles = fileIndex;
        int t = fileIndex;
        fileIndex = 0;
        while (t > 0) {
            while (fileIndex * 2 < countFiles) {
                String leftFilename = String.format("%s%s.%s.tmp", outPath, level, fileIndex * 2);
                String rightFilename = String.format("%s%s.%s.tmp", outPath, level, fileIndex * 2 + 1);
                String outFilename;
                if (t == 1) {
                    outFilename = dist.getPath();
                } else {
                    outFilename = String.format("%s%s.%s.tmp", outPath, level + 1, fileIndex);
                }
                if (fileIndex * 2 + 1 < countFiles) {
                    try (BufferedReader left = Files.newBufferedReader(Paths.get(leftFilename));
                         BufferedReader right = Files.newBufferedReader(Paths.get(rightFilename));
                         BufferedWriter bw = Files.newBufferedWriter(Paths.get(outFilename))) {
                        String leftLine = left.readLine();
                        String rightLine = right.readLine();
                        boolean fileEmpty = true;
                        while (true) {
                            if (leftLine == null && rightLine == null) {
                                break;
                            }
                            if (!fileEmpty) {
                                bw.newLine();
                            }
                            if (leftLine == null) {
                                bw.write(rightLine);
                                rightLine = right.readLine();
                            } else if (rightLine == null) {
                                bw.write(leftLine);
                                leftLine = left.readLine();
                            } else {
                                if (leftLine.length() <= rightLine.length()) {
                                    bw.write(leftLine);
                                    leftLine = left.readLine();
                                } else {
                                    bw.write(rightLine);
                                    rightLine = right.readLine();
                                }
                            }
                            fileEmpty = false;
                        }
                    }
                } else {
                    Files.copy(Paths.get(leftFilename), Paths.get(outFilename));
                }
                fileIndex++;
            }
            for (int i = 0; i < countFiles; i++) {
                Files.deleteIfExists(Paths.get(outPath + String.format("%s.%s.tmp", level, i)));
            }
            t = t >> 1;
            countFiles = fileIndex;
            fileIndex = 0;
            level++;
        }
    }
}
