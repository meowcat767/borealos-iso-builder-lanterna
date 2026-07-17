package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import org.borealos.val.InstallConfig;


public class DePage {
    private final BasicWindow window;
    private final Panel panel;
    private final ComboBox comboBox = new ComboBox<String>();
    private final InstallConfig installConfig = new InstallConfig();
    private final Button button = new Button("Continue", new Runnable() {
        @Override
        public void run() {
            int PickedDE = comboBox.getSelectedIndex();


            switch (PickedDE) {
                case 0 -> installConfig.setDesktopEnvironment("--plasma");
                case 1 -> installConfig.setDesktopEnvironment("--xfce");
                case 2 -> installConfig.setDesktopEnvironment("--niri");
                case 3 -> installConfig.setDesktopEnvironment("--tty");
                default -> installConfig.setDesktopEnvironment("--xfce");
            };
        }
    });

    public DePage(BasicWindow window) {
        this.window = window;
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
