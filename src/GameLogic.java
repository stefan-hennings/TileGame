import java.util.ArrayList;
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
    List<Integer> shuffledNumbers;
    private int gridSide;
    private int blankPosition;

    public GameLogic(int gridSide){
        this.gridSide = gridSide;
        tiles = new int[this.gridSide * this.gridSide];
        numberOfTiles = this.gridSide *this.gridSide -1;

        shuffledNumbers = new ArrayList<>();

        for(int i = 1; i<(this.gridSide *this.gridSide); i++){
            shuffledNumbers.add(i);
        }
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
