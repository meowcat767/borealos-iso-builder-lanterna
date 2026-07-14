package org.borealos.pages;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class WelcomePage {

    public Terminal terminal = null;
    public Screen screen = null;
    public TextGraphics graphics = null;

    public void init() {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        try {
            this.terminal = terminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
            this.graphics = screen.newTextGraphics();

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

       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
