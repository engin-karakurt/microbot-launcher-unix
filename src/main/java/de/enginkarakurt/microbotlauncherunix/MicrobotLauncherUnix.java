package de.enginkarakurt.microbotlauncherunix;

import de.enginkarakurt.microbotlauncherunix.util.FileUtils;

public class MicrobotLauncherUnix {
    public static final String VERSION = "1.3.0";

    public static void main(String[] args) {
       runMicrobotLauncher();
    }

    private static void runMicrobotLauncher() {
        System.out.println("--- microbot-launcher-unix v" + VERSION + " ---\n");

        // Setup Directory
        FileUtils.setupDirectory();

        // Update and retrieve latest version
        String fileName = FileUtils.updateAndRetrieveLatestVersion();

        // Run file
        FileUtils.runJarFile(fileName);

        System.out.println("\n\nFinished!");
    }
}