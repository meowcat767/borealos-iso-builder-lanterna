package org.borealos.pages;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class WelcomePage {

    private Terminal terminal = null;

    public void init() {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        try {
            this.terminal = terminalFactory.createTerminal();
            terminal.enterPrivateMode(); // this is so we activate a buffer for our app, and can spool text on the term behind
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
