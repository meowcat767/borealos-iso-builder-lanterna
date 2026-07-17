package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;

public class ExecPage {
    private final BasicWindow window;
    private final Panel panel;

    public ExecPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
    }

    public void show() {
        window.setTitle("Executing Build");
        window.setComponent(panel);
        panel.addComponent(new Label("Executing build..."));
        panel.addComponent(new Label("This may take a while."));

        window.setComponent(panel);


    }
}
