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

    public GameLogic(int gridSide){
        this.gridSide = gridSide;
        tiles = new int[this.gridSide * this.gridSide];
        numberOfTiles = this.gridSide *this.gridSide -1;

        shuffler = new ArrayList<>();

        for(int i = 1; i<(this.gridSide *this.gridSide); i++){
            shuffler.add(i);
        }
    }

    public void moveTiles(int clickedValue){
        int clickedIndex = 0;
        int blackIndex = 0;
        int indexesFoundCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == clickedValue) {
                clickedIndex = i;
                indexesFoundCount++;
            } else if (tiles[i] == 0) {
                blackIndex = i;
                indexesFoundCount++;
            }
            if (indexesFoundCount == 2) {
                break;
            }
        }

        int clickedRow = clickedIndex % gridSide;
        int clickedColumn = clickedIndex / gridSide;
        int blackRow = blackIndex % gridSide;
        int blackColumn = blackIndex / gridSide;

        shuffler.clear();
        if (clickedRow == blackRow) {
            if (clickedColumn < blackColumn) {
                for (int i = clickedIndex; i <= blackIndex; i++) {
                    shuffler.add(tiles[i]);
                }
                Collections.rotate(shuffler, 1);
                int insertIndex = 0;
                for (int i = clickedIndex; i < blackIndex; i++) {
                    tiles[i] = shuffler.get(insertIndex++);
                }
            } else {
                for (int i = blackIndex; i <= clickedIndex; i++) {
                    shuffler.add(tiles[i]);
                }
                Collections.rotate(shuffler, -1);
                int insertIndex = 0;
                for (int i = clickedIndex; i < blackIndex; i++) {
                    tiles[i] = shuffler.get(insertIndex++);
                }
            }
        } else if (clickedColumn == blackColumn) {
            if (clickedRow < blackRow) {
                for (int i = clickedIndex; i <= blackIndex; i += gridSide) {
                    shuffler.add(tiles[i]);
                }
                Collections.rotate(shuffler, 1);
                int insertIndex = 0;
                for (int i = clickedIndex; i < blackIndex; i += gridSide) {
                    tiles[i] = shuffler.get(insertIndex++);
                }
            } else {
                for (int i = blackIndex; i <= clickedIndex; i += gridSide) {
                    shuffler.add(tiles[i]);
                }
                Collections.rotate(shuffler, -1);
                int insertIndex = 0;
                for (int i = clickedIndex; i < blackIndex; i += gridSide) {
                    tiles[i] = shuffler.get(insertIndex++);
                }
            }
        } else {
            return; //Clicked invalid tile, do nothing
        }
        moveCount++;
    }

    public boolean isSolved(){
        return false;
    }

    public boolean isSolvable(){
        int inversionCount = 0;

        for (int i = 0; i < numberOfTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i])
                    inversionCount++;
            }
        }
        return inversionCount % 2 == 0;
    }

    public void newGame(){

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
    public void changeArray(int index, int newNumber){
        tiles[index] = newNumber;
    }
}
