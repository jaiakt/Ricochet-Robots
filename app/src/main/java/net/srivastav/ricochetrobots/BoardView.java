package net.srivastav.ricochetrobots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO: document your custom view class.
 */
public class BoardView extends View {
    private final int BACKGROUND_COLOR = android.graphics.Color.WHITE;

    private Paint fillBoxPaint;

    private Paint noWallBorder;
    private Paint wallBorder;
    private TextPaint targetPaint;

    private Board board;

    private int contentSize;
    private int startX;
    private int startY;
    private float cellSize;

    final float TEXT_SIZE = 40;

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
        noWallBorder.setStrokeWidth(2);
        noWallBorder.setColor(android.graphics.Color.BLACK);
        noWallBorder.setStrokeCap(Paint.Cap.BUTT);
        wallBorder = new Paint();
        wallBorder.setStrokeWidth(10);
        wallBorder.setColor(android.graphics.Color.BLACK);
        wallBorder.setStrokeCap(Paint.Cap.ROUND);

        targetPaint = new TextPaint();
        targetPaint.setTextSize(TEXT_SIZE);
        targetPaint.setTextAlign(Paint.Align.CENTER);
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
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (heightMeasureSpec < widthMeasureSpec)
            widthMeasureSpec = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void drawRect(int row, int col, int sidePadding, Canvas canvas, Paint paint) {
        canvas.drawRect(startX + row * cellSize + sidePadding,
                startY + col * cellSize + sidePadding,
                startX + (row + 1) * cellSize - sidePadding,
                startY + (col + 1) * cellSize - sidePadding,
                paint);
    }

    private void drawSquares(Canvas canvas) {
        fillBoxPaint.setColor(Color.NONE.value);
        for (int i = 0; i < board.ROWS; i++) {
            for (int j = 0; j < board.COLS; j++) {
                drawRect(i, j, 1, canvas, fillBoxPaint);
            }
        }

        for (Color key : board.pieceLocations.keySet()) {
            Location loc = board.pieceLocations.get(key);
            fillBoxPaint.setColor(key.value);
            drawRect(loc.row, loc.col, 15, canvas, fillBoxPaint);
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

    private void drawTargets(Canvas canvas) {
        HashMap<Color, ArrayList<BoardSpace>> targetMap = board.targetMap;
        for (Color color : targetMap.keySet()) {
            for (BoardSpace space : targetMap.get(color)) {
                Rect textSize = new Rect();
                String text = "" + space.targetID;
                targetPaint.getTextBounds(text, 0, text.length(), textSize);
                int textHeight = textSize.bottom - textSize.top;
                targetPaint.setColor(color.value);
                canvas.drawText(text,
                        startX + space.loc.col*cellSize + cellSize/2,
                        startY + space.loc.row*cellSize + (cellSize + textHeight)/2,
                        targetPaint);
            }
        }
    }

   @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(BACKGROUND_COLOR);
        drawSquares(canvas);
        drawBorders(canvas);
        drawTargets(canvas);
    }

}
