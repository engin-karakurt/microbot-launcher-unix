package de.enginkarakurt.microbotlauncherunix.util;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class VersionUtil {

    private static Optional<JSONObject> getLatestRelease() {
        String releasesResponseBody = HttpUtil.sendRequestAndReceiveResponse("https://api.github.com/repos/chsami/microbot/releases").body();
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

    private static String getAssetsUrl() {
        if (VersionUtil.getLatestRelease().isPresent()) {
            JSONObject latestRelease = VersionUtil.getLatestRelease().get();

            return latestRelease.get("assets_url").toString();
        } else {
            return null;
        }
    }

    public static JSONObject getAssetToDownload() {
        String assetsResponseBody = Objects.requireNonNull(HttpUtil.sendRequestAndReceiveResponse(getAssetsUrl())).body();
        ArrayList<JSONObject> assetsJsonObjects = new ArrayList<>();

            JSONArray assets = new JSONArray(assetsResponseBody);
            assets.forEach(jsonObject -> {
                JSONObject obj = (JSONObject) jsonObject;
                assetsJsonObjects.add(obj);
            });

            for (JSONObject assetsJsonObject : assetsJsonObjects) {
                if (assetsJsonObject.get("name").toString().toLowerCase().contains("microbot") && assetsJsonObject.get("name").toString().toLowerCase().contains(".jar")) {
                    System.out.printf("Latest release: %s%n", assetsJsonObject.get("name").toString());
                    return assetsJsonObject;
                }
            }

        return null;
    }

    public static int checkVersion() {
        System.out.println("--- Checking version ---");
        int isNewer = -1;

        File[] filesInJarsDir = FileUtil.getFilesFromJarsDir();

        if(filesInJarsDir == null || filesInJarsDir.length < 1) {
            isNewer = 1;
        }
        else {
            AtomicReference<ComparableVersion> latestVersion = new AtomicReference<>();

            VersionUtil.getLatestRelease().ifPresent(latestRelease -> latestVersion.set(new ComparableVersion(latestRelease.get("tag_name").toString())));

            ComparableVersion localVersion;

            for (File file : filesInJarsDir) {
                if(file.getName().toLowerCase().contains(".jar") && file.getName().toLowerCase().contains("microbot")) {
                    // Extract version from filename
                    localVersion = new ComparableVersion(file.getName().split("-")[1].replace(".jar","").trim());

                    isNewer = isVersionNewer(latestVersion.get(), localVersion);

                    if(isNewer > 0) {
                        System.out.println("v" + localVersion + " is outdated! Deleting...");

                        if(file.delete()) {
                            System.out.println("Successfully deleted the outdated version!");
                        }
                    }
                }
            }
        }

        return isNewer;
    }

    public static int isVersionNewer(ComparableVersion version1, ComparableVersion version2) {
        return version1.compareTo(version2);
    }
}
