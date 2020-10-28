import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-28
 * Time: 16:05
 * Project: Inlämning3GUI
 * Copyright: MIT
 */
public class HighScore implements Comparable<HighScore>, Serializable {

    private static List<HighScore> highScoreList = new ArrayList<>();
    private int score;
    private String name;

    public HighScore(int score, String name){
        this.score = score;
        this.name = name;
        highScoreList.add(this);
    }

    @SuppressWarnings("unchecked")
    public static void deSerialize() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("highscores.ser"));
            highScoreList = (List<HighScore>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Fil skapad");
        }
    }

    public static List<HighScore> getHighScoreList() {
        return highScoreList;
    }
    public Integer getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(HighScore o) {
        return this.getScore().compareTo(o.getScore());
    }
}
