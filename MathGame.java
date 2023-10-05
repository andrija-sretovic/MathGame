import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MathGame {

    private JFrame frame = new JFrame("MATHS");

    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    private JButton button1 = new JButton("EASY");
    private JButton button2 = new JButton("MEDIUM");
    private JButton button3 = new JButton("HARD");

    private JLabel titleLabel = new JLabel("Title", JLabel.CENTER);

    private JPanel rowsPanel = new JPanel();

    private JPanel[] rowPanels = new JPanel[5];
    private JLabel[] label1s = new JLabel[5];
    private JLabel[] label2s = new JLabel[5];
    private JLabel[] label3s = new JLabel[5];
    private JTextField[] textFields = new JTextField[5];
    private JLabel[] label4s = new JLabel[5];

    private JPanel bottomPanel = new JPanel();

    private JButton leftButton = new JButton("SUBMIT");
    private JLabel rightLabel = new JLabel("SCORE", JLabel.RIGHT);

    Numbers num = new Numbers();
    private int maxNum = 100;
    private String[] plus = {"+"};
    private String[] plusMinus = {"+", "-"};
    private String[] allSigns = {"+", "-", "*", "/"};
    private int correct;

    public MathGame(){

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        mainPanel.setLayout(new BorderLayout());

        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(button1);
        button1.addActionListener(forButton1);
        buttonPanel.add(button2);
        button2.addActionListener(forButton2);
        buttonPanel.add(button3);
        button3.addActionListener(forButton3);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.CENTER);

        rowsPanel.setLayout(new GridLayout(5, 1));

        for (int i = 0; i < 5; i++) {
            JPanel row = new JPanel();
            row.setLayout(new FlowLayout());

            label1s[i] = new JLabel("A  ");
            label2s[i] = new JLabel("+-*/");
            label3s[i] = new JLabel("  B");
            textFields[i] = new JTextField(5);
            label4s[i] = new JLabel("???");

            row.add(label1s[i]);
            row.add(label2s[i]);
            row.add(label3s[i]);
            row.add(textFields[i]);
            row.add(label4s[i]);

            rowPanels[i] = row;

            rowsPanel.add(row);
        }

        mainPanel.add(rowsPanel, BorderLayout.CENTER);

        bottomPanel.setLayout(new BorderLayout());

        bottomPanel.add(leftButton, BorderLayout.WEST);
        leftButton.setEnabled(false);
        leftButton.addActionListener(forSubmit);
        bottomPanel.add(rightLabel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    ActionListener forButton1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetBoard();
            disableButtons();
            setSigns(plus);
            checkSign();
        }
    };

    ActionListener forButton2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetBoard();
            disableButtons();
            setSigns(plusMinus);
            checkSign();
        }
    };

    ActionListener forButton3 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetBoard();
            disableButtons();
            setSigns(allSigns);
            checkSign();
        }
    };

    ActionListener forSubmit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean errorFound = false;
            for (int j = 0; j < textFields.length; j++) {
                label4s[j].setText("???");
                String input = textFields[j].getText();
                if (input.isBlank()) {
                    label4s[j].setText("Please enter a number");
                    textFields[j].setText("");
                    errorFound = true;
                } else if (!num.isNumeric(input)) {
                    label4s[j].setText("This is not a number");
                    textFields[j].setText("");
                    errorFound = true;
                } else if (Integer.parseInt(input) > maxNum) {
                        label4s[j].setText("No numbers over 100");
                        textFields[j].setText("");
                        errorFound = true;
                    }
                }

                if (!errorFound) {
                    disableSubmit();

                    correct = 0;
                    for (int i = 0; i < label2s.length; i++) {
                        if (label2s[i].getText().equals("+")) {
                            int res = Integer.parseInt(textFields[i].getText());
                            int a = Integer.parseInt(label1s[i].getText());
                            int b = Integer.parseInt(label3s[i].getText());
                            if (a + b == res) {
                                label4s[i].setText("Correct");
                                correct++;
                            } else {
                                        label4s[i].setText("Correct answer is " + (a + b));
                                    }
                        } else if (label2s[i].getText().equals("-")) {
                            int res = Integer.parseInt(textFields[i].getText());
                            int a = Integer.parseInt(label1s[i].getText());
                            int b = Integer.parseInt(label3s[i].getText());
                            if (a - b == res) {
                                label4s[i].setText("Correct");
                                correct++;
                            } else {
                                label4s[i].setText("Correct answer is " + (a - b));
                            }
                        } else if (label2s[i].getText().equals("*")) {
                            int res = Integer.parseInt(textFields[i].getText());
                            int a = Integer.parseInt(label1s[i].getText());
                            int b = Integer.parseInt(label3s[i].getText());
                            if (a * b == res) {
                                label4s[i].setText("Correct");
                                correct++;
                            } else {
                                label4s[i].setText("Correct answer is " + (a * b));
                            }
                        } else if (label2s[i].getText().equals("/")) {
                            int res = Integer.parseInt(textFields[i].getText());
                            int a = Integer.parseInt(label1s[i].getText());
                            int b = Integer.parseInt(label3s[i].getText());
                            if (res * b == a) {
                                label4s[i].setText("Correct");
                                correct++;
                            } else {
                                label4s[i].setText("Correct answer is " + (a / b));
                            }
                        }
                    }
                    checkScore(correct);
                }
        }
    };

    private void disableButtons(){
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        leftButton.setEnabled(true);
    }

    private void disableSubmit() {
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        leftButton.setEnabled(false);
    }

    private void setSigns(String[] arr) {
        for (JLabel label2 : label2s) {
            label2.setText(arr[num.getRandomNum(arr.length)]);
        }

    }

    private void checkSign () {
        for (int i = 0; i < label2s.length; i++) {
            if (label2s[i].getText().equals("+")) {
                int rand1 = num.getRandomNum(maxNum);
                int rand2 = num.getRandomNum(maxNum - rand1) + 1;
                label1s[i].setText(num.numToString(rand1));
                label3s[i].setText(num.numToString(rand2));
            } else if (label2s[i].getText().equals("-")) {
                int rand1 = num.getRandomNum(maxNum - 10) + 10;
                int rand2 = num.getRandomNum(rand1) + 1;
                label1s[i].setText(num.numToString(rand1));
                label3s[i].setText(num.numToString(rand2));
            } else if (label2s[i].getText().equals("*")) {
                int rand1 = num.getRandomNum(maxNum / 2) + 1;
                int rand2 = num.getRandomNum(maxNum / rand1) + 1;
                label1s[i].setText(num.numToString(rand1));
                label3s[i].setText(num.numToString(rand2));
            } else if (label2s[i].getText().equals("/")) {
                int rand2 = num.getRandomNum(maxNum / 4) + 1;
                int rand1 = num.getRandomNum((maxNum / rand2) + 1) * rand2;
                label1s[i].setText(num.numToString(rand1));
                label3s[i].setText(num.numToString(rand2));
            }
        }
    }

    private void resetBoard() {
        for (int i = 0; i < textFields.length; i++) {
            textFields[i].setText("");
            label4s[i].setText("???");
            rightLabel.setText("SCORE");
        }
    }

    private void checkScore (int num) {
        if (num == 5) {
            rightLabel.setText(num + " correct " + "Well done!!!");
        } else if (num >= 3) {
            rightLabel.setText(num + " correct " + "ALMOST THERE!?!?");
        } else {
            rightLabel.setText(num + " correct " + "WORK HARDER!!!");
        }
    }

}

