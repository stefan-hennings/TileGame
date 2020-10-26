import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    // TODO: 22-Oct-20 Write code
    private static ImageIcon icon = new ImageIcon("numberfifteen.png");
    private static JPanel southPanel = new JPanel();
    private static JPanel northPanel = new JPanel();
    private static JLabel movesText;
    private static final JButton startOverButton = new JButton("Starta om");


    public Game(){
        movesText = GameGraphics.getMovesText();
        southPanel = GameGraphics.getSouthPanel();
        southPanel.setBackground(Color.WHITE);
        northPanel.setLayout(new BorderLayout());
        setIconImage(icon.getImage());
        setResizable(false);
        setTitle("Världens bästa brickspel, typ");

        GameGraphics run = new GameGraphics(600,30,4);


        add(run, BorderLayout.CENTER);
        movesText.setForeground(GameGraphics.FOREGROUND_COLOR);
        southPanel.setLayout(new GridLayout(2,0));
        southPanel.add(movesText);
        northPanel.add(startOverButton, BorderLayout.NORTH);
        startOverButton.setFont(new Font("Serif", Font.BOLD, 25));
        startOverButton.setBackground(GameGraphics.FOREGROUND_COLOR);
        startOverButton.setForeground(Color.BLACK);
        startOverButton.addActionListener(e -> {
            run.callNewGame();
            run.repaint();
        });
        add(southPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

    }

    public static void main(String[] args) {
        new Game();
    }
}
