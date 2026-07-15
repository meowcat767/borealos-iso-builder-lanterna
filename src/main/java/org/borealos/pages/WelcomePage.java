package org.borealos.pages;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Arrays;

public class WelcomePage {

    public Terminal terminal = null;
    public Screen screen = null;
    public TextGraphics graphics = null;

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
            this.window = new BasicWindow("Welcome");
            this.gui = new MultiWindowTextGUI(screen);
            this.panel = new Panel();
            this.button = new Button("Continue", new Runnable() {
                @Override
                public void run() {

                }
            });

            screen.startScreen();
            screen.setCursorPosition(null);

            terminal.enterPrivateMode(); // this is so we activate a buffer for our app, and can spool text on the term behind
            terminal.clearScreen();


            DispWelcomePage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        try {
            terminal.exitPrivateMode();
            terminal.clearScreen();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DispWelcomePage() {
       try {
           window.setHints(Arrays.asList(Window.Hint.CENTERED));

           panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
           panel.addComponent(new Label("Welcome to BorealOS ISO Builder!"));
           panel.addComponent(new Label(""));
           panel.addComponent(new Label("This wizard will help you build a BorealOS ISO."));
           panel.addComponent(new Label(""));
           panel.addComponent(new Label("Press any key to continue..."));
           panel.addComponent(button);

           window.setComponent(panel);
           gui.addWindowAndWait(window);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
