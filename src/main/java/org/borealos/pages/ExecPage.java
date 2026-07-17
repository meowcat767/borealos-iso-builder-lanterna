package org.borealos.pages;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import org.borealos.subsys.BuildScriptDirector;
import org.borealos.val.InstallConfig;

public class ExecPage {
    private final BasicWindow window;
    private final Panel panel;
    private final InstallConfig installConfig = new InstallConfig();

    private final TextBox outputBox =
            new TextBox(new TerminalSize(100, 20))
                    .setReadOnly(true);

    public ExecPage(BasicWindow window) {
        this.window = window;
        this.panel = new Panel(new LinearLayout(Direction.VERTICAL));
    }

    public void show() {
        window.setTitle("Executing Build");

        panel.addComponent(new Label("Executing build..."));
        panel.addComponent(new Label("This may take a while."));
        panel.addComponent(outputBox);

        window.setComponent(panel);

        FireIt();
    }

    public void appendOutput(String line) {
        window.getTextGUI()
                .getGUIThread()
                .invokeLater(() -> outputBox.addLine(line));
    }

    private void FireIt() {
        new BuildScriptDirector().FireScript(installConfig, this::appendOutput);
    }
}