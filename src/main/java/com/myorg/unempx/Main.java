package com.myorg.unempx;

import com.myorg.unempx.ui.UnempXApp;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UnempXApp().setVisible(true));
    }
}
