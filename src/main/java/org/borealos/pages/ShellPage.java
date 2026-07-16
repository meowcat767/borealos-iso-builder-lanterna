package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;

public class ShellPage {
    private final BasicWindow window;
    private final Panel panel;
    private final ComboBox comboBox = new ComboBox<String>();
    private final Button button = new Button("Continue", new Runnable() {
        @Override
        public void run() {
            int PickedShell = comboBox.getSelectedIndex();

            String ShFlag = switch (PickedShell) {
                case 0 -> "--bash";
                case 1 -> "--fish";
                case 2 -> "--sh";
                default -> "--bash";
            };

            new ShellPage(window).show();
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
