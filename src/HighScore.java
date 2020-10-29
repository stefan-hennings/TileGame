import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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

    public static void loadAndSaveHighscore() {
        GameFrame.setJOptionPaneProperties();
        deSerialize();

        String name = null;
        while (name == null || name.isEmpty()) {
            name = (String) JOptionPane.showInputDialog(null, "Grattis, du vann!\n" +
                            "Ange ditt namn för att spara din poäng: ", "Ange namn",
                    JOptionPane.QUESTION_MESSAGE, GameFrame.getMedalIcon(), null, null);
            if (name == null) {
                JOptionPane.showMessageDialog(null, "Din poäng har inte sparats.", "Ingen poäng sparad", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Du måste ange ett namn!", "Där blev det fel!", JOptionPane.ERROR_MESSAGE);
            }
        }
        int score = GameFrame.getHours() * 3600 + GameFrame.getMinutes() * 60 + GameFrame.getSeconds() + GameLogic.getMoveCount();
        new HighScore(score, name);
        if (getHighScoreList().size() > 1) {
            Collections.sort(getHighScoreList());
        }
        serialize();
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
