package pack;

import org.apache.commons.cli.*;
import ru.job4j.utils.fs.Search;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Pack {
    private final String dir;
    private final List<String> exts;
    private final String output;

    public Pack(String dir, List<String> exts, String output) {
        this.dir = dir;
        this.exts = exts;
        this.output = output;
    }

    public void exec() throws IOException {
        Search search = new Search();
        List<File> files = search.files(dir, exts);
        if (files.size() != 0) {
            try (FileOutputStream fos = new FileOutputStream(output);
                 ZipOutputStream zout = new ZipOutputStream(fos)) {
                for (File f : files) {
                    try (FileInputStream fis = new FileInputStream(f)) {
                        String entryName = f.getPath().substring(dir.length());
                        ZipEntry entry = new ZipEntry(entryName);
                        zout.putNextEntry(entry);
                        byte[] buffer = new byte[fis.available()];
                        fis.read(buffer);
                        zout.write(buffer);
                        zout.closeEntry();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws ParseException, IOException {
        Option optionDirectory = new Option("d", true, "catalog for archive");
        Option optionExtensions = new Option("e", true, "list of extensions to be archived");
        Option optionOutputFile = new Option("o", true, "output file");
        Options options = new Options();
        options.addOption(optionDirectory);
        options.addOption(optionExtensions);
        options.addOption(optionOutputFile);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        HelpFormatter formatter = new HelpFormatter();
        String execString = "pack.jar -d <dir> -e <ext1,ext2,...> -o <outputfile>";
        String dir = cmd.getOptionValue("d");
        ArrayList<String> exts = new ArrayList<>();
        String output = cmd.getOptionValue("o");
        if (!cmd.hasOption("d") || Files.notExists(Paths.get(dir))) {
            System.out.println("Directory not exists");
            formatter.printHelp(execString, options);
        } else if (!cmd.hasOption("e")) {
            System.out.println("No extension specified");
            formatter.printHelp(execString, options);
        } else if (!cmd.hasOption("o") || output.length() == 0) {
            System.out.println("No output specified");
            formatter.printHelp(execString, options);
        } else {
            exts.addAll(Arrays.asList(cmd.getOptionValue("e").split(",")));
            Pack pack = new Pack(dir, exts, output);
            pack.exec();
        }
    }
}
