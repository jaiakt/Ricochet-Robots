package net.srivastav.ricochetrobots;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

/**
 * TODO: Add a class header comment!
 */

public class Board {
    static int ROWS = 16;
    static int COLS = 16;
    private BoardSpace [][] boardArray;
    private HashMap<Color, ArrayList<Integer>> idMap;
    public Board() {
        boardArray = new BoardSpace[ROWS][COLS];
    }

    public void setColor(int r, int c, Color color) {
        boardArray[r][c].color = color;
    }
    public void setID(int r, int c, int id) {
        boardArray[r][c].targetID = id;
    }
    public int getNewID(Color color) {
        ArrayList<Integer> idList = idMap.get(color);
        if (idList == null) {
            idList = new ArrayList<>();
            idList.add(1);
            idMap.put(color, idList);
            return 1;
        }
        else {
            int id = idList.get(idList.size() - 1);
            ++id;
            idList.add(id);
            idMap.put(color, idList);
            return id;
        }
    }
    public void setTop(int r, int c) {
        boardArray[r][c].top = true;
    }
    public void setLeft(int r, int c) {
        boardArray[r][c].left = true;
    }
    public void setBottom(int r, int c) {
        if (r < ROWS - 1)
            boardArray[r+1][c].top = true;
        else
            boardArray[r][c].bottom = true;
    }
    public void setRight(int r, int c) {
        if (c < COLS - 1)
            boardArray[r][c+1].left = true;
        else
            boardArray[r][c].right = true;
    }
    public void setTopLeft(int r, int c, Color color) {
        setTop(r, c);
        setLeft(r, c);
        setColor(r, c, color);
        setID(r, c, getNewID(color));
    }
    public void setBottomLeft(int r, int c, Color color) {
        setBottom(r, c);
        setLeft(r, c);
        setColor(r, c, color);
        setID(r, c, getNewID(color));
    }
    public void setTopRight(int r, int c, Color color) {
        setTop(r, c);
        setRight(r, c);
        setColor(r, c, color);
        setID(r, c, getNewID(color));
    }
    public void setBottomRight(int r, int c, Color color) {
        setBottom(r, c);
        setRight(r, c);
        setColor(r, c, color);
        setID(r, c, getNewID(color));
    }

    // Dummy board data from this picture:
    // http://www.redmeeple.com/site/images/stories/virtuemart/product/ricochet_robots_image4.jpg
    public void setDummyBoard() {
        // Set Walls
        for (int i = 0; i < boardArray.length; i++) {
            setLeft(i, 0);
            setRight(i, COLS - 1);
        }
        for (int j = 0; j < boardArray[0].length; j++) {
            setTop(0, j);
            setBottom(ROWS - 1, j);
        }
        setTop(ROWS / 2 - 1, COLS / 2 - 1);
        setLeft(ROWS / 2 - 1, COLS / 2 - 1);
        setBottom(ROWS / 2, COLS / 2 - 1);
        setLeft(ROWS / 2, COLS / 2 - 1);
        setTop(ROWS / 2 - 1, COLS / 2);
        setRight(ROWS / 2 - 1, COLS / 2);
        setBottom(ROWS / 2, COLS / 2);
        setRight(ROWS / 2, COLS / 2);

        setRight(0, 3);
        setRight(0, 9);
        setBottomRight(1, 13, Color.GREEN);
        setBottom(1, 15);
        setBottomRight(2, 5, Color.BLUE);
        setTopLeft(3, 9, Color.BLUE);
        setBottom(4, 0);
        setTopRight(4, 2, Color.GREEN);
        setTopRight(4, 14, Color.RED);
        setBottomLeft(5, 7, Color.RED);
        setTopLeft(6, 1, Color.YELLOW);
        setBottomLeft(6, 12, Color.YELLOW);
        setBottomLeft(9, 4, Color.YELLOW);
        setBottomLeft(9, 13, Color.BLUE);
        setBottom(10, 0);
        setTopLeft(10, 6, Color.BLUE);
        setBottomRight(11, 9, Color.YELLOW);
        setBottom(11, 15);
        setTopRight(12, 7, Color.ALL);
        setTopRight(13, 1, Color.RED);
        setTopRight(13, 14, Color.RED);
        setBottomRight(14, 3, Color.GREEN);
        setTopLeft(14, 10, Color.GREEN);
        setRight(15, 4);
        setRight(15, 11);
    }
}