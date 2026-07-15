package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

public class PackagesPage {

    private final BasicWindow window;
    private final Panel panel;

    private final Button detectButton = new Button("Detect", new Runnable() {
        @Override
        public void run() {
            try {
                WindowBasedTextGUI textGUI = window.getTextGUI();
                String manager = org.borealos.subsys.PackageManagerDetector.getPackageManager();
                MessageDialog.showMessageDialog(
                        textGUI,
                        "Package Manager",
                        manager != null ? "Detected: " + manager : "No supported package manager found.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    public PackagesPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel();
    }

    public void show() {
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("I need to determine what package manager your local Linux install uses."));
        panel.addComponent(detectButton);

        window.setComponent(panel);
    }
}
