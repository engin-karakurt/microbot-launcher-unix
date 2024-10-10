package de.enginkarakurt.microbotlauncherlinux.util;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class FileUtils {

    public static void setupDirectory() {
        if (new File("microbot").mkdirs()) {
            System.out.println("\n--- Setup directory... ---");
            System.out.println("Creating Microbot folder...");
        }
    }

    public static String downloadFile() {
        if(VersionUtils.isNewerVersion()) {
            JSONObject assetToDownload = VersionUtils.getAssetToDownload();
            if (assetToDownload != null) {
                System.out.println("\n--- Download release... ---");

                try {
                    URL url = new URI(assetToDownload.get("browser_download_url").toString()).toURL();

                    InputStream inputStream = url.openStream();
                    FileOutputStream fileOutputStream = new FileOutputStream("microbot/" + assetToDownload.get("name").toString());

                    byte[] bytes = new byte[1024];
                    int len;

                    while ((len = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, len);
                    }

                    fileOutputStream.close();
                    inputStream.close();

                    System.out.println("Download completed!\n");

                    return assetToDownload.get("name").toString();

                } catch (Exception ex) {
                    System.out.println("Download Failed! \n");
                    ex.printStackTrace();
                }
            }
        }

        return new File(System.getProperty("user.dir") + "/microbot").listFiles()[0].getName();
    }

    public static void runJarFile(String fileName) {
        System.out.println("--- Execute the .jar... ---");
        System.out.printf("Attempting to run %s...%n", fileName);
        try {
            Runtime.getRuntime().exec(
                    String.format("java -jar %s", fileName),
                    null,
                    new File(System.getProperty("user.dir") + "/microbot")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
