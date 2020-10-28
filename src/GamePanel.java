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
public class GamePanel extends JPanel {

    GameLogic logic;
    private int sizeOfTile;
    private final int margin;
    private final int sizeOfGrid;
    private  static Color[] color = { new Color(0x7a4988), GameFrame.getForegroundColor(),
            new Color(0xC8A957), new Color(0xFFFFE0),
            new Color(0x16064), new Color(0xAFEEEE),
            new Color(0x2C041C), new Color(0x7a4988)};

    private final static Color[] menuColor = { GameFrame.getForegroundColor(),
            new Color(0xC8A957),
            new Color(0x16064),
            new Color(0x7a4988)};

    //TODO: Adding options for changing color?

    public GamePanel(int dimension, int margin, int gridSide) {

        logic = new GameLogic(gridSide);
        this.margin = margin;

        sizeOfGrid = (dimension - 2 * margin);
        sizeOfTile = sizeOfGrid / gridSide;


        setPreferredSize(new Dimension(dimension, dimension + margin));
        setBackground(Color.WHITE);
        setForeground(GameFrame.getForegroundColor());
        setFont(new Font("Bell MT", Font.BOLD, 50));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                if (logic.isSolved()) {
                    logic.newGame();
                } else {
                    int clickX = e.getX() - margin;
                    int clickY = e.getY() - margin;
                    logic.moveTiles(clickX, clickY, sizeOfGrid, sizeOfTile);

                    if (logic.isSolved()) {
                        GameFrame.getTimer().stop();
                        GameFrame.loadAndSaveHighscore();
                        GameFrame.getCardLayout().show(GameFrame.getCardPanel(), "4");
                    }
                }
                repaint();
            }
        });
        logic.newGame();
    }

    public void buildGrid(Graphics2D graphics2D) {
        int arc = sizeOfTile / 2;
        for (int i = 0; i < logic.getTiles().length; i++) {

            int column = i % logic.getGridSide();
            int row = i / logic.getGridSide();

            int xCoordinate = column * sizeOfTile + margin;
            int yCoordinate = row * sizeOfTile + margin;

            if (logic.getTiles()[i] == 0) {
                if (logic.isSolved()) {
                    graphics2D.setFont(new Font("Serif", Font.BOLD, 60));
                    graphics2D.setColor(Color.ORANGE);
                    drawCenteredString(graphics2D, new StringBuilder().appendCodePoint(0x0001F947).toString(), xCoordinate, yCoordinate);
                    GameFrame.getTimer().restart();
                }
                continue;
            }
            // for other tiles, we first set the color of the tile and fill it.

            GradientPaint gradientPaint = new GradientPaint(margin, 0, color[ColorPanel.getColor()-1], sizeOfTile / 2f + margin,
                    0, color[ColorPanel.getColor()], true);
            graphics2D.setPaint(gradientPaint);
            graphics2D.fillRoundRect(xCoordinate, yCoordinate, sizeOfTile, sizeOfTile, arc, arc);

            //we set the color again and draw the borders.
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRoundRect(xCoordinate, yCoordinate, sizeOfTile, sizeOfTile, arc, arc);

            graphics2D.setColor(Color.BLACK);
            drawCenteredString(graphics2D, String.valueOf(logic.getTiles()[i]), xCoordinate, yCoordinate);
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;  //Cast graphics object to Graphics2D object.
        //Add Anti aliasing to components, makes them smooth.
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        buildGrid(graphics2D);
        drawStartMessage(graphics2D);
    }

    private void drawCenteredString(Graphics2D graphics, String string, int xCoordinate, int yCoordinate) {

        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.drawString(string, xCoordinate + (sizeOfTile - fontMetrics.stringWidth(string)) / 2,
                yCoordinate + (fontMetrics.getAscent() + (sizeOfTile - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2));
    }

    private void drawStartMessage(Graphics2D g) {
        if (logic.isSolved()) {
            g.setFont(getFont().deriveFont(Font.BOLD, 25));
            g.setColor(color[ColorPanel.getColor()-1]);
            String s = "Grattis, du vann! Klicka för nytt spel";
            g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2,
                    getHeight() - margin);
            repaint();
        }
    }

    public void callNewGame() {
        logic.newGame();
        GameFrame.updateMoveCountLabel();
    }

    public static Color[] getColor() {
        return color;
    }

    public static Color[] getMenuColor() {
        return menuColor;
    }

    public GameLogic getLogic() {
        return logic;
    }

    public void setSizeOfTile(int gridSide) {
        sizeOfTile = sizeOfGrid / gridSide;
    }
}
