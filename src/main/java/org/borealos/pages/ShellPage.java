package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;

import org.borealos.val.InstallConfig;

public class ShellPage {
    private final BasicWindow window;
    private final Panel panel;
    private final ComboBox comboBox = new ComboBox<String>();
    private final Button button = new Button("Continue", new Runnable() {
        @Override
        public void run() {
            int PickedShell = comboBox.getSelectedIndex();
            InstallConfig installConfig = new InstallConfig();

            switch (PickedShell) {
                case 0 -> installConfig.setInstallBash(true);
                case 1 -> installConfig.setInstallFish(true);
                case 2 -> installConfig.setInstallSh(true);
                default -> installConfig.setInstallBash(true);
            };

            new KernelPage(window).show();
        }
    });

    private ShellPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
    }

    private void show() {
        window.setTitle("Select Shell");
        comboBox.addItem("bash");
        comboBox.addItem("fish");
        comboBox.addItem("sh");
        panel.addComponent(comboBox);
        window.setComponent(panel);
    }
}
