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
    private final int margin;
    private final int sizeOfGrid;
    public static final Color FOREGROUND_COLOR = new Color(0x9e7bb5);

    private static final JLabel movesText = new JLabel("");
    private static final JPanel southPanel = new JPanel();

    public GameGraphics(int dimension, int margin, int gridSide) {

        logic = new GameLogic(gridSide);
        this.margin = margin;

        sizeOfGrid = (dimension - 2 * margin);
        sizeOfTile = sizeOfGrid / gridSide;

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setBackground(Color.WHITE);
        setForeground(FOREGROUND_COLOR);
        setFont(new Font("Bell MT", Font.BOLD, 50));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                movesText.setText("");

                if(logic.isSolved()){
                    logic.newGame();
                } else {
                    int clickX = e.getX() - margin;
                    int clickY = e.getY() - margin;
                    logic.moveTiles(clickX, clickY, sizeOfGrid, sizeOfTile);

                    movesText.setHorizontalAlignment(SwingConstants.CENTER);
                    movesText.setFont(new Font("Bell MT", Font.BOLD, 15));
                    movesText.setForeground(FOREGROUND_COLOR);
                    if(logic.getMoveCount()>0) {
                        movesText.setText("Antal moves: " + logic.getMoveCount());
                    }
                    southPanel.setBackground(Color.WHITE);
                    southPanel.repaint();
                    logic.isSolved();
                }
                repaint();
            }
        });
        logic.newGame();
    }

    public void buildGrid(Graphics2D graphics2D) {

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
        graphics.drawString(string,  xCoordinate + (sizeOfTile - fontMetrics.stringWidth(string)) / 2,
                yCoordinate + (fontMetrics.getAscent() + (sizeOfTile - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2));
    }
    private void drawStartMessage(Graphics2D g) {
        if (logic.isSolved()) {
            g.setFont(getFont().deriveFont(Font.BOLD, 25));
            g.setColor(FOREGROUND_COLOR);
            String s = "Grattis, du vann! Klicka för nytt spel";
            g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2,
                    getHeight()-margin);
            repaint();
        }
    }

    public static JPanel getSouthPanel() {
        return southPanel;
    }

    public static JLabel getMovesText() {
        return movesText;
    }

    public void callNewGame(){
        movesText.setText("");
        logic.newGame();
    }

    public GameLogic getLogic() {
        return logic;
    }

    public void setSizeOfTile(int gridSide) {
         sizeOfTile = sizeOfGrid/gridSide;
    }
}
