package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CommercialCalculator implements ActionListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 500);
        frame.setLocationRelativeTo(null);

        CommercialCalculator ob1=new CommercialCalculator();
        frame.add(ob1.getCommercialCalculator());
        frame.setVisible(true);
    }

    JButton[] allButtons;
    JTextField screen;
    JButton btnMa,btnMs,btnMr,btnHist;
    ArrayList<String> history=new ArrayList<>(); int index=0;

    static CardLayout cardLayout=new CardLayout();
    static JPanel cardPanel;

    public JPanel getCommercialCalculator(){
        cardPanel=new JPanel(cardLayout);
        JPanel calc=createUI();
        JPanel history=historyPanel();

        cardPanel.add(calc,"calculator");
        cardPanel.add(history,"historyPage");
        return cardPanel;

    }

    public JPanel createUI() {
        // Colors
        Color panelBg = new Color(238, 238, 238); // Soft lavender
        Color buttonBg = new Color(207, 207, 255); // Slightly darker for contrast
        Color screenBg = new Color(255, 255, 255); // White screen


        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(panelBg);

        // Screen Panel (30% height)
        JPanel screenPanel = new JPanel();
        screenPanel.setPreferredSize(new Dimension(350, (int) (500 * 0.2)));
        screenPanel.setBackground(screenBg);
        screenPanel.setLayout(new BorderLayout());

        screen = new JTextField();
        screen.setFont(new Font("Arial", Font.BOLD, 28));
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setEditable(false);
        screen.setBackground(screenBg);
        screen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        screenPanel.add(screen, BorderLayout.CENTER);

        // Button Panel (70% height)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 8, 8)); // 5 rows x 4 columns
        buttonPanel.setBackground(panelBg);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
                "←", "C", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", "del"
        };

        Object[] button = {"btnBack", "btnClear", "btnModulus", "btnDivision", "btn7", "btn8", "btn9", "btnMul", "btn4", "btn5", "btn6", "btnSub", "btn1", "btn2", "btn3", "btnAdd", "btn0", "btnDecimal", "btnEqual", "btnDel"};
        allButtons = new JButton[20];
        btnMa=new JButton("M+");
        btnMs=new JButton("M-");
        btnMr=new JButton("MR");
        btnHist=new JButton("⌛");
        int i = 0;
        for (String text : buttons) {
            if (!text.isEmpty()) {
                button[i] = new JButton(text);
                allButtons[i] = (JButton) buttonPanel.add((Component) button[i]);
                i++;
            } else {
                buttonPanel.add(new JLabel()); // Empty space
            }
        }

        for (JButton btn : allButtons) {
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setBackground(buttonBg);
            btn.setFocusPainted(false);
            btn.addActionListener(this);
        }
        allButtons[0].setBackground(new Color(239, 138, 138));

        btnMa.setFont(new Font("Arial", Font.BOLD, 20));
        btnMa.setBackground(buttonBg);
        btnMa.setFocusPainted(false);
        btnMa.addActionListener(this);

        btnMs.setFont(new Font("Arial", Font.BOLD, 20));
        btnMs.setBackground(buttonBg);
        btnMs.setFocusPainted(false);
        btnMs.addActionListener(this);

        btnMr.setFont(new Font("Arial", Font.BOLD, 20));
        btnMr.setBackground(buttonBg);
        btnMr.setFocusPainted(false);
        btnMr.addActionListener(this);

        btnHist.setBackground(buttonBg);
        btnHist.setFocusPainted(false);
        btnHist.addActionListener(this);

        buttonPanel.add(btnMa);
        buttonPanel.add(btnMs);
        buttonPanel.add(btnMr);
        buttonPanel.add(btnHist);

        // Assemble
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    String result="";
    double memory=0;
    boolean isMemoryPressed=false;

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] value = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "+", "-", "*", "/", "%"};

        if(isMemoryPressed){
            isMemoryPressed=false;
            screen.setText("");
        }
        //****** String Array contains respective value of Button *******
        String old;
        for (JButton btn : allButtons) {
            for (int i = 0; i < 10; i++) {
                if (btn.getText().equalsIgnoreCase(value[i])) {
                    if (e.getSource() == btn) {
                        old = screen.getText();
                        screen.setText(old + btn.getText());
                    }
                }
            }
        }

        //******** Below For Loop has been used to just display the value of the operator Button like .,+,/,etc. ********
        old = screen.getText();
        for (JButton btn : allButtons) {
            for (int i = 10; i < value.length; i++) {
                if (btn.getText().equalsIgnoreCase(value[i])) {
                    if (e.getSource() == btn && !old.equalsIgnoreCase("")) {
                        if (!old.endsWith("+") && !old.endsWith("/") && !old.endsWith("*") &&
                                !old.endsWith("-") && !old.endsWith("%") && !old.endsWith(".")) {
                            screen.setText(old + btn.getText());
                        }
                    }
                }
            }
        }

        if (e.getSource() == allButtons[1]) {
            screen.setText("");
        }
        if(e.getSource()==allButtons[allButtons.length-2]){ //Equal to Button
            String expression=screen.getText();
            result=String.valueOf(evaluate(expression));
            if(result.substring(result.length()-2,result.length()).equalsIgnoreCase(".0")){
                result=result.substring(0,result.length()-2);
            }

            try {
                history.add(expression + " = " + result);
                index++;
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
            screen.setText(result);
            isMemoryPressed=true;
        }
        if(e.getSource()==allButtons[allButtons.length-1]){ //Delete Button
            String text=String.valueOf(screen.getText());
            screen.setText(text.substring(0,text.length()-1));
        }
        if(e.getSource()==btnMa){
            allButtons[allButtons.length-2].doClick();
            result=screen.getText();
            if(!result.equalsIgnoreCase("")) memory+= Double.parseDouble(result);
            isMemoryPressed=true;
        }
        if(e.getSource()==btnMs){
            allButtons[allButtons.length-2].doClick();
            result=screen.getText();
            if(!result.equalsIgnoreCase("")) memory-= Double.parseDouble(result);
            isMemoryPressed=true;
        }
        if(e.getSource()==btnMr){
            screen.setText("");
            screen.setText(String.valueOf(memory));
            isMemoryPressed=true;
        }
        if(e.getSource()==btnHist){
            historyPanel();
            cardLayout.show(cardPanel,"historyPage");
        }
        if(e.getSource()==allButtons[0]){
            CalculatorPage.cardLayout.show(CalculatorPage.cardPanel,"calculatorPage");
        }
    }

    JPanel historyPanel=new JPanel();
    public JPanel historyPanel(){
        historyPanel.removeAll();
        historyPanel.setLayout(new BoxLayout(historyPanel,BoxLayout.Y_AXIS));
        historyPanel.setPreferredSize(new Dimension(350,500));
        historyPanel.setBackground(new Color(245, 245, 255));

        JPanel scrollPanel=new JPanel();
        scrollPanel.removeAll();
        scrollPanel.setLayout(new BoxLayout(scrollPanel,BoxLayout.Y_AXIS));
        scrollPanel.setPreferredSize(new Dimension(350,500));
        scrollPanel.setBackground(new Color(245, 245, 255));

        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        historyPanel.add(scrollPane);

        int i=1;
        for (String a: history){
            JPanel historyRow=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
            historyRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            historyRow.setBackground(new Color(214, 214, 255));
            historyRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel lblExpression=new JLabel(i+". "+a);
            historyRow.add(lblExpression);

            scrollPanel.add(historyRow);
            scrollPanel.add(Box.createVerticalStrut(8));

            scrollPanel.revalidate();
            scrollPanel.repaint();
            i++;
        }

        if (history.isEmpty()) {
            JPanel historyRow=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
            historyRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            historyRow.setBackground(new Color(214, 214, 255));
            historyRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel emptyLabel = new JLabel("No history available.");
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            historyRow.add(emptyLabel);

            scrollPanel.add(historyRow);
            scrollPanel.add(Box.createVerticalStrut(8));
            scrollPanel.revalidate();
            scrollPanel.repaint();
        }

        JPanel buttonRow=new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonRow.setBackground(new Color(214, 214, 255));

        JButton btnCancel=new JButton("Cancel");
        JButton btnClear=new JButton("Clear");

        btnCancel.setBackground(new Color(245,0,0));
        btnCancel.setForeground(new Color(255, 255, 255));
        btnClear.setBackground(new Color(245,0,0));
        btnClear.setForeground(new Color(255, 255, 255));

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"calculator");
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                history.clear();
                historyPanel();
            }
        });

        buttonRow.add(btnCancel);
        buttonRow.add(btnClear);
        scrollPanel.add(buttonRow);

        return historyPanel;
    }
    public static String evaluate(String expression) {
        char[] digits = expression.toCharArray(); //Converting the Complete TextField Statement into Character Array

        //********** Declaration of number and sign array *********8
        double[] number = new double[10];
        double[] sign = new double[10];
        int index = 0;
        double num;

        //Finding out each number and sign from the expression and storing in its respective array.
        String each_number = "";
        int point_came = 0; //I declared this so I can perform certain operation with decimal number

        //This complete Loop will separate each sign and each number and storing in its respective array.
        for (int i = 0; i < digits.length; i++) {
            if ((digits[i] != '+' && digits[i] != '-' && digits[i] != '/' && digits[i] != '*' && digits[i] != '%'))
            {
                if (digits[i] == '.') {
                    point_came = i; //Storing Position where point has been came in the expression.
                }
                else {
                    each_number += digits[i];
                }
                //Below If conditional statement is just used to find out the decimal position of number which occur in the last position of expression
                if ((i + 1) == digits.length && point_came != 0) {
                    point_came = i - point_came;
                }
            } else {
                //Below If statement will execute where any of the 5 sign has occured
                if ((i + 1) != digits.length && point_came != 0) {
                    point_came = i - 1 - point_came; // 2 1 . 2 1 +
                }
                num = Integer.parseInt(each_number); //All sets of digits appear before any sign occur is stored all together in num variable Eg: 12.29 digit num mai 1229 se stored hoga

                //Converting Integer number i.e. 1229 into an 12.29 from the given below conditional statement.
                if (point_came == 1) {
                    number[index] = (double) num / 10;
                } else if (point_came == 2) {
                    number[index] = (double) num / 100;
                } else if (point_came == 3) {
                    number[index] = (double) num / 1000;
                } else if (point_came == 4) {
                    number[index] = (double) num / 10000;
                } else {
                    number[index] = num;
                }

                sign[index] = digits[i];
                index++;
                each_number = "";
                point_came = 0;
            }
        }
        //This is used because in the last Iteration of Loop Last number of expression isn't used in its array that's why below conditional statement is used.
        num = Integer.parseInt(each_number);
        if (point_came == 1) {
            number[index] = (double) num / 10;
        } else if (point_came == 2) {
            number[index] = (double) num / 100;
        } else if (point_came == 3) {
            number[index] = (double) num / 1000;
        } else if (point_came == 4) {
            number[index] = (double) num / 10000;
        } else {
            number[index] = num;
        }

        return getResult(number, sign);
    }

    private static String getResult(double[] number, double[] sign) {
        double result= number[0];
        double each_part;
        boolean insideLoop=false;
        /*
        Here I have noted when I stored the sign in the character array and I printed its Integer value I got the below given value.
        //+ = 43, - = 45, * = 42, / = 47, % = 37
        So, If between two numbers + is used its Integer value 43 is stored in Double array.
        */
        for(int i=0;i<10;i++){
            if(number[i] != 0 && number[i+1] != 0){
                insideLoop=true;
                //Below If statement represent Modulus Logic
                if(sign[i] == 37){
                    each_part=result % number[i+1];
                    result=each_part;
                }
                //Below If statement represent Multiplication Logic
                if (sign[i] == 42) {
                    each_part = result * number[i + 1];
                    result = each_part;
                }
                //Below If statement represent Division Logic
                if (sign[i] == 47) {
                    if(number[i+1]!=0) {
                        each_part = result / number[i + 1];
                        result = each_part;
                    }
                }
                //Below If statement represent Addition Logic
                if (sign[i] == 43) {
                    each_part = result + number[i + 1];
                    result = each_part;
                }
                //Below If statement represent Subtraction Logic
                if (sign[i] == 45) {
                    each_part = result - number[i + 1];
                    result = each_part;
                }
            }
        }
        if(insideLoop) return String.valueOf(result);
        else return "Infinite";
    }

}