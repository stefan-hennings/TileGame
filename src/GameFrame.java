import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;


public class GameFrame extends JFrame implements Serializable {
    // TODO: 22-Oct-20 Write code
    private static final ImageIcon icon = new ImageIcon("numberfifteen.png");
    private static JPanel southPanel = new JPanel();
    private static final JPanel northPanel = new JPanel();
    private static final JPanel topPanel = new JPanel();
    private static JLabel movesText;
    private static final JButton startOverButton = new JButton("Starta om");
    private static JSlider slider;
    private final GamePanel gamePanel;
    private static Timer timer;
    private JLabel timerLabel;
    private static int hours, minutes, seconds;
    private static Font smallFont = new Font("Bell MT", Font.PLAIN, 15);
    private static List<Integer> highscore = new ArrayList<>();

    public GameFrame() {

        timerLabel = new JLabel("");
        timerLabel.setFont(smallFont);
        timerLabel.setForeground(GamePanel.FOREGROUND_COLOR);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

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
            updateTimerLabel();
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
                    gamePanel.getLogic().changeGridParameters(slider.getValue());
                    gamePanel.setSizeOfTile(slider.getValue());
                    gamePanel.paintComponent(gamePanel.getGraphics());
                    startNewGame();
                }
            }
        });
        movesText = GamePanel.getMovesText();
        southPanel = GamePanel.getSouthPanel();
        southPanel.setBackground(Color.WHITE);
        southPanel.add(timerLabel, BorderLayout.EAST);
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(GamePanel.FOREGROUND_COLOR);
        setIconImage(icon.getImage());
        setTitle("Världens bästa brickspel, typ");

        gamePanel = new GamePanel(600, 30, slider.getValue());
        startNewGame();
        add(gamePanel, BorderLayout.CENTER);
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

    private void updateTimerLabel() {
        timerLabel.setText(String.format("Tid: %02d:%02d", minutes, seconds));
        if (hours > 0)
            timerLabel.setText(String.format("Tid: %02d:%02d:%02d", hours, minutes, seconds));
    }

    public void startNewGame() {
        seconds = 0;
        minutes = 0;
        hours = 0;
        updateTimerLabel();
        gamePanel.callNewGame();
        timer.start();
        gamePanel.repaint();
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