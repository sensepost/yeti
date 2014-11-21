package com.sensepost.yeti.common;

import com.sensepost.yeti.gui.DebugDisplay;

public class Log {

    private static final DebugDisplay debugWindow = new DebugDisplay();

    public static DebugDisplay getDebugWindow() {
        return debugWindow;
    }

    public static void debug(String message) {
        debugWindow.addDebug(message);
    }
}
