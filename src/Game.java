import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Game extends JFrame {
    // TODO: 22-Oct-20 Write code
    private static final ImageIcon icon = new ImageIcon("numberfifteen.png");
    private static JPanel southPanel = new JPanel();
    private static final JPanel northPanel = new JPanel();
    private static final JPanel topPanel = new JPanel();
    private static JLabel movesText;
    private static final JButton startOverButton = new JButton("Starta om");

    private static JSlider slider;
    private final GameGraphics run;



    public Game(){

        slider = new JSlider(JSlider.HORIZONTAL, 2,7, 4);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(GameGraphics.FOREGROUND_COLOR);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!slider.getValueIsAdjusting()){
                    run.getLogic().changeGridParameters(slider.getValue());
                    run.setSizeOfTile(slider.getValue());
                    run.paintComponent(run.getGraphics());
                    run.callNewGame();
                    repaint();
                    pack();
                }
            }
        });
        movesText = GameGraphics.getMovesText();
        southPanel = GameGraphics.getSouthPanel();
        southPanel.setBackground(Color.WHITE);
        northPanel.setLayout(new BorderLayout());
        setIconImage(icon.getImage());
        setTitle("Världens bästa brickspel, typ");

        run = new GameGraphics(600,30, slider.getValue());





        add(run, BorderLayout.CENTER);
        movesText.setForeground(GameGraphics.FOREGROUND_COLOR);
        southPanel.setLayout(new GridLayout(2,0));
        southPanel.add(movesText, BorderLayout.SOUTH);
        northPanel.add(startOverButton, BorderLayout.CENTER);
        northPanel.add(slider, BorderLayout.EAST);
        startOverButton.setFont(new Font("Serif", Font.BOLD, 25));
        startOverButton.setBackground(GameGraphics.FOREGROUND_COLOR);
        startOverButton.setForeground(Color.BLACK);
        startOverButton.addActionListener(e -> {

            run.callNewGame();
            run.repaint();
        });
        topPanel.setLayout(new GridLayout(2,0));
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
}
