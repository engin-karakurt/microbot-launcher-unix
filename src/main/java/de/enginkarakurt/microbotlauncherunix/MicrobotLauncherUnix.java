package de.enginkarakurt.microbotlauncherunix;

import de.enginkarakurt.microbotlauncherunix.util.FileUtils;

public class MicrobotLauncherUnix {
    public static final String VERSION = "1.2.0";

    public static void main(String[] args) {
       runMicrobotLauncher();
    }

    private static void runMicrobotLauncher() {
        System.out.println("--- microbot-launcher-unix v" + VERSION + " ---\n");

        // Setup Directory
        FileUtils.setupDirectory();

        // Download file
        String fileName = FileUtils.downloadFile();

        // Run file
        FileUtils.runJarFile(fileName);

        System.out.println("\n\nFinished!");
    }
}