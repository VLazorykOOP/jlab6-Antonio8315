import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class task_1 extends JFrame {
    private DrawingPanel drawingPanel;
    private Color currentColor = Color.BLACK;
    private int currentStrokeSize = 5;
    public task_1() {
        setTitle("Drawing App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);
        createMenuBar();
        setVisible(true);
    }
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu colorMenu = new JMenu("Color");
        JMenuItem blackColor = new JMenuItem("Black");
        JMenuItem redColor = new JMenuItem("Red");
        JMenuItem greenColor = new JMenuItem("Green");
        JMenuItem blueColor = new JMenuItem("Blue");
        blackColor.addActionListener(e -> currentColor = Color.BLACK);
        redColor.addActionListener(e -> currentColor = Color.RED);
        greenColor.addActionListener(e -> currentColor = Color.GREEN);
        blueColor.addActionListener(e -> currentColor = Color.BLUE);
        colorMenu.add(blackColor);
        colorMenu.add(redColor);
        colorMenu.add(greenColor);
        colorMenu.add(blueColor);
        JMenu strokeMenu = new JMenu("Stroke");
        JMenuItem thinStroke = new JMenuItem("Thin (5px)");
        JMenuItem mediumStroke = new JMenuItem("Medium (10px)");
        JMenuItem thickStroke = new JMenuItem("Thick (15px)");
        thinStroke.addActionListener(e -> currentStrokeSize = 5);
        mediumStroke.addActionListener(e -> currentStrokeSize = 10);
        thickStroke.addActionListener(e -> currentStrokeSize = 15);
        strokeMenu.add(thinStroke);
        strokeMenu.add(mediumStroke);
        strokeMenu.add(thickStroke);
        menuBar.add(colorMenu);
        menuBar.add(strokeMenu);
        setJMenuBar(menuBar);
    }
    private class DrawingPanel extends JPanel {
        private ArrayList<Line> lines = new ArrayList<>();
        private Point startPoint = null;
        public DrawingPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (startPoint != null) {
                        lines.add(new Line(startPoint, e.getPoint(), currentColor, currentStrokeSize));
                        startPoint = null;
                        repaint();
                    }
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (startPoint != null) {
                        lines.add(new Line(startPoint, e.getPoint(), currentColor, currentStrokeSize));
                        startPoint = e.getPoint();
                        repaint();
                    }
                }
            });
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            for (Line line : lines) {
                g2.setColor(line.color);
                g2.setStroke(new BasicStroke(line.strokeSize));
                g2.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
            }
        }
    }
    private class Line {
        Point start, end;
        Color color;
        int strokeSize;
        public Line(Point start, Point end, Color color, int strokeSize) {
            this.start = start;
            this.end = end;
            this.color = color;
            this.strokeSize = strokeSize;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(task_1::new);
    }
}
