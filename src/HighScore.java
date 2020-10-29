import java.io.*;
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
    private final int score;
    private final String name;

    public HighScore(int score, String name) {
        this.score = score;
        this.name = name;
        highScoreList.add(this);
    }

    public static void serialize() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("highscores.ser"));
            out.writeObject(getHighScoreList());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void deSerialize() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("highscores.ser"));
            highScoreList = (List<HighScore>) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            serialize();
            System.out.println("Fil skapad");
        } catch (Exception e) {
            e.printStackTrace();
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
