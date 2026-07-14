package org.borealos;

import org.borealos.pages.WelcomePage;

public class App {
    public static void main(String[] args) {
        org.borealos.pages.WelcomePage welcomePage = new WelcomePage();
        welcomePage.init();
    }
}
