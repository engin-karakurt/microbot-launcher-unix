package de.enginkarakurt.microbotlauncherlinux;

import de.enginkarakurt.microbotlauncherlinux.util.FileUtils;

public class MicrobotLauncherLinux {
    public static final String VERSION = "1.1.1";

    public static void main(String[] args) {
       runMicrobotLauncher();
    }

    private static void runMicrobotLauncher() {
        System.out.println("--- microbot-launcher-linux v" + VERSION + " ---\n");

        // Setup Directory
        FileUtils.setupDirectory();

        // Download file
        String fileName = FileUtils.downloadFile();

        // Run file
        FileUtils.runJarFile(fileName);

        System.out.println("\n\nFinished!");
    }
}