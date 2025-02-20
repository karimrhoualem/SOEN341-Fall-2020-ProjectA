package co.tdude.soen341.projecta.WordCountSuperEnterpriseEdition.impl;

import co.tdude.soen341.projecta.WordCountSuperEnterpriseEdition.interfaces.ArgumentParser;
import co.tdude.soen341.projecta.WordCountSuperEnterpriseEdition.interfaces.strategies.WordCountCountStrategy;
import co.tdude.soen341.projecta.WordCountSuperEnterpriseEdition.interfaces.wcoo.IWordCount;

import java.io.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CopyCommand {
    protected static Level level = Level.INFO;
    protected static boolean banner = false;
    protected static String BANNER;
    protected static StringBuilder fileContent;
    private static int EOF = -1;
    private static File srcFile = null;
    private static File dstFile = null;
    private static String srcFilename = "<srcFilename>";
    private static String dstFilename = "<dstFilename>";

    public static void main(String[] args) {
        ArgumentParser argumentParser = new EnterpriseArgumentParser(2);
        argumentParser.addArgument(argumentParser.getArgumentFactory().buildBooleanCommandLineArgument("banner", "Print command information banner"));
        argumentParser.addArgument(argumentParser.getArgumentFactory().buildBooleanCommandLineArgument("verbose", "Print with verbose output"));
        if (!argumentParser.parseArguments(args)) {
            System.out.println("Usage: copy [-?] [-b] [-v] file" );
            return;
        };
        if (argumentParser.getArguments().containsKey("v")) {
            if ((Boolean) argumentParser.getArguments().get("v")) {
                level = Level.FINE;
            }
        }
        if (argumentParser.getArguments().containsKey("b")) {
            if ((Boolean) argumentParser.getArguments().get("b")) {
                banner = true;
            }
        }
        BANNER = "copy Version 1.42b\nCopyright (C) ABC Inc 2020. All Rights Reserved.\nWritten by John Smith\n";

        Logger.getLogger("").setLevel(level);
        for (Handler h : Logger.getLogger("").getHandlers()) {
            h.setLevel(level);
        }
        if (banner) {
            Logger.getLogger("").info(BANNER);
        }

        String fileName1 = argumentParser.getPositional().get(0);
        String fileName2 = argumentParser.getPositional().get(1);
        try {
            if (fileName1 != null) { // Check <src>
                srcFilename = fileName1;
                System.out.println("copy: srcFilename = '" + fileName1 + "'");
                srcFile = new File(fileName1);
                if (!srcFile.canRead()) {
                    Logger.getLogger("").info("copy: Cannot open srcFile '" + fileName1 + "'");
                    return;
                }
            } else {
                Logger.getLogger("").info("copy: [OK] srcFilename = '" + srcFilename + "'");
            }

            if (fileName2 != null) { // Check <dst>
                dstFilename = fileName2;
                dstFile = new File(fileName2);
            } else {
                Logger.getLogger("").info("copy: [OK] dstFilename = '" + fileName2 + "'");
            }

            FileInputStream srcStream = new FileInputStream(srcFile);
            FileOutputStream dstStream = new FileOutputStream(dstFile);

            // Execute the copy.
            int c;

            while ((c = srcStream.read()) != EOF) {
                dstStream.write(c);
                Logger.getLogger("").fine(".");
            }

            // Close and flush all the files.
            srcStream.close();
            dstStream.close();

            Logger.getLogger("").info("copy: done.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
