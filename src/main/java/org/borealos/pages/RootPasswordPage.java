package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import java.util.Arrays;

public class RootPasswordPage {
    private final BasicWindow window;
    private final Panel panel;
    private static String rootPassword = "";

    public RootPasswordPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
    }

    public static String getRootPassword() {
        return rootPassword;
    }

    public void show() {
        window.setTitle("Root Authentication");

        panel.addComponent(new Label("The installer requires root privileges to install packages."));
        panel.addComponent(new Label("Please enter the root/sudo password:"));

        final TextBox passwordBox = new TextBox().setMask('*');
        panel.addComponent(passwordBox);

        panel.addComponent(new EmptySpace());

        panel.addComponent(new Button("Continue", new Runnable() {
            @Override
            public void run() {
                rootPassword = passwordBox.getText();
                if (rootPassword.isEmpty()) {
                    MessageDialog.showMessageDialog(window.getTextGUI(), "Warning", "Password cannot be empty (if sudo requires one).");
                }
                PackagesPage packagesPage = new PackagesPage(window);
                packagesPage.show();
            }
        }));

        panel.addComponent(new Button("Back", new Runnable() {
            @Override
            public void run() {
                WelcomePage welcomePage = new WelcomePage();
                // Note: WelcomePage.init() creates a new screen, which might not be ideal here.
                // But following the existing pattern for now.
                welcomePage.init();
            }
        }));

        window.setComponent(panel);
    }
}
