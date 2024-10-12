package de.enginkarakurt.microbotlauncherunix;

import de.enginkarakurt.microbotlauncherunix.util.FileUtil;

public class MicrobotLauncherUnix {
    public static final String VERSION = "1.4.1";

    public static void main(String[] args) {
       runMicrobotLauncher();
    }

    private static void runMicrobotLauncher() {
        System.out.println("--- microbot-launcher-unix v" + VERSION + " ---");

        // Setup Directory
        FileUtil.setupDirectory();

        // Update and retrieve latest version
        String fileName = FileUtil.updateAndRetrieveLatestVersion();

        // Run file
        FileUtil.runJarFile(fileName);

        System.out.println("\nFinished!");
    }
}