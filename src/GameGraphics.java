import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    public static final Color AWESOME_COLOR = new Color(0x9e7bb5);

    public GameGraphics(int dimension, int margin, int gridSide) {

        logic = new GameLogic(gridSide);
        this.margin = margin;

        sizeOfGrid = (dimension - 2 * margin);
        sizeOfTile = sizeOfGrid / gridSide;

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setBackground(Color.WHITE);
        setForeground(AWESOME_COLOR);
        setFont(new Font("Serif", Font.BOLD, 50));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(logic.isSolved()){
                    logic.newGame();
                } else {
                    int clickX = e.getX() - margin;
                    int clickY = e.getY() - margin;

                }
            }

            });

    }


    public void buildGrid(Graphics2D graphics2D) {

        for (int i = 0; i < logic.getTiles().length; i++) {

            int column = i % logic.getGridSide();
            int row = i / logic.getGridSide();

            int xCoordinate = column * sizeOfTile + margin;
            int yCoordinate = row * sizeOfTile + margin;

            if (logic.getTiles()[i] == 0) {
                if (logic.isSolved()) {

                    //TODO: Change message type from JOptionPane.

                    JOptionPane.showMessageDialog(null, "Grattis, du vann!");
                }
                continue;
            }
            // for other tiles, we first set the color of the tile and fill it.
            graphics2D.setColor(getForeground());
            graphics2D.fillRoundRect(xCoordinate, yCoordinate, sizeOfTile, sizeOfTile, 75, 75);

            //we set the color again and draw the borders.
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRoundRect(xCoordinate, yCoordinate, sizeOfTile, sizeOfTile, 75, 75);

            graphics2D.setColor(Color.BLACK);
            drawCenteredString(graphics2D, String.valueOf(logic.getTiles()[i]), xCoordinate , yCoordinate);
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;  //Cast graphics object to Graphics2D object.
        //Add Anti aliasing to components, makes them smooth.
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        buildGrid(graphics2D);
    }
    private void drawCenteredString(Graphics2D graphics, String string, int xCoordinate, int yCoordinate) {

        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.drawString(string,  xCoordinate + (sizeOfTile - fontMetrics.stringWidth(string)) / 2,
                yCoordinate + (fontMetrics.getAscent() + (sizeOfTile - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2));
    }
}
