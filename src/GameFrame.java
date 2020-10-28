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
    private static  GamePanel gamePanel;
    private static JLabel timerLabel;
    private JPanel centerpanel = new JPanel();
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel cardPanel;
    private JButton menuButton = new JButton("Meny");
    private static JFrame gameFrame;
    private static JPanel statusPanel;

    public GameFrame() {
        gameFrame= new JFrame("Världens bästa brickspel, typ");
        gameFrame.setIconImage(icon.getImage());


        createTimer();

        createGridSizeSlider();

        createMenuButton();

        createTopPanel();

        createStartOverButton();

        gamePanel = new GamePanel(600, 30, gridSizeSlider.getValue());

        initializeCenterPanel();

        startNewGame();

        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    private void initializeCenterPanel(){
        cardPanel= new JPanel();
        cardPanel.setLayout(cardLayout);
        centerpanel.setLayout(new BorderLayout());
        centerpanel.add(topPanel, BorderLayout.NORTH);
        centerpanel.add(gamePanel, BorderLayout.CENTER);
        cardPanel.add(centerpanel, "1");
        cardPanel.add(new OptionsPanel(), "2");
        gameFrame.add(cardPanel, BorderLayout.CENTER);

    }

    private void createStartOverButton() {
        startOverButton.setFont(new Font("Bell MT", Font.BOLD, 25));
        startOverButton.setBackground(GamePanel.getColor()[OptionsPanel.getButton()+1]);
        startOverButton.setForeground(Color.BLACK);
        startOverButton.setFocusPainted(false);
        startOverButton.addActionListener(e -> startNewGame());
    }
    private void createMenuButton() {

        menuButton.setFont(new Font("Bell MT", Font.BOLD, 25));
        menuButton.setBackground(FOREGROUND_COLOR);
        menuButton.setForeground(Color.BLACK);
        menuButton.setFocusPainted(false);
        menuButton.addActionListener(e ->  cardLayout.show(cardPanel, "2"));
    }

    private void createTopPanel() {
        moveCountLabel = new JLabel();
        moveCountLabel.setForeground(FOREGROUND_COLOR);
        moveCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moveCountLabel.setFont(GameFrame.getSmallFont());
        moveCountLabel.setForeground(FOREGROUND_COLOR);

        statusPanel = new JPanel();
        statusPanel.setBackground(Color.WHITE);
        statusPanel.add(timerLabel, BorderLayout.EAST);
        statusPanel.setLayout(new GridLayout(2, 0));
        statusPanel.add(moveCountLabel, BorderLayout.SOUTH);

        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBackground(FOREGROUND_COLOR);
        menuPanel.add(startOverButton, BorderLayout.WEST);
        menuPanel.add(menuButton, BorderLayout.CENTER);
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

    private static void updateTimerLabel() {
        timerLabel.setText(String.format("Tid: %02d:%02d", minutes, seconds));
        if (hours > 0)
            timerLabel.setText(String.format("Tid: %02d:%02d:%02d", hours, minutes, seconds));
    }

    public static void startNewGame() {

            seconds = 0;
            minutes = 0;
            hours = 0;
            updateTimerLabel();
            gamePanel.callNewGame();
            timer.start();
            gamePanel.repaint();
            gameFrame.pack();

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

    public static CardLayout getCardLayout() {
        return cardLayout;
    }

    public static JPanel getCardPanel() {
        return cardPanel;
    }

    public static JPanel getTopPanel() {
        return topPanel;
    }

    public static JPanel getMenuPanel() {
        return menuPanel;
    }

    public static JPanel getStatusPanel() {
        return statusPanel;
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