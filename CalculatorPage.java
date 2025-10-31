//import javax.swing.*;
//import java.awt.*;
//
//public class CalculatorPage {
//    public static JPanel calculatorPage(){
//        JPanel calculatorPage=new JPanel();
//        calculatorPage.setLayout(new BoxLayout(calculatorPage,BoxLayout.Y_AXIS));
//        calculatorPage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        calculatorPage.setBackground(new Color(245,245,255));
//        JLabel lbl=new JLabel("This is Calculator Page");
//        calculatorPage.add(lbl);
//        return calculatorPage;
//    }
//}
package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class CalculatorPage implements ActionListener {

    JButton sipButton,cagrButton,fvButton,CcButton,emiButton;
    static CardLayout cardLayout=new CardLayout();
    static JPanel cardPanel;

    public CalculatorPage(){
        cardPanel=new JPanel(cardLayout);
        JPanel calculatorPage=CalculatorPanel();
        JPanel sipPage=new SIP().sipCalculatorPanel();
        JPanel cagrPage=new CAGR().cagrCalculatorPanel();
        JPanel futurePage=new FutureValue().futureCalculatorPanel();
        JPanel commerce=new CommercialCalculator().getCommercialCalculator();
        JPanel emi=new EmiCalculator().emiCalculatorPanel();

        cardPanel.add(calculatorPage,"calculatorPage");
        cardPanel.add(sipPage,"sipPage");
        cardPanel.add(cagrPage,"cagrPage");
        cardPanel.add(futurePage,"futurePage");
        cardPanel.add(commerce,"commerce");
        cardPanel.add(emi,"emi");

        CardMethod();
    }
    public JPanel CardMethod(){
        return cardPanel;
    }
    public JPanel CalculatorPanel() {
        JPanel calculatorPage=new JPanel();
        // Set panel background color (RGB: 245, 245, 255)
        calculatorPage.setBackground(new Color(245, 245, 255));
        calculatorPage.setLayout(new GridBagLayout()); // Center everything

        // Create buttons
        sipButton = createStyledButton("SIP Calculator", new Color(100, 149, 237));       // Cornflower Blue
        cagrButton = createStyledButton("CAGR Calculator", new Color(60, 179, 113));      // Medium Sea Green
        fvButton = createStyledButton("Future Value Calculator", new Color(255, 165, 0)); // Orange
        CcButton = createStyledButton("Commercial Calculator", new Color(245, 107, 229)); // Orange
        emiButton = createStyledButton("EMI Calculator", new Color(238, 73, 73)); // Orange

        // Add action listeners
        sipButton.addActionListener(this);
        cagrButton.addActionListener(this);
        fvButton.addActionListener(this);
        CcButton.addActionListener(this);
        emiButton.addActionListener(this);

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20); // spacing between buttons

        gbc.gridy = 0;
        calculatorPage.add(sipButton, gbc);
        gbc.gridy = 1;
        calculatorPage.add(cagrButton, gbc);
        gbc.gridy = 2;
        calculatorPage.add(fvButton, gbc);
        gbc.gridy = 3;
        calculatorPage.add(CcButton, gbc);
        gbc.gridy = 4;
        calculatorPage.add(emiButton, gbc);

        return calculatorPage;
    }

    private static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(220, 40));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sipButton){
            cardLayout.show(cardPanel,"sipPage");
        };
        if(e.getSource()==cagrButton){
            cardLayout.show(cardPanel,"cagrPage");
        };
        if(e.getSource()==fvButton){
            cardLayout.show(cardPanel,"futurePage");
        }
        if(e.getSource()==CcButton){
            cardLayout.show(cardPanel,"commerce");
        }
        if(e.getSource()==emiButton){
            cardLayout.show(cardPanel,"emi");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        CalculatorPage ob1=new CalculatorPage();
        frame.setContentPane(ob1.CardMethod());

        //Logo at the JFrame Title
        String logoPath = "calculator.jpg";
        ImageIcon icon = new ImageIcon(logoPath);
        Image scaledImage = icon.getImage().getScaledInstance(52, 52, Image.SCALE_SMOOTH);
        ImageIcon perfectImage=new ImageIcon(scaledImage);
        frame.setIconImage(perfectImage.getImage());

        frame.setVisible(true);
    }

    static int decimalCount;
    static int i,j;
    static boolean hasDecimal;
    public static void rateLogic(JTextField txt, KeyEvent e){
        if(e.getSource()==txt){
            if(txt.getText().isEmpty()){
                decimalCount=0;
                i=2;
                hasDecimal=false;
            }
            char c=e.getKeyChar();

            if(!Character.isDigit(c)){
                if(c=='.' && !hasDecimal){
                    e.setKeyChar(c);
                    decimalCount++;
                    i=5;
                }
                else e.consume();
            }
            if(c=='.' && decimalCount>1){
                e.consume();
            }
            if(txt.getText().length()>=i){
                e.consume();
            }
        }
    }
    public static void amountLogic(JTextField txt, KeyEvent e){
        if(e.getSource()==txt){
            if(txt.getText().isEmpty()){
                j=8;
                decimalCount=0;
            }
            char c=e.getKeyChar();
            if(!Character.isDigit(c)){
                if(c=='.' && !hasDecimal){
                    e.setKeyChar(c);
                    decimalCount++;
                    j=11;
                }
                else e.consume();
            }
            if(c=='.' && decimalCount>1){
                e.consume();
            }
            if(txt.getText().length()>=j){
                e.consume();
            }
        }
    }
}