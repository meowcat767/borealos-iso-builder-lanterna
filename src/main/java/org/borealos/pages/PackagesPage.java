package org.borealos.pages;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Arrays;

public class PackagesPage {

    private Terminal terminal = null;
    private Screen screen = null;
    private TextGraphics graphics = null;

    private BasicWindow window = null;
    private MultiWindowTextGUI gui = null;
    private Panel panel = null;
    private Button button = null;

    public void init() {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        try {
            this.terminal = terminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
            this.graphics = screen.newTextGraphics();
            this.window = new BasicWindow("Set up local packages");
            this.gui = new MultiWindowTextGUI(screen);
            this.panel = new Panel();


            screen.startScreen();
            screen.setCursorPosition(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DispPackagesPage() {
        window.setHints(Arrays.asList(Window.Hint.CENTERED));

        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("I need to determine what package manager your local Linux install uses."));
    }
}
