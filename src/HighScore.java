import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-28
 * Time: 16:05
 * Project: Inlämning3GUI
 * Copyright: MIT
 */
public class HighScore {
    private static List<Integer> highscore = new ArrayList<>();
    private static List<String> scoreHolder = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static void deSerialize() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("highscores.ser"));
            highscore = (List<Integer>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Fil skapad");
        }
    }

    public static List<Integer> getHighscore() {
        return highscore;
    }

    public static List<String> getScoreHolder() {
        return scoreHolder;
    }
}
