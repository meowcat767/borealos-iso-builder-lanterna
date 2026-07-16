package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;


public class DePage {
    private final BasicWindow window;
    private final Panel panel;
    private final ComboBox comboBox = new ComboBox<String>();
    private final Button button = new Button("Continue", new Runnable() {
        @Override
        public void run() {
            int PickedDE = comboBox.getSelectedIndex();

            String flag = switch (PickedDE) {
                case 0 -> "--kde";
                case 1 -> "--xfce";
                case 2 -> "--niri";
                case 3 -> "--tty";
                default -> "--xfce";
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
