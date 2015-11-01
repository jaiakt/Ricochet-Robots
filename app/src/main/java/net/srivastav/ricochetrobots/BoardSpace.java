package net.srivastav.ricochetrobots;

/**
 * TODO: Add a class header comment!
 */
public class BoardSpace {
    public boolean top, bottom, left, right;
    public Color color;
    public int targetID;
    public BoardSpace(boolean top, boolean bottom, boolean left, boolean right,
                        Color color, int targetID) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.color = color;
        this.targetID = targetID;
    }
    public BoardSpace() {
        this(false, false, false, false, Color.NONE, 0);
    }

}
