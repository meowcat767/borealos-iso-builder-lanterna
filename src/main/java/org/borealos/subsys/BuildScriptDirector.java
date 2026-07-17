package org.borealos.subsys;

import org.borealos.val.InstallConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuildScriptDirector {
    private final ExecutorService worker = Executors.newSingleThreadExecutor();

    public interface OutputListener {
        void onLine(String line);
    }

    public void FireScript(
            InstallConfig installConfig,
            OutputListener listener) {

        worker.submit(() -> {
            try {

                List<String> command = new ArrayList<>();

                command.add("sudo");
                command.add("bash");
                command.add("./build-iso.sh");

                // Map Desktop Environment string to argument flags
                String de = installConfig.getDesktopEnvironment();
                if (de != null && !de.trim().isEmpty()) {
                    if (!de.startsWith("--")) {
                        command.add("--" + de.toLowerCase());
                    } else {
                        command.add(de);
                    }
                }

                // Add shell option flags
                if (installConfig.isInstallBash()) {
                    command.add("--bash");
                }
                if (installConfig.isInstallFish()) {
                    command.add("--fish");
                }
                if (installConfig.isInstallSh()) {
                    command.add("--sh");
                }

                // Add kernel selection flags
                if (installConfig.isKernelLTS()) {
                    command.add("--kernel-lts");
                } else {
                    command.add("--kernel-cur");
                }

                System.out.println("Running command: " + String.join(" ", command));
                runCommand(listener, command.toArray(new String[0]));
            } catch (Exception e) {
                if (listener != null) {
                    String msg = (e.getMessage() != null) ? e.getMessage() : e.toString();
                    listener.onLine("ERROR: " + msg);
                }
                e.printStackTrace();
            }
        });
    }

    private void runCommand(OutputListener listener, String... command) throws Exception {
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

        java.io.BufferedReader reader =
                new java.io.BufferedReader(
                        new java.io.InputStreamReader(process.getInputStream()));

        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");

            // Send output directly to the UI/log listener
            if (listener != null) {
                listener.onLine(line);
            }
        }

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            String errorMsg =
                    "Command failed: " + String.join(" ", command)
                            + "\nExit code: " + exitCode
                            + "\nOutput: " + output;

            System.err.println(errorMsg);

            if (output.toString().contains("sudo: a password is required")
                    || output.toString().contains("sudo: no tty present")
                    || output.toString().contains("sudo: 1 incorrect password attempt")) {

                throw new RuntimeException(
                        "Privilege escalation failed. Incorrect password or sudo configuration issue."
                );
            }

            throw new RuntimeException(
                    "Command failed with exit code: "
                            + exitCode
                            + "\nOutput: "
                            + output
            );
        }
    }
}