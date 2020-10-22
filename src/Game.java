import javax.swing.*;

public class Game extends JFrame {
    // TODO: 22-Oct-20 Write code
    private static ImageIcon icon = new ImageIcon("numberfifteen.png");

    public Game(){
        setIconImage(icon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Världens bästa brickspel, typ");
        add(new GameGraphics(600,30));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Game();
    }
}
