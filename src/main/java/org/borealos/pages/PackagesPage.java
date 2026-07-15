package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class PackagesPage {

    private final BasicWindow window;
    private final Panel panel;

    private final Button detectButton = new Button("Detect", new Runnable() {
        @Override
        public void run() {
            window.setTitle("Local Package Manager");
            try {
                WindowBasedTextGUI textGUI = window.getTextGUI();
                String manager = org.borealos.subsys.PackageManagerDetector.getPackageManager();
                if ("apk".equals(manager)) {
                    MessageDialog.showMessageDialog(textGUI, "Package Manager", "You will need to add the community repo in /etc/apk/repositories.", MessageDialogButton.OK);
                }
                MessageDialogButton result = MessageDialog.showMessageDialog(
                        textGUI,
                        "Package Manager",
                        manager != null ? "Detected: " + manager : "No supported package manager found.",
                        MessageDialogButton.OK);


                if (result == MessageDialogButton.OK) {
                    // Dialog dismissed via OK -> swap this window's content to the next page.
                    FileValidationPage fileValidationPage = new FileValidationPage(window);
                    fileValidationPage.show();
                }
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
