package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import org.borealos.subsys.PackageManagerDirector;
import org.borealos.val.InstallConfig;

public class PkgInstPage {
    private final BasicWindow window;
    private final Panel panel;
    private final ProgressBar progressBar = new ProgressBar(0, 100, 50);
    private final InstallConfig installConfig;

    // 1. FIXED: Added 'InstallConfig installConfig' to the constructor parameters
    public PkgInstPage(BasicWindow window, InstallConfig installConfig) {
        this.window = window;
        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
        this.installConfig = installConfig; // 2. FIXED: Will now properly assign from parameter
    }

    public void show() {
        window.setTitle("Installing Packages");

        panel.addComponent(new Label("Installing base package modules..."));
        panel.addComponent(new Label("This may take a while."));
        panel.addComponent(new Label(""));
        panel.addComponent(progressBar);

        window.setComponent(panel);

        WindowBasedTextGUI gui = window.getTextGUI();
        PackageManagerDirector.installPackages(gui, progressBar);

        // 3. FIXED: Replaced 'ins' with the correct field variable 'installConfig'
        new DePage(window, installConfig).show();
    }
}