package org.borealos.subsys;

import org.borealos.val.InstallConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BuildScriptDirector {
    private static final ExecutorService worker = Executors.newSingleThreadExecutor();

    public static void FireScript(InstallConfig installConfig) {
        try {
            worker.submit(() -> {

                List<String> command = new ArrayList<>();
                command.add("bash");
                command.add("./build.sh");
                command.add(installConfig.getDesktopEnvironment());

                if (installConfig.isInstallBash()) {
                    command.add("--bash");
                }

                if (installConfig.isInstallFish()) {
                    command.add("--fish");
                }

                if (installConfig.isInstallSh()) {
                    command.add("--sh");
                }

                if (installConfig.isKernelLTS()) {
                    command.add("--lts");
                }

                try {
                    runCommand(command.toArray(new String[0]));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    private static void runCommand(String... command) throws Exception {
        // If we are not root and trying to use a package manager, we might need elevation
        // But since we are in a TUI, sudo will fail if it asks for a password and we don't have a tty.
        // We now use 'sudo -S' to read the password from stdin.

        String[] finalCommand = command;
        boolean useSudoS = command.length > 0 && "sudo".equals(command[0]);
        if (useSudoS) {
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
}
