package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;

public class PackagesPage {

    private final BasicWindow window;
    private final Panel panel;

    public PackagesPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel();
    }

    public void show() {
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("I need to determine what package manager your local Linux install uses."));
        window.setComponent(panel);
    }
}
