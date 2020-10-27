import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameFrame extends JFrame implements Serializable {

    private static final Color FOREGROUND_COLOR = new Color(0x9e7bb5);
    private static final ImageIcon icon = new ImageIcon("numberfifteen.png");
    private static final JPanel menuPanel = new JPanel();
    private static final JPanel topPanel = new JPanel();
    private static final JButton startOverButton = new JButton("Starta om");
    private static final Font smallFont = new Font("Bell MT", Font.PLAIN, 15);
    private static JLabel moveCountLabel;
    private static JSlider gridSizeSlider;
    private static Timer timer;
    private static int hours, minutes, seconds;
    private static List<Integer> highscore = new ArrayList<>();
    private final GamePanel gamePanel;
    private JLabel timerLabel;

    public GameFrame() {
        setIconImage(icon.getImage());
        setTitle("Världens bästa brickspel, typ");

        createTimer();

        createGridSizeSlider();

        createTopPanel();

        createStartOverButton();

        gamePanel = new GamePanel(600, 30, gridSizeSlider.getValue());
        add(gamePanel, BorderLayout.CENTER);

        startNewGame();

        add(topPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createStartOverButton() {
        startOverButton.setFont(new Font("Bell MT", Font.BOLD, 25));
        startOverButton.setBackground(FOREGROUND_COLOR);
        startOverButton.setForeground(Color.BLACK);
        startOverButton.setFocusPainted(false);
        startOverButton.addActionListener(e -> startNewGame());
    }

    private void createTopPanel() {
        moveCountLabel = new JLabel();
        moveCountLabel.setForeground(FOREGROUND_COLOR);
        moveCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moveCountLabel.setFont(GameFrame.getSmallFont());
        moveCountLabel.setForeground(FOREGROUND_COLOR);

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.WHITE);
        statusPanel.add(timerLabel, BorderLayout.EAST);
        statusPanel.setLayout(new GridLayout(2, 0));
        statusPanel.add(moveCountLabel, BorderLayout.SOUTH);

        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBackground(FOREGROUND_COLOR);
        menuPanel.add(startOverButton, BorderLayout.CENTER);
        menuPanel.add(gridSizeSlider, BorderLayout.EAST);

        topPanel.setLayout(new GridLayout(2, 0));
        topPanel.add(menuPanel);
        topPanel.add(statusPanel);
    }

    private void createTimer() {
        timerLabel = new JLabel("");
        timerLabel.setFont(smallFont);
        timerLabel.setForeground(FOREGROUND_COLOR);
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
    }

    private void createGridSizeSlider() {
        gridSizeSlider = new JSlider(JSlider.HORIZONTAL, 2, 7, 4);
        gridSizeSlider.setMajorTickSpacing(1);
        gridSizeSlider.setFont(smallFont);
        gridSizeSlider.setPaintTicks(true);
        gridSizeSlider.setPaintLabels(true);
        gridSizeSlider.setBackground(FOREGROUND_COLOR);

        gridSizeSlider.addChangeListener(e -> {
            if (!gridSizeSlider.getValueIsAdjusting()) {
                gamePanel.getLogic().changeGridParameters(gridSizeSlider.getValue());
                gamePanel.setSizeOfTile(gridSizeSlider.getValue());
                gamePanel.paintComponent(gamePanel.getGraphics());
                startNewGame();
            }
        });
    }

    public static void updateMoveCountLabel() {
        moveCountLabel.setText(GameLogic.getMoveCount() > 0 ? "Antal moves: " + GameLogic.getMoveCount() : "");
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

    public static Font getSmallFont() {
        return smallFont;
    }

    public static Color getForegroundColor() {
        return FOREGROUND_COLOR;
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