package com.myorg.unempx;

import com.myorg.unempx.ui.UnempXApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Launching UnempX Swing application...");

        try {
            SwingUtilities.invokeLater(() -> {
                log.debug("Initializing UnempXApp UI...");
                new UnempXApp().setVisible(true);
                log.info("UnempXApp UI is now visible.");
            });
        } catch (Exception e) {
            log.error("Unexpected error while starting UnempX application", e);
        }
    }
}
