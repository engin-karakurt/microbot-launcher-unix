package de.enginkarakurt.microbotlauncherlinux.util;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class VersionUtils {

    public static Optional<JSONObject> getLatestRelease() {
        String releasesResponseBody = HttpUtils.sendRequestAndReceiveResponseBody("https://api.github.com/repos/chsami/microbot/releases");
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(releasesResponseBody);
        jsonArray.forEach(jsonObject -> {
            JSONObject obj = (JSONObject) jsonObject;
            jsonObjects.add(obj);
        });

        for (JSONObject object : jsonObjects) {
            if (!Objects.equals(object.get("tag_name").toString(), "nightly")) {
                return Optional.of(object);
            }
        }

        return Optional.empty();
    }

    public static String getAssetsUrl() {
        if (VersionUtils.getLatestRelease().isPresent()) {
            System.out.println("--- Get latest release... ---");
            JSONObject latestRelease = VersionUtils.getLatestRelease().get();

            return latestRelease.get("assets_url").toString();
        } else {
            return null;
        }
    }

    public static JSONObject getAssetToDownload() {
        String assetsResponseBody = HttpUtils.sendRequestAndReceiveResponseBody(getAssetsUrl());
        ArrayList<JSONObject> assetsJsonObjects = new ArrayList<>();

        if (assetsResponseBody != null) {
            JSONArray assets = new JSONArray(assetsResponseBody);
            assets.forEach(jsonObject -> {
                JSONObject obj = (JSONObject) jsonObject;
                assetsJsonObjects.add(obj);
            });

            for (JSONObject assetsJsonObject : assetsJsonObjects) {
                if (assetsJsonObject.get("name").toString().contains("microbot") && assetsJsonObject.get("name").toString().contains(".jar")) {
                    System.out.println("Latest release: " + assetsJsonObject.get("name").toString());
                    return assetsJsonObject;
                }
            }
        }

        return null;
    }

    public static boolean isNewerVersion() {
        boolean isNewer = false;
        File[] filesInFolder = new File(System.getProperty("user.dir") + "/microbot").listFiles();

        if(filesInFolder == null || filesInFolder.length < 1) {
            isNewer = true;
        }
        else {
            AtomicReference<ComparableVersion> latestVersion = new AtomicReference<>();

            VersionUtils.getLatestRelease().ifPresent(latestRelease -> latestVersion.set(new ComparableVersion(latestRelease.get("tag_name").toString())));

            ComparableVersion localVersion;

            for (File file : filesInFolder) {
                localVersion = new ComparableVersion(file.getName().split("-")[1].replace(".jar","").trim());
                isNewer = latestVersion.get().compareTo(localVersion) > 0;

                if(isNewer) {
                    System.out.println("v" + localVersion + " is outdated! Deleting...\n");

                    if(file.delete()) {
                        System.out.println("Successfully deleted the outdated version!\n");
                    }
                }
            }
        }

        return isNewer;
    }
}
