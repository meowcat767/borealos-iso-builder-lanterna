package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import org.borealos.val.InstallConfig;

public class DePage {
    private final BasicWindow window;
    private final Panel panel;
    private final ComboBox<String> comboBox = new ComboBox<>();
    private final InstallConfig installConfig; // No instantiation here!

    private final Button button = new Button("Continue", new Runnable() {
        @Override
        public void run() {
            int pickedDE = comboBox.getSelectedIndex();

            switch (pickedDE) {
                case 0 -> installConfig.setDesktopEnvironment("--kde"); // Matches script parameter expectation
                case 1 -> installConfig.setDesktopEnvironment("--xfce");
                case 2 -> installConfig.setDesktopEnvironment("--niri");
                case 3 -> installConfig.setDesktopEnvironment("--no-de");
                default -> installConfig.setDesktopEnvironment("--xfce");
            }

            // Pass the single shared configuration state to the next step
            new KernelPage(window, installConfig).show();
        }
    });

    // Update constructor to accept the configuration
    public DePage(BasicWindow window, InstallConfig installConfig) {
        this.window = window;
        this.installConfig = installConfig;
        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
    }

    public void show() {
        window.setTitle("Select Desktop Environment");
        panel.addComponent(new Label("You now need to select a desktop environment."));

        comboBox.addItem("KDE Plasma");
        comboBox.addItem("XFCE 4");
        comboBox.addItem("Niri");
        comboBox.addItem("None (TTY)");

        panel.addComponent(comboBox);
        panel.addComponent(button);
        window.setComponent(panel);
    }
}