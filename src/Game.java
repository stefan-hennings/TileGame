import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;


public class Game extends JFrame implements Serializable {
    // TODO: 22-Oct-20 Write code
    private static final ImageIcon icon = new ImageIcon("numberfifteen.png");
    private static JPanel southPanel = new JPanel();
    private static final JPanel northPanel = new JPanel();
    private static final JPanel topPanel = new JPanel();
    private static JLabel movesText;
    private static final JButton startOverButton = new JButton("Starta om");
    private static JSlider slider;
    private final GamePanel run;
    private static Timer timer;
    private JLabel timeLabel;
    private static int hours, minutes, seconds;
    private static Font smallFont = new Font("Bell MT", Font.PLAIN, 15);
    private static List<Integer> highscore = new ArrayList<>();

    public Game() {

        timeLabel = new JLabel("Tid: " + minutes + " : " + seconds);
        timeLabel.setFont(smallFont);
        timeLabel.setForeground(GamePanel.FOREGROUND_COLOR);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        timer = new Timer(1000, e -> {
            seconds++;
            if (seconds == 60) {
                seconds = 0;
                minutes++;
                if (minutes == 60) {
                    minutes = 0;
                    hours++;
                }
            }
            timeLabel.setText("Tid: " + minutes + " : " + seconds);
            if (hours > 0)
                timeLabel.setText("Tid: " + hours + " : " + minutes + " : " + seconds);
        });
        timer.start();
        slider = new JSlider(JSlider.HORIZONTAL, 2, 7, 4);
        slider.setMajorTickSpacing(1);
        slider.setFont(new Font("Bell MT", Font.PLAIN, 15));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(GamePanel.FOREGROUND_COLOR);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!slider.getValueIsAdjusting()) {
                    run.getLogic().changeGridParameters(slider.getValue());
                    run.setSizeOfTile(slider.getValue());
                    run.paintComponent(run.getGraphics());
                    startNewGame();
                }
            }
        });
        movesText = GamePanel.getMovesText();
        southPanel = GamePanel.getSouthPanel();
        southPanel.setBackground(Color.WHITE);
        southPanel.add(timeLabel, BorderLayout.EAST);
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(GamePanel.FOREGROUND_COLOR);
        setIconImage(icon.getImage());
        setTitle("Världens bästa brickspel, typ");

        run = new GamePanel(600, 30, slider.getValue());
        startNewGame();
        add(run, BorderLayout.CENTER);
        movesText.setForeground(GamePanel.FOREGROUND_COLOR);
        southPanel.setLayout(new GridLayout(2, 0));
        southPanel.add(movesText, BorderLayout.SOUTH);
        northPanel.add(startOverButton, BorderLayout.CENTER);
        northPanel.add(slider, BorderLayout.EAST);
        startOverButton.setFont(new Font("Bell MT", Font.BOLD, 25));
        startOverButton.setBackground(GamePanel.FOREGROUND_COLOR);
        startOverButton.setForeground(Color.BLACK);
        startOverButton.setFocusPainted(false);
        startOverButton.addActionListener(e -> startNewGame());
        topPanel.setLayout(new GridLayout(2, 0));
        topPanel.add(northPanel);
        topPanel.add(southPanel);

        add(topPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }

    public void startNewGame() {
        seconds = 0;
        minutes = 0;
        hours = 0;
        timeLabel.setText("Tid: " + minutes + " : " + seconds);
        if (hours > 0)
            timeLabel.setText("Tid: " + hours + " : " + minutes + " : " + seconds);
        run.callNewGame();
        timer.start();
        run.repaint();
        pack();
    }

    public static Timer getTimer() {
        return timer;
    }

    public static void loadAndSaveHighscore() {
        deSerialize();
        int score = hours * 3600 + minutes * 60 + seconds + GameLogic.getMoveCount();
        highscore.add(score);
        Collections.sort(highscore);
        highscore.forEach(System.out::println);
        serialize();
    }

    public static void serialize() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("highscores.ser"));
            out.writeObject(highscore);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}