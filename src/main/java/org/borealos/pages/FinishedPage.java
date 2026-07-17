package org.borealos.pages;

import com.googlecode.lanterna.gui2.*;

public class FinishedPage {
    private final BasicWindow window;
    private final Panel panel;
    private final Button button = new Button("Exit", new Runnable() {
        @Override
        public void run() {
            System.exit(0);
        }
    });

    public FinishedPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
    }

    public void show() {
        window.setTitle("Finished!");
        panel.addComponent(new Label("Build Complete!"));
        panel.addComponent(new Label("You can find your ISO where this tool is located."));

        panel.addComponent(button);
        window.setComponent(panel);
    }
}
