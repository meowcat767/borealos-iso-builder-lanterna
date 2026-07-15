package org.borealos.subsys;

import java.io.IOException;

public class PackageManagerDetector {
    public static String getPackageManager() throws IOException {
        String[] managers = {"apt", "pacman", "yay", "dnf", "zypper", "yum"};
        for (String manager : managers) {
            if (isCommandAvailable(manager)){
                return manager;
            }
        }
        return null;
    }

    private static boolean isCommandAvailable(String command) {
        try {
            Process process = new ProcessBuilder("which", command).start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
