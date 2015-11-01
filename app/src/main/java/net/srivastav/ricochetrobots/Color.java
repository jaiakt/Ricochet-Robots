package net.srivastav.ricochetrobots;

import java.util.ArrayList;

/**
 * TODO: Add a class header comment!
 */
public enum Color {
    BLUE(android.graphics.Color.BLUE),
    YELLOW(android.graphics.Color.YELLOW),
    RED(android.graphics.Color.RED),
    GREEN(android.graphics.Color.GREEN),
    NONE(android.graphics.Color.rgb(150, 150, 150)),
    ALL(android.graphics.Color.MAGENTA);
    final public static Color[] pieceColors = {BLUE, YELLOW, RED, GREEN};
    public final int value;
    Color(int value) {
        this.value = value;
    }
}