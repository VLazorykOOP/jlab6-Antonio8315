import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private Timer timer;
    private double q = 200;  // Довжина відрізка
    private double w = 1;    // Константа
    private double t = 0;    // Час
    private int x = 0;       // Координата точки
    private boolean isRunning = false;

    private JTextField qField, wField;
    private JButton startButton, stopButton;
    private JPanel panel;

    public Main() {
        System.out.println(" Java Lab #6 ");
        setTitle("Task_1");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Поля для введення q і w
        qField = new JTextField(Double.toString(q), 5);
        wField = new JTextField(Double.toString(w), 5);

        // старт / зупинка
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        // Панель для малювання
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                g.drawLine(50, 100, 250, 100);
                g.fillOval(x, 95, 10, 10);
            }
        };

        // Лейаут
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("q:"));
        controlPanel.add(qField);
        controlPanel.add(new JLabel("w:"));
        controlPanel.add(wField);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        add(panel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Таймер
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t += 0.1;
                x = (int) (50 + q * (1 + Math.cos(w * t)) / 2);
                panel.repaint();
            }
        });

        // Обробка "Start"
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    try {
                        q = Double.parseDouble(qField.getText());
                        w = Double.parseDouble(wField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers for q and w.");
                        return;
                    }
                    isRunning = true;
                    timer.start();
                }
            }
        });

        // Обробка "Stop"
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRunning = false;
                timer.stop();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}

