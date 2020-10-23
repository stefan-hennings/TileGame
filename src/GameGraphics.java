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
        sizeOfTile = sizeOfGrid / logic.getGridSide();

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setBackground(Color.WHITE);
        setForeground(AWESOME_COLOR);
        setFont(new Font("Serif", Font.BOLD, 50));


    }

    public void buildGrid(Graphics2D graphics2D) {
        for (int i = 0; i < logic.getTiles().length; i++) {
            int column = i % logic.getGridSide();
            int row = i / logic.getGridSide();

            int xCoordinate = column * sizeOfTile + margin;
            int yCoordinate = row * sizeOfTile + margin;

            if(logic.getTiles()[i]==0){
                if(logic.isSolved()){

                    //TODO: Change message type from JOptionPane.

                    JOptionPane.showMessageDialog(null, "Grattis, du vann!");
                }
                continue;
            }
        }
    }
}
