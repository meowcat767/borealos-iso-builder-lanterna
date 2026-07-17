package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import org.borealos.val.InstallConfig;

public class KernelPage {
    private final BasicWindow window;
    private final Panel panel;
    private final ComboBox<String> comboBox = new ComboBox<>();
    private final InstallConfig installConfig; // FIXED: Keep track of the shared instance

    private final Button button = new Button("Continue", new Runnable() {
        @Override
        public void run() {
            int selectedKernel = comboBox.getSelectedIndex();

            if (selectedKernel == -1) {
                MessageDialog.showMessageDialog(window.getTextGUI(), "Error", "Please select a kernel.");
                return; // Stop execution if nothing is selected
            }

            // Note: Make sure case 0 and case 1 align properly with your combo box order
            switch (selectedKernel) {
                case 0 -> installConfig.setKernelLTS(false); // Current 7.0x is Std, not LTS
                case 1 -> installConfig.setKernelLTS(true);  // LTS 6.18
                default -> installConfig.setKernelLTS(false);
            }

            // FIXED: Pass the shared config forward to the final execution page
            new ExecPage(window, installConfig).show();
        }
    });

    // FIXED: Accept the single shared InstallConfig in the constructor
    public KernelPage(BasicWindow window, InstallConfig installConfig) {
        this.window = window;
        this.installConfig = installConfig;
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