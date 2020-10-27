import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-22
 * Time: 16:44
 * Project: Inlämning3GUI
 * Copyright: MIT
 */
public class GameLogic {

    private int[] tiles;
    private int numberOfTiles;
    List<Integer> shuffler;
    private int gridSide;
    private int blankPosition;
    private int moveCount = 0;

    public GameLogic(int gridSide) {
        this.gridSide = gridSide;
        tiles = new int[this.gridSide * this.gridSide];
        numberOfTiles = this.gridSide * this.gridSide - 1;

        shuffler = new ArrayList<>();

        for (int i = 1; i < (this.gridSide * this.gridSide); i++) {
            shuffler.add(i);
        }
    }

    public void moveTiles(int clickedXPixel, int clickedYPixel, int gridPixelWidth, int tilePixelWidth) {

        if (clickedXPixel < 0 || clickedXPixel > gridPixelWidth || clickedYPixel < 0 || clickedYPixel > gridPixelWidth) {
            return;
        }

        // Otherwise we get position in the grid
        int clickPositionColumn = clickedXPixel / tilePixelWidth;
        int clickPositionRow = clickedYPixel / tilePixelWidth;

        int blankCellColumn = blankPosition % gridSide;
        int blankCellRow = blankPosition / gridSide;

        int clickPosition = clickPositionRow * gridSide + clickPositionColumn;

        int direction = 0;

        if (clickPositionColumn == blankCellColumn && Math.abs(clickPositionRow - blankCellRow) > 0) {

            direction = (clickPositionRow - blankCellRow) > 0 ? gridSide : -gridSide;

        } else if (clickPositionRow == blankCellRow && Math.abs(clickPositionColumn - blankCellColumn) > 0) {

            direction = (clickPositionColumn - blankCellColumn) > 0 ? 1 : -1;

        }

        if (direction != 0) {
            do {
                int newBlankPosition = blankPosition + direction;
                tiles[blankPosition] = tiles[newBlankPosition];
                blankPosition = newBlankPosition;

            } while (blankPosition != clickPosition);
            tiles[blankPosition] = 0;

            moveCount++;
        }
    }

    public boolean isSolved() {
        if (tiles[tiles.length - 1] != 0) // if blank tile is not in the solved position ==> not solved
            return false;

        for (int i = numberOfTiles - 1; i >= 0; i--) {
            if (tiles[i] != i + 1)
                return false;
        }

        return true;
    }

    public boolean isSolvable() {
        int inversionCount = 0;

        for (int i = 0; i < numberOfTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i])
                    inversionCount++;
            }
        }
        return inversionCount % 2 == 0;
    }

    public void newGame() {
        moveCount = 0;
        do {
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = (i + 1) % tiles.length;
            }

            // we set blank position to tiles[last index]
            blankPosition = tiles.length - 1;
            if (numberOfTiles > 1) {
                Collections.shuffle(shuffler);
                for (int i = 0; i < tiles.length - 1; i++) {
                    tiles[i] = shuffler.get(i);
                }
            }
        } while (!isSolvable() || isSolved());
    }


    public int[] getTiles() {
        return tiles;
    }

    public int getGridSide() {
        return gridSide;
    }

    public int getBlankPosition() {
        return blankPosition;
    }

    public void setBlankPosition(int blankPosition) {
        this.blankPosition = blankPosition;
    }

    public void changeArray(int index, int newNumber) {
        tiles[index] = newNumber;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void changeGridParameters(int gridSide) {
        this.gridSide = gridSide;
        tiles = new int[gridSide * gridSide];
        numberOfTiles = gridSide * gridSide - 1;
        shuffler = new ArrayList<>();

        for (int i = 1; i < (this.gridSide * this.gridSide); i++) {
            shuffler.add(i);
        }
    }
}
