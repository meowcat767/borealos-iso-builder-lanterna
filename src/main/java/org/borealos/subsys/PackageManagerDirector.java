package org.borealos.subsys;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
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
                        runCommand("sudo", "apt", "install", "-y", "xorriso");
                        updateUI(gui, progressBar, 20);

                        runCommand("sudo", "apt", "install", "-y", "imagemagick");
                        updateUI(gui, progressBar, 40);

                        runCommand("sudo", "apt", "install", "-y", "grub-efi-amd64-bin", "grub-pc-bin", "mtools");
                        updateUI(gui, progressBar, 60);

                        runCommand("sudo", "apt", "install", "-y", "squashfs-tools");
                        updateUI(gui, progressBar, 80);

                        runCommand("sudo", "apt", "install", "-y", "unzip");
                        updateUI(gui, progressBar, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                        updateUIError(gui, e.getMessage());
                    }
                });
            } else if ("apk".equals(pm)){
                worker.submit(() -> {
                    try {
                        runCommand("sudo", "apk", "add", "xorriso");
                         updateUI(gui, progressBar, 10);
                         runCommand("sudo", "apk", "add", "imagemagick");
                         updateUI(gui, progressBar, 20);
                         runCommand("sudo", "apk", "add", "mtools");
                         updateUI(gui, progressBar, 40);
                         runCommand("sudo", "apk", "add", "squashfs-tools");
                         updateUI(gui, progressBar, 50);
                         runCommand("sudo", "apk", "add", "unzip");
                         updateUI(gui, progressBar, 60);
                         runCommand("sudo", "apk", "add", "grub-efi");
                         updateUI(gui, progressBar, 80);
                         runCommand("sudo", "apk", "add", "grub");
                         updateUI(gui, progressBar, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                        updateUIError(gui, e.getMessage());
                    }
                });
            } else if ("pacman".equals(pm)){
                worker.submit(() -> {
                    try {
                        runCommand("sudo", "pacman", "-Syy");
                        runCommand("sudo", "pacman", "-S", "--noconfirm", "xorriso");
                        updateUI(gui, progressBar, 10);
                        runCommand("sudo", "pacman", "-S", "--noconfirm", "imagemagick");
                        updateUI(gui, progressBar, 20);
                        runCommand("sudo", "pacman", "-S", "--noconfirm", "mtools");
                        updateUI(gui, progressBar, 30);
                        runCommand("sudo", "pacman", "-S", "--noconfirm", "squashfs-tools");
                        updateUI(gui, progressBar, 40);
                        runCommand("sudo", "pacman", "-S", "--noconfirm", "unzip");
                        updateUI(gui, progressBar,  50);
                        runCommand("sudo", "pacman", "-S", "--noconfirm", "grub");
                        updateUI(gui, progressBar, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                        updateUIError(gui, e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateUIError(WindowBasedTextGUI gui, String message) {
        if (gui != null) {
            gui.getGUIThread().invokeLater(() -> {
                com.googlecode.lanterna.gui2.dialogs.MessageDialog.showMessageDialog(gui, "Error", message);
            });
        }
    }

    private static void runCommand(String... command) throws Exception {
        // If we are not root and trying to use a package manager, we might need elevation
        // But since we are in a TUI, sudo will fail if it asks for a password and we don't have a tty.
        // We now use 'sudo -S' to read the password from stdin.

        String[] finalCommand = command;
        boolean useSudoS = command.length > 0 && "sudo".equals(command[0]);
        if (useSudoS) {
            // Insert -S after sudo
            String[] newCmd = new String[command.length + 1];
            newCmd[0] = "sudo";
            newCmd[1] = "-S";
            System.arraycopy(command, 1, newCmd, 2, command.length - 1);
            finalCommand = newCmd;
        }

        ProcessBuilder pb = new ProcessBuilder(finalCommand)
                .redirectErrorStream(true);
        
        Process process = pb.start();

        if (useSudoS) {
            String password = org.borealos.pages.RootPasswordPage.getRootPassword();
            try (java.io.OutputStream os = process.getOutputStream()) {
                os.write((password + "\n").getBytes());
                os.flush();
            }
        }

        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String errorMsg = "Command failed: " + String.join(" ", command) + "\nExit code: " + exitCode + "\nOutput: " + output.toString();
            System.err.println(errorMsg);
            
            if (output.toString().contains("sudo: a password is required") || output.toString().contains("sudo: no tty present") || output.toString().contains("sudo: 1 incorrect password attempt")) {
                throw new RuntimeException("Privilege escalation failed. Incorrect password or sudo configuration issue.");
            }
            
            throw new RuntimeException("Command failed with exit code: " + exitCode + "\nOutput: " + output.toString());
        }
    }

    // Changed signature here as well to cleanly map text interface threads
    private static void updateUI(WindowBasedTextGUI gui, ProgressBar bar, int value) {
        if (gui != null && bar != null) {
            gui.getGUIThread().invokeLater(() -> bar.setValue(value)); // Works perfectly
        }
    }
}
