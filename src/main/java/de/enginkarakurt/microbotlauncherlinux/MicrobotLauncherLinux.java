package de.enginkarakurt.microbotlauncherlinux;

import de.enginkarakurt.microbotlauncherlinux.util.HttpUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class MicrobotLauncherLinux {
    public static void main(String[] args) {
        String version = "1.0";
        System.out.println("microbot-launcher-linux v" + version);

        System.out.println("Checking releases...");
        String releasesResponseBody = HttpUtils.sendRequestAndReceiveResponseBody("https://api.github.com/repos/chsami/microbot/releases");

        ArrayList<JSONObject> jsonObjects = new ArrayList<>();

        if (releasesResponseBody != null) {
            JSONArray jsonArray = new JSONArray(releasesResponseBody);
            jsonArray.forEach(jsonObject -> {
                JSONObject obj = (JSONObject) jsonObject;
                jsonObjects.add(obj);
            });
        }

        JSONObject latestRelease = null;
        for (JSONObject object : jsonObjects) {
            if (!Objects.equals(object.get("tag_name").toString(), "nightly")) {
                latestRelease = object;
                break;
            }
        }

        ArrayList<JSONObject> assetsJsonObjects = new ArrayList<>();

        JSONObject assetToDownload = null;


        if (latestRelease != null) {
            String assetsUrl = latestRelease.get("assets_url").toString();

            String assetsResponseBody = HttpUtils.sendRequestAndReceiveResponseBody(assetsUrl);

            if (assetsResponseBody != null) {
                JSONArray assets = new JSONArray(assetsResponseBody);
                assets.forEach(jsonObject -> {
                    JSONObject obj = (JSONObject) jsonObject;
                    assetsJsonObjects.add(obj);
                });

                for (JSONObject assetsJsonObject : assetsJsonObjects) {
                    if (assetsJsonObject.get("name").toString().contains("microbot") && assetsJsonObject.get("name").toString().contains(".jar")) {
                        assetToDownload = assetsJsonObject;
                        break;
                    }
                }
            }
        }

        if (assetToDownload != null) {
            System.out.println("Latest release found: " + assetToDownload.get("name").toString());

            System.out.println("Creating microbot folder if it does not already exists...");
            new File("microbot").mkdirs();
            
            System.out.println("Attempting to download...");
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
                System.out.println("Download completed!");
                System.out.println("Running jar...");
                Runtime.getRuntime().exec(
                        String.format("java -jar %s", assetToDownload.get("name").toString()),
                        null,
                        new File(System.getProperty("user.dir") + "/microbot")
                );


            } catch (Exception ex) {
                System.out.println("Download Failed! \n");
                ex.printStackTrace();
            }
        }
    }
}