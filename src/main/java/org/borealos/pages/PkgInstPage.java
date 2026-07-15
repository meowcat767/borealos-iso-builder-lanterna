package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import org.borealos.subsys.PackageManagerDirector;

public class PkgInstPage {
    private final BasicWindow window;
    private final Panel panel;
    private final ProgressBar progressBar = new ProgressBar(0, 100, 50);

    public PkgInstPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
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
    }
}
