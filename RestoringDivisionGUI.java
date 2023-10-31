import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestoringDivisionGUI extends JFrame implements ActionListener {
    private JTextField dividendField;
    private JTextField divisorField;
    private JTextArea outputArea;
    private Timer timer;
    private int step;

    public RestoringDivisionGUI() {
        setTitle("Restoring Division");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel dividendLabel = new JLabel("Enter 4-bit dividend (D): ");
        dividendField = new JTextField(10);
        add(dividendLabel);
        add(dividendField);

        JLabel divisorLabel = new JLabel("Enter 4-bit divisor (M): ");
        divisorField = new JTextField(10);
        add(divisorLabel);
        add(divisorField);

        JButton calculateButton = new JButton("Start Division");
        calculateButton.addActionListener(this); // Register this class as the ActionListener
        add(calculateButton);

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        add(outputArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start Division")) {
            performRestoringDivision();
        }
    }

    private void performRestoringDivision() {
        try {
            int dividend = Integer.parseInt(dividendField.getText());
            int divisor = Integer.parseInt(divisorField.getText());

            final int[] quotient = { 0 };
            final int[] remainder = { 0 };
            step = 0;

            outputArea.setText(""); // Clear previous output

            outputArea.append("Dividend (D): " + Integer.toBinaryString(dividend) + "\n");
            outputArea.append("Divisor (M): " + Integer.toBinaryString(divisor) + "\n");

            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (step < 4) {
                        remainder[0] = (remainder[0] << 1) | ((dividend >> (3 - step)) & 1);

                        int subtract = 0;
                        if (remainder[0] >= divisor) {
                            remainder[0] -= divisor;
                            subtract = 1;
                        }

                        quotient[0] = (quotient[0] << 1) | subtract;
                        outputArea.append("\nStep " + (step + 1) + ": ");
                        outputArea.append("Quotient = " + Integer.toBinaryString(quotient[0]) + ", Remainder = "
                                + Integer.toBinaryString(remainder[0]));
                        step++;

                        if (step == 4) {
                            outputArea.append("\n\nQuotient: " + Integer.toBinaryString(quotient[0]) + "\n");
                            outputArea.append("Remainder: " + Integer.toBinaryString(remainder[0]) + "\n");
                            timer.stop();
                        }
                    }
                }
            });

            timer.start();
        } catch (NumberFormatException ex) {
            outputArea.setText("Please enter valid binary numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RestoringDivisionGUI gui = new RestoringDivisionGUI();
            gui.setVisible(true);
        });
    }
}
