package org.borealos.pages;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import org.borealos.subsys.BuildScriptDirector;
import org.borealos.val.InstallConfig;

public class ExecPage {
    private final BasicWindow window;
    private final Panel panel;
    private final InstallConfig installConfig; // FIXED: Changed to hold the shared instance

    private final TextBox outputBox =
            new TextBox(new TerminalSize(100, 20))
                    .setReadOnly(true);

    // FIXED: Constructor now accepts the single shared configuration instance
    public ExecPage(BasicWindow window, InstallConfig installConfig) {
        this.window = window;
        this.installConfig = installConfig;
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
        // Now cleanly sends the fully aggregated config down to the script process builder
        new BuildScriptDirector().FireScript(installConfig, this::appendOutput);
    }
}