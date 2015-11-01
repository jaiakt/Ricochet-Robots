package net.srivastav.ricochetrobots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class BoardView extends View {
    private Paint fillBoxPaint;

    private Paint noWallBorder;
    private Paint wallBorder;

    private Board board;

    private int contentSize;
    private int startX;
    private int startY;
    private float cellSize;

    public BoardView(Context context) {
        super(context);
        init(null, 0);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        board = new Board();

        // Set up a default TextPaint object
        fillBoxPaint = new Paint();
        fillBoxPaint.setStyle(Paint.Style.FILL);

        noWallBorder = new Paint();
        noWallBorder.setStrokeWidth(3);
        noWallBorder.setColor(android.graphics.Color.BLACK);
        wallBorder = new Paint();
        wallBorder.setStrokeWidth(7);
        wallBorder.setColor(android.graphics.Color.BLACK);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (heightMeasureSpec < widthMeasureSpec)
            widthMeasureSpec = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void drawRect(int row, int col, Canvas canvas, Paint paint) {
        canvas.drawRect(startX + row * cellSize + 1, startY + col * cellSize + 1,
                startX + (row + 1) * cellSize - 1, startY + (col + 1) * cellSize - 1, paint);
    }

    private void drawSquares(Canvas canvas) {
        fillBoxPaint.setColor(Color.NONE.value);
        for (int i = 0; i < board.ROWS; i++) {
            for (int j = 0; j < board.COLS; j++) {
                drawRect(i, j, canvas, fillBoxPaint);
            }
        }
        Log.v("asdf", "inDrawSquares");

        for (Color key : board.pieceLocations.keySet()) {
            Location loc = board.pieceLocations.get(key);
            fillBoxPaint.setColor(key.value);
            drawRect(loc.row, loc.col, canvas, fillBoxPaint);
        }
    }

    private void drawHorizontalLine(int rowBelow, int col, Canvas canvas) {
        // If wall
        if (
                (rowBelow > 0 && board.get(rowBelow - 1, col).bottom) ||
                (rowBelow < board.ROWS && board.get(rowBelow, col).top)
                ) {
            canvas.drawLine(startX + col*cellSize, startY + rowBelow*cellSize,
                    startX + (col+1)*cellSize, startY + rowBelow*cellSize, wallBorder);
        }
        else {
            canvas.drawLine(startX + col*cellSize, startY + rowBelow*cellSize,
                    startX + (col+1)*cellSize, startY + rowBelow*cellSize, noWallBorder);
        }
    }

    private void drawVerticalLine(int row, int colRight, Canvas canvas) {
        // If wall
        if (
                (colRight > 0 && board.get(row, colRight - 1).right) ||
                (colRight < board.COLS && board.get(row, colRight).left)
                ) {
            canvas.drawLine(startX + colRight*cellSize, startY + row*cellSize,
                    startX + colRight*cellSize, startY + (row+1)*cellSize, wallBorder);
        }
        else {
            canvas.drawLine(startX + colRight*cellSize, startY + row*cellSize,
                    startX + colRight*cellSize, startY + (row+1)*cellSize, noWallBorder);
        }
    }

    private void drawBorders(Canvas canvas) {
        // Draw horizontals
        for (int i = 0; i <= board.ROWS; ++i) {
            for (int j = 0; j < board.COLS; ++j) {
                drawHorizontalLine(i, j, canvas);
            }
        }

        // Draw verticals
        for (int i = 0; i < board.ROWS; ++i) {
            for (int j = 0; j <= board.COLS; ++j) {
                drawVerticalLine(i, j, canvas);
            }
        }
    }

    @Override
    protected void onSizeChanged(int a, int b, int c, int d) {
        super.onSizeChanged(a, b, c, d);
        contentSize = Math.min(getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom());
        startX = (getWidth() - contentSize) / 2;
        startY = (getHeight() - contentSize) / 2;
        cellSize = contentSize / board.ROWS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        contentSize = Math.min(getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom());
        startX = (getWidth() - contentSize) / 2;
        startY = (getHeight() - contentSize) / 2;
        cellSize = contentSize / board.ROWS;
        canvas.drawColor(android.graphics.Color.WHITE);
        Log.v("af", "drawing squares");
        drawSquares(canvas);
        drawBorders(canvas);
    }

}
