package org.borealos.subsys;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI; // <-- Change this import
import com.googlecode.lanterna.gui2.ProgressBar;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PackageManagerDirector {
    private static final ExecutorService worker = Executors.newSingleThreadExecutor();

    // Changed signature to accept base type WindowBasedTextGUI
    public static void installPackages(WindowBasedTextGUI gui, ProgressBar progressBar) {
        try {
            String pm = PackageManagerDetector.getPackageManager();

            if ("apt".equals(pm)){
                worker.submit(() -> {
                    try {
                        runCommand("apt", "install", "-y", "xorriso");
                        updateUI(gui, progressBar, 20);

                        runCommand("apt", "install", "-y", "imagemagick");
                        updateUI(gui, progressBar, 40);

                        runCommand("apt", "install", "-y", "grub-efi-amd64-bin", "grub-pc-bin", "mtools");
                        updateUI(gui, progressBar, 60);

                        runCommand("apt", "install", "-y", "squashfs-tools");
                        updateUI(gui, progressBar, 80);

                        runCommand("apt", "install", "-y", "unzip");
                        updateUI(gui, progressBar, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else if ("apk".equals(pm)){
                worker.submit(() -> {
                    try {
                        runCommand("apk", "add", "xorriso");
                         updateUI(gui, progressBar, 10);
                         runCommand("apk", "add", "imagemagick");
                         updateUI(gui, progressBar, 20);
                         runCommand("apk", "add", "mtools");
                         updateUI(gui, progressBar, 40);
                         runCommand("apk", "add", "squashfs-tools");
                         updateUI(gui, progressBar, 50);
                         runCommand("apk", "add", "unzip");
                         updateUI(gui, progressBar, 60);
                         runCommand("apk", "add", "grub-efi");
                         updateUI(gui, progressBar, 80);
                         runCommand("apk", "add", "grub");
                         updateUI(gui, progressBar, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else if ("pacman".equals(pm)){
                try {
                    runCommand("pacman", "-S", "--noconfirm", "xorriso");
                    updateUI(gui, progressBar, 10);
                    runCommand("pacman", "-S", "--noconfirm", "imagemagick");
                    updateUI(gui, progressBar, 20);
                    runCommand("pacman", "-S", "--noconfirm", "mtools");
                    updateUI(gui, progressBar, 30);
                    runCommand("pacman", "-S", "--noconfirm", "squashfs-tools");
                    updateUI(gui, progressBar, 40);
                    runCommand("pacman", "-S", "--noconfirm", "unzip");
                    updateUI(gui, progressBar,  50);
                    runCommand("pacman", "-S", "--noconfirm", "grub");
                    updateUI(gui, progressBar, 100);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runCommand(String... command) throws Exception {
        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        process.getInputStream().transferTo(java.io.OutputStream.nullOutputStream());

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code: " + exitCode);
        }
    }

    // Changed signature here as well to cleanly map text interface threads
    private static void updateUI(WindowBasedTextGUI gui, ProgressBar bar, int value) {
        if (gui != null && bar != null) {
            gui.getGUIThread().invokeLater(() -> bar.setValue(value)); // Works perfectly
        }
    }
}
