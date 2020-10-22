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
    private int size;

    public GameLogic(int size){
        this.size = size;
    }

    public boolean isSolved(){
        return true;
    }

    public boolean isSolvable(){
        return true;
    }

    public void newGame(){

    }

    public int[] getTiles() {
        return tiles;
    }

    public int getSize() {
        return size;
    }
}
