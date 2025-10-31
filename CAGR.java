package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CAGR implements ActionListener, KeyListener {

    static JTextField txtPrincipal,txtMaturity;
    public JPanel cagrCalculatorPanel() {
        JPanel cagrPanel=new JPanel();
        cagrPanel.setLayout(new BoxLayout(cagrPanel, BoxLayout.Y_AXIS));
        cagrPanel.setBackground(new Color(245, 245, 255)); // Soft background

        // 1️⃣ Title Panel
        cagrPanel.add(createCenteredLabelPanel("CAGR Calculator"));

        // 2️⃣ Principal Input
        JPanel principalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        principalPanel.setOpaque(false);

        JLabel lblPrincipal = new JLabel("Principal: ");
        txtPrincipal = new JTextField(15);
        txtPrincipal.setPreferredSize(new Dimension(200,35));
        txtPrincipal.addKeyListener(this);

        lblPrincipal.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblPrincipal.setPreferredSize(new Dimension(130,25));
        principalPanel.add(lblPrincipal,FlowLayout.LEFT);
        principalPanel.add(txtPrincipal);
        cagrPanel.add(principalPanel);

        // 3️⃣ ROI Input
        JPanel maturityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        maturityPanel.setOpaque(false);

        JLabel lblMaturity = new JLabel("Maturity Amount: ");
        txtMaturity = new JTextField(15);
        txtMaturity.setPreferredSize(new Dimension(100,35));
        txtMaturity.addKeyListener(this);

        lblMaturity.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblMaturity.setPreferredSize(new Dimension(130,25));
        maturityPanel.add(lblMaturity,FlowLayout.LEFT);
        maturityPanel.add(txtMaturity);
        cagrPanel.add(maturityPanel);

        // 4️⃣ Start Date
        cagrPanel.add(createStartDatePanel("Start Date:"));

        // 5️⃣ End Date
        cagrPanel.add(createEndDatePanel("End Date:"));

        // 6️⃣ Action Buttons
        cagrPanel.add(createButtonPanel());

        return cagrPanel;
    }

    private JPanel createCenteredLabelPanel(String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        panel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(label);
        return panel;
    }

    JComboBox<String> yearCombo = new JComboBox<>();
    JComboBox<String> monthCombo = new JComboBox<>();
    JComboBox<String> dayCombo = new JComboBox<>();
    private JPanel createStartDatePanel(String labelText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setPreferredSize(new Dimension(100,35));

        yearCombo.addItem("YYYY");
        monthCombo.addItem("MM");
        dayCombo.addItem("DD");
        for (int i=2000;i<=2030;i++) yearCombo.addItem(String.valueOf(i));
        for (int i=1;i<=12;i++) monthCombo.addItem(String.valueOf(i));
        for (int i=1;i<=31;i++) dayCombo.addItem(String.valueOf(i));

        panel.add(label,FlowLayout.LEFT);
        JPanel combo=new JPanel(new FlowLayout());
        combo.setBackground(new Color(245,245,255));

        combo.add(yearCombo);
        combo.add(monthCombo);
        combo.add(dayCombo);
        panel.add(combo);
        return panel;
    }
    JComboBox<String> yearCombo1 = new JComboBox<>();
    JComboBox<String> monthCombo1 = new JComboBox<>();
    JComboBox<String> dayCombo1 = new JComboBox<>();
    private JPanel createEndDatePanel(String labelText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setPreferredSize(new Dimension(100,35));

        yearCombo1.addItem("YYYY");
        monthCombo1.addItem("MM");
        dayCombo1.addItem("DD");

        for (int i=2025;i<=2060;i++) yearCombo1.addItem(String.valueOf(i));
        for (int i=1;i<=12;i++) monthCombo1.addItem(String.valueOf(i));
        for (int i=1;i<=31;i++) dayCombo1.addItem(String.valueOf(i));

        JPanel combo=new JPanel(new FlowLayout());
        combo.setBackground(new Color(245,245,255));

        combo.add(yearCombo1);
        combo.add(monthCombo1);
        combo.add(dayCombo1);
        panel.add(combo);
        panel.add(label,FlowLayout.LEFT);

        return panel;
    }

    JButton calculateBtn,cancelBtn,resetBtn;
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);

        calculateBtn = new JButton("Calculate");
        cancelBtn = new JButton("Cancel");
        resetBtn=new JButton("Reset");

        calculateBtn.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        calculateBtn.setForeground(Color.WHITE);
        calculateBtn.addActionListener(this);
        cancelBtn.setBackground(new Color(220, 20, 60)); // Crimson
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.addActionListener(this);
        resetBtn.setBackground(new Color(185, 182, 183)); // Grey
        resetBtn.setForeground(Color.WHITE);
        resetBtn.addActionListener(this);


        calculateBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        resetBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        panel.add(calculateBtn);
        panel.add(cancelBtn);
        panel.add(resetBtn);

        return panel;
    }

    public boolean isNullTextField(){
        return ((txtPrincipal.getText().isEmpty()) || txtMaturity.getText().isEmpty());
    }

    LocalDate finalStartDate,finalEndDate,currentDate;
    String startDate,endDate;
    public void dateDifference() {
        Object y1 = yearCombo1.getSelectedItem();
        Object m1 = monthCombo1.getSelectedItem();
        Object d1 = dayCombo1.getSelectedItem();

        Object y2 = yearCombo.getSelectedItem();
        Object m2 = monthCombo.getSelectedItem();
        Object d2 = dayCombo.getSelectedItem();

        if ((y1 != null && y1 != "YYYY") && (m1 != null && m1!= "MM") && (d1 != null && d1 !="DD") && (y2 != null && y2 != "YYYY") && (m2 != null && m2!="MM") && (d2 != null && d2!="DD")) {
            startDate = y2 + "-" + m2 + "-" + d2;
            endDate = y1 + "-" + m1 + "-" + d1;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
            finalStartDate = LocalDate.parse(startDate, formatter);
            finalEndDate = LocalDate.parse(endDate, formatter);
            currentDate = LocalDate.now();
            // Proceed with logic
        }  else {
            startDate="Wrong";
            endDate="Wrong";
        }
    }

    long monthsBetween;
    private boolean isMature(){
        dateDifference();
        if(finalEndDate.getYear() < currentDate.getYear()){
            return true;
        }
        if(finalEndDate.getYear() == currentDate.getYear() && finalEndDate.getMonthValue() < currentDate.getMonthValue()){
            return true;
        }
        else {
            monthsBetween = ChronoUnit.MONTHS.between(finalStartDate, finalEndDate);
            return false;
        }
    }

    // For testing
    public static void main(String[] args) {
        JFrame frame = new JFrame("SIP Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        CAGR ob1=new CAGR();
        frame.setContentPane(ob1.cagrCalculatorPanel());
        frame.setVisible(true);
    }

    public double CAGR(double amt,double finalAmt,long time){
        long year=time/12; long month=time-year*12;
        double powerPart= (double) 1 /(year+ (double) month /12);
        double expPart=Math.pow((finalAmt/amt),powerPart);
        String value=String.format("%.2f", (expPart-1)*100);
        double cagr=(expPart-1)*(double)100;
        return (double) Math.round(cagr * 100) /100;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==calculateBtn){
            dateDifference();
            if(isNullTextField()){
                JOptionPane.showMessageDialog(null,"Fill up all the details");
                return;
            }
            if(Double.parseDouble(txtPrincipal.getText())<1000){
                JOptionPane.showMessageDialog(null,"Principal must be at least Rs 1000");
                return;
            }

            if(startDate.equalsIgnoreCase("wrong") || endDate.equalsIgnoreCase("wrong")){
                JOptionPane.showMessageDialog(null,"Date is Invalid");
                return;
            }
            if(isMature()){
                JOptionPane.showMessageDialog(null,"Your Investment have been Matured.");
                return;
            } else{
                double amt=Double.parseDouble(txtPrincipal.getText());
                double finalAmt=Double.parseDouble(txtMaturity.getText());
                double cagr=CAGR(amt,finalAmt,monthsBetween);
                JOptionPane.showMessageDialog(null,"Compounded Annual Growth Rate(CAGR) is: "+cagr+"%");

            }
        }
        if(e.getSource()==cancelBtn){
            resetBtn.doClick();
            CalculatorPage.cardLayout.show(CalculatorPage.cardPanel,"calculatorPage");

        }
        if(e.getSource()==resetBtn){
            txtPrincipal.setText("");
            txtMaturity.setText("");
            yearCombo.setSelectedIndex(0);
            monthCombo.setSelectedIndex(0);
            dayCombo.setSelectedIndex(0);

            yearCombo1.setSelectedIndex(0);
            monthCombo1.setSelectedIndex(0);
            dayCombo1.setSelectedIndex(0);

        }
    }

    int j=8,i=8;
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource()==txtPrincipal){
            CalculatorPage.amountLogic(txtPrincipal,e);
        }
        if(e.getSource()==txtMaturity){
            CalculatorPage.amountLogic(txtMaturity,e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}

