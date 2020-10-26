import javax.swing.*;

public class Game extends JFrame {
    // TODO: 22-Oct-20 Write code
    private static ImageIcon icon = new ImageIcon("numberfifteen.png");
    private static JPanel southPanel = new JPanel();
    private static JPanel northPanel = new JPanel();
    private static JLabel movesText;
    private static final JButton startOverButton = new JButton("Starta om");


    public Game(){
        setIconImage(icon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Världens bästa brickspel, typ");
        add(new GameGraphics(600,30, 4));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Game();
    }
}
