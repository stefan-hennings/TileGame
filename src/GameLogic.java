import java.util.ArrayList;
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
    private static int moveCount = 0;
    List<Integer> tilesToRotate;

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

        tilesToRotate = new ArrayList<>();

        if (clickedXPixel < 0 || clickedXPixel > gridPixelWidth || clickedYPixel < 0 || clickedYPixel > gridPixelWidth) {
            return;
        }

        // Otherwise we get position in the grid
        int clickedColumn = clickedXPixel / tilePixelWidth;
        int clickedRow = clickedYPixel / tilePixelWidth;

        int blackColumn = blankPosition % gridSide;
        int blackRow = blankPosition / gridSide;

        int clickedIndex = clickedRow * gridSide + clickedColumn;

        if (clickedIndex == blankPosition) {
            return;
        } else if (clickedRow == blackRow) {
            if (clickedColumn < blackColumn) {
                for (int i = clickedIndex; i <= blankPosition; i++) {
                    tilesToRotate.add(tiles[i]);
                }
                Collections.rotate(tilesToRotate, 1);
                int insertIndex = 0;
                for (int i = clickedIndex; i <= blankPosition; i++) {
                    tiles[i] = tilesToRotate.get(insertIndex);
                    insertIndex++;
                }
            } else {
                for (int i = blankPosition; i <= clickedIndex; i++) {
                    tilesToRotate.add(tiles[i]);
                }
                Collections.rotate(tilesToRotate, -1);
                int insertIndex = 0;
                for (int i = blankPosition; i <= clickedIndex; i++) {
                    tiles[i] = tilesToRotate.get(insertIndex++);
                }
            }
        } else if (clickedColumn == blackColumn) {
            if (clickedRow < blackRow) {
                for (int i = clickedIndex; i <= blankPosition; i += gridSide) {
                    tilesToRotate.add(tiles[i]);
                }
                Collections.rotate(tilesToRotate, 1);
                int insertIndex = 0;
                for (int i = clickedIndex; i <= blankPosition; i += gridSide) {
                    tiles[i] = tilesToRotate.get(insertIndex++);
                }
            } else {
                for (int i = blankPosition; i <= clickedIndex; i += gridSide) {
                    tilesToRotate.add(tiles[i]);
                }
                Collections.rotate(tilesToRotate, -1);
                int insertIndex = 0;
                for (int i = blankPosition; i <= clickedIndex; i += gridSide) {
                    tiles[i] = tilesToRotate.get(insertIndex++);
                }
            }
        } else {
            return; //Illegal Move - do nothing
        }
        this.blankPosition = clickedIndex;
        moveCount++;
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

    public static int getMoveCount() {
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
