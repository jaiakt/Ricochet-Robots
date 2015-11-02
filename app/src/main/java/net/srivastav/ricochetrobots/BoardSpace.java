package net.srivastav.ricochetrobots;

/**
 * TODO: Add a class header comment!
 */
public class BoardSpace {
    public boolean top, bottom, left, right;
    public Color color;
    public int targetID;

    // Optional.  Only really used for targets.
    public Location loc;

    public BoardSpace(boolean top, boolean bottom, boolean left, boolean right,
                        Color color, int targetID, Location loc) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.color = color;
        this.targetID = targetID;
        this.loc = loc;
    }

    public BoardSpace(boolean top, boolean bottom, boolean left, boolean right,
                      Color color, int targetID) {
        this(top, bottom, left, right, color, targetID, null);
    }

    public BoardSpace(Color color, int targetID, Location loc) {
        this(false, false, false, false, color, targetID, loc);
    }
    public BoardSpace() {
        this(false, false, false, false, Color.NONE, 0);
    }

}
