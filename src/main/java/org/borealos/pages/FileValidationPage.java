package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileValidationPage {

    private final BasicWindow window;
    private final Panel panel;
    private final Button button = new Button("Validate", new Runnable() {
        @Override
        public void run() {
            try {
                if (!Files.exists(Paths.get("borealOS-rootfs.tar.gz"))){
                    throw new IOException("File not found");
                }

                MessageDialogButton result = MessageDialog.showMessageDialog(
                        window.getTextGUI(),
                        "RootFS Valid",
                        "RootFS is valid.",
                        MessageDialogButton.OK);
                if (result == MessageDialogButton.OK) {

                }

            } catch (Exception e) {
                MessageDialog.showMessageDialog(window.getTextGUI(), "Error!", e.getMessage());
            }
        }
    });

    public FileValidationPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel();
    }

    public void show() {
        window.setTitle("Asset Validation");
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("I now need to validate that the rootfs is valid."));
        panel.addComponent(new Label(""));
        panel.addComponent(new Label("I am expecting borealOS-rootfs.tar.gz in the same directory as me."));
        panel.addComponent(button);
        window.setComponent(panel);
    }
}
