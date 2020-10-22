import javax.swing.*;
import java.awt.*;

/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-22
 * Time: 16:45
 * Project: Inlämning3GUI
 * Copyright: MIT
 */
public class GameGraphics extends JPanel {
    GameLogic logic;
    private int sizeOfTile;
    private int margin;
    private int sizeOfGrid;
    private static final Color AWESOME_COLOR = new Color(0x9e7bb5);

    public GameGraphics(int dimension, int margin) {
        logic = new GameLogic(4);
        this.margin = margin;

        sizeOfGrid = (dimension - 2 * margin);
        sizeOfTile = sizeOfGrid / logic.getSize();

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setBackground(Color.WHITE);
        setForeground(AWESOME_COLOR);
        setFont(new Font("Serif", Font.BOLD, 50));


    }

    public void drawGrid(Graphics2D graphics2D) {
        for (int i = 0; i < logic.getTiles().length; i++) {
            int column = i % logic.getSize();
            int row = i / logic.getSize();
        }
    }
}
