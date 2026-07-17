package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import org.borealos.val.InstallConfig;

public class KernelPage {
    private final BasicWindow window;
    private final Panel panel;
    private final ComboBox comboBox = new ComboBox<String>();
    private final Button button = new Button("Continue", new Runnable() {
        @Override
        public void run() {
            int selectedKernel = comboBox.getSelectedIndex();

            if (selectedKernel == -1) {
                MessageDialog.showMessageDialog(window.getTextGUI(), "Error", "Please select a kernel.");
                // even though we have default, it isn't the best option incase of a failed return.
            }

            InstallConfig installConfig = new InstallConfig();

            switch (selectedKernel) {
                case 0 -> installConfig.setKernelLTS(true);
                case 1 -> installConfig.setKernelStd(true);
                default -> installConfig.setKernelLTS(true);
            };

        }
    });

    public KernelPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel();
    }

    public void show() {
        window.setTitle("Select Kernel");
        comboBox.addItem("Current 7.0x");
        comboBox.addItem("LTS 6.18");
        panel.addComponent(comboBox);
        panel.addComponent(button);
        window.setComponent(panel);
    }
}
