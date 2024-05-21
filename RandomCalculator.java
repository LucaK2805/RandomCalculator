import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomCalculator {
    private JFrame frame;
    private JTextField textField;
    private String operator;
    private double num1, num2, result;
    private StringBuilder currentExpression;

    public RandomCalculator() {
        frame = new JFrame("Randomrechner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 450);
        frame.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setBackground(Color.GRAY);
        textField.setForeground(Color.WHITE);
        textField.setPreferredSize(new Dimension(300, 60));
        textField.setEditable(false);
        frame.add(textField, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 5, 5));
        panel.setBackground(new Color(50, 50, 50));

        String[] buttons = {
                "C", "←", "(", ")",
                "7", "8", "9", "÷",
                "4", "5", "6", "x",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "√", "^", "%"
        };

        List<String> buttonList = Arrays.asList(buttons);
        Collections.shuffle(buttonList);
        String[] shuffledButtons = buttonList.toArray(new String[0]);

        currentExpression = new StringBuilder();

        for (String text : shuffledButtons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.setBackground(new Color(80, 80, 80));
            button.setForeground(Color.WHITE);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                switch (command) {
                    case "C":
                        textField.setText("");
                        currentExpression.setLength(0);
                        break;
                    case "=":
                        currentExpression.append(textField.getText());
                        num2 = Double.parseDouble(textField.getText());
                        switch (operator) {
                            case "+":
                                result = num1 + num2;
                                break;
                            case "-":
                                result = num1 - num2;
                                break;
                            case "x":
                                result = num1 * num2;
                                break;
                            case "÷":
                                result = num1 / num2;
                                break;
                            case "^":
                                result = Math.pow(num1, num2);
                                break;
                            case "%":
                                result = num1 % num2;
                                break;
                            case "√":
                                result = Math.sqrt(num2);
                                break;
                        }
                        currentExpression.append("=");
                        currentExpression.append(result);
                        textField.setText(String.valueOf(result));
                        currentExpression.setLength(0);
                        break;
                    case "√":
                        operator = command;
                        currentExpression.append("√");
                        break;
                    case "^":
                    case "%":
                    case "+":
                    case "-":
                    case "x":
                    case "÷":
                        if (!textField.getText().isEmpty()) {
                            num1 = Double.parseDouble(textField.getText());
                            operator = command;
                            currentExpression.append(textField.getText());
                            currentExpression.append(command);
                            textField.setText("");
                        }
                        break;
                    case "←":
                        String text = textField.getText();
                        if (!text.isEmpty()) {
                            textField.setText(text.substring(0, text.length() - 1));
                            currentExpression.setLength(currentExpression.length() - 1);
                        }
                        break;
                    default:
                        textField.setText(textField.getText() + command);
                        currentExpression.append(command);
                        break;
                }
            } catch (NumberFormatException ex) {
                textField.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandomCalculator::new);
    }
}
