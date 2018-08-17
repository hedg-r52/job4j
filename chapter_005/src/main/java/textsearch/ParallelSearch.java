package textsearch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@ThreadSafe
public class ParallelSearch {
    private final String root;
    private final String text;
    private final List<String> extensions;

    private volatile boolean finish = false;

    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();

    @GuardedBy("this")
    private final Set<String> paths = new LinkedHashSet<>();

    class SearchThread extends Thread {
        @Override
        public void run() {
            Path rootPath = Paths.get(root);
            try {
                Files.walk(rootPath)
                        .filter(p -> !Files.isDirectory(p)
                                && extensions.contains(FilenameUtils.getExtension(p.toString())))
                        .forEach(p -> files.offer(p.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish = true;
        }
    }

    class ReadThread extends Thread {
        @Override
        public void run() {
            while (true) {
                String file = "";
                synchronized ("this") {
                    file = files.poll();
                }
                if (file == null && finish) {
                    break;
                }
                if (file != null) {
                    String s;
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        while ((s = reader.readLine()) != null) {
                            if (s.contains(text)) {
                                paths.add(file);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public ParallelSearch(String root, String text, List<String> extensions) {
        this.root = root;
        this.text = text;
        this.extensions = extensions;
    }

    public void init() {
        Thread search = new SearchThread();
        Thread read1 = new ReadThread();
        Thread read2 = new ReadThread();
        search.start();
        read1.start();
        read2.start();
        while (!finish) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            read1.join();
            read2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized Set<String> result() {
        return this.paths;
    }
}
