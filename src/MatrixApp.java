import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixApp extends JFrame {
    private JTextField nField;
    private JButton calculateButton, readFileButton;
    private JTable resultTable;
    private JTextArea matrixInputArea;

    public MatrixApp() {
        setTitle("Task_2");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("Enter n (<= 15):"));
        nField = new JTextField();
        panel.add(nField);

        matrixInputArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(matrixInputArea);
        panel.add(new JLabel("Enter matrix A and B elements (row-wise):"));
        panel.add(scrollPane);

        readFileButton = new JButton("Read from file");
        panel.add(readFileButton);

        calculateButton = new JButton("Calculate X");
        panel.add(calculateButton);

        resultTable = new JTable(1, 15); // Таблиця для результату X
        panel.add(new JLabel("Result vector X:"));
        panel.add(new JScrollPane(resultTable));

        add(panel, BorderLayout.CENTER);

        // Читання даних з файлу
        readFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(MatrixApp.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Scanner scanner = new Scanner(file);
                        StringBuilder sb = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            sb.append(scanner.nextLine()).append("\n");
                        }
                        matrixInputArea.setText(sb.toString());
                        scanner.close();
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "File not found.");
                    }
                }
            }
        });

        // "Calculate"
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(nField.getText());
                    if (n > 15) {
                        throw new IllegalArgumentException("n should be <= 15");
                    }

                    String[] lines = matrixInputArea.getText().split("\\n");
                    if (lines.length != 2 * n) {
                        throw new IllegalArgumentException("Invalid matrix input.");
                    }

                    int[][] A = new int[n][n];
                    int[][] B = new int[n][n];
                    for (int i = 0; i < n; i++) {
                        String[] aRow = lines[i].split("\\s+");
                        String[] bRow = lines[i + n].split("\\s+");
                        for (int j = 0; j < n; j++) {
                            A[i][j] = Integer.parseInt(aRow[j]);
                            B[i][j] = Integer.parseInt(bRow[j]);
                        }
                    }

                    // Обчислення X
                    int[] X = calculateX(A, B, n);
                    for (int i = 0; i < n; i++) {
                        resultTable.setValueAt(X[i], 0, i);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage());
                } catch (InvalidMatrixException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    // Метод для обчислення X
    private int[] calculateX(int[][] A, int[][] B, int n) throws InvalidMatrixException {
        int[] X = new int[n];
        for (int i = 0; i < n; i++) {
            X[i] = 1;
            for (int j = 0; j < n; j++) {
                if (A[i][j] <= B[i][j]) {
                    X[i] = 0;
                    break;
                }
            }
            // Якщо числа у першому стовпці матриці A < від чисел першого стовпця у B - виняток
            if (X[i] == 0 && A[i][0] < B[i][0]) {
                throw new InvalidMatrixException("Invalid matrix row: A[" + i + "] < B[" + i + "]");
            }
        }
        return X;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MatrixApp().setVisible(true);
        });
    }
}

// Власне виключення
class InvalidMatrixException extends ArithmeticException {
    public InvalidMatrixException(String message) {
        super(message);
    }
}
