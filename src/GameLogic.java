import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {

    private int[] tiles;
    private int numberOfTilesExcludingBlank;
    private List<Integer> shuffler;
    private int gridSide;
    private int blankPosition;
    private static int moveCount = 0;
    List<Integer> tilesToRotate;

    public GameLogic(int gridSide) {
        this.gridSide = gridSide;
        tiles = new int[this.gridSide * this.gridSide];
        numberOfTilesExcludingBlank = this.gridSide * this.gridSide - 1;

        resetShuffler();
    }

    private void resetShuffler() {
        shuffler = new ArrayList<>();

        for (int i = 0; i < (this.gridSide * this.gridSide); i++) {
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
        GameFrame.updateMoveCountLabel();
    }

    public boolean isSolved() {
        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isSolvable() {
        int inversionCount = 0;

        //Count the number of times a high number appears before a low number
        for (int i = 0; i <= numberOfTilesExcludingBlank; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i] && !(tiles[i] == 0 || tiles[j] == 0)) {
                    inversionCount++;
                }
            }
        }
        //If the gridSide is odd, or if it's even and the blank tile is on an even numbered row, confirm that
        //inversionCount is even. Otherwise, if confirm that it's odd.
        if (gridSide % 2 == 1 || (blankPosition % (gridSide * 2)) >= gridSide) {
            return inversionCount % 2 == 0;
        } else {
            return inversionCount % 2 == 1;
        }
    }

    public void newGame() {
        moveCount = 0;
        do {
            Collections.shuffle(shuffler);
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = shuffler.get(i);
                if (shuffler.get(i) == 0) {
                    blankPosition = i;
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
        numberOfTilesExcludingBlank = gridSide * gridSide - 1;
        resetShuffler();
    }
}
