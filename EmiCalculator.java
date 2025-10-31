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

public class EmiCalculator implements KeyListener, ActionListener {
    JTextField txtPrincipal,txtRoi;
    public JPanel emiCalculatorPanel() {
        JPanel emiPanel=new JPanel();
        emiPanel.setLayout(new BoxLayout(emiPanel, BoxLayout.Y_AXIS));
        emiPanel.setBackground(new Color(245, 245, 255)); // Soft background

        // 1️⃣ Title Panel
        emiPanel.add(createCenteredLabelPanel("EMI Calculator"));

        // 2️⃣ Principal Input
        JPanel principalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        principalPanel.setOpaque(false);

        JLabel lblPrincipal = new JLabel("Borrowed Loan: ");
        txtPrincipal = new JTextField(15);
        txtPrincipal.setPreferredSize(new Dimension(200,35));
        txtPrincipal.addKeyListener(this);

        lblPrincipal.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblPrincipal.setPreferredSize(new Dimension(130,25));
        principalPanel.add(lblPrincipal,FlowLayout.LEFT);
        principalPanel.add(txtPrincipal);
        emiPanel.add(principalPanel);


        // 3️⃣ ROI Input
        JPanel roiPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        roiPanel.setOpaque(false);

        JLabel lblRoi = new JLabel("ROI: ");
        txtRoi = new JTextField(15);
        txtRoi.setPreferredSize(new Dimension(100,35));
        txtRoi.addKeyListener(this);

        lblRoi.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblRoi.setPreferredSize(new Dimension(130,25));
        roiPanel.add(lblRoi,FlowLayout.LEFT);
        roiPanel.add(txtRoi);
        emiPanel.add(roiPanel);

        // 4️⃣ Start Date
        emiPanel.add(createStartDatePanel("Start Date:"));

        // 5️⃣ End Date
        emiPanel.add(createEndDatePanel("End Date:"));

        // 6️⃣ Action Buttons
        emiPanel.add(createButtonPanel());

        return emiPanel;
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

    JButton cancelBtn,calculateBtn,resetBtn;
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("EMI Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        EmiCalculator ob1=new EmiCalculator();
        frame.setContentPane(ob1.emiCalculatorPanel());
        frame.setVisible(true);
    }
    LocalDate finalStartDate,finalEndDate,currentDate;
    String startDate,endDate;
    private void dateDifference() {
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

    private boolean isNullTextField(){
        return txtPrincipal.getText().isEmpty() || txtRoi.getText().isEmpty();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dateDifference();
        if(e.getSource()==calculateBtn){
            if(isNullTextField()){
                JOptionPane.showMessageDialog(null,"Fill up all the details");
                return;
            }
            if(Double.parseDouble(txtPrincipal.getText())<10000){
                JOptionPane.showMessageDialog(null,"Borrowed Load must be at least Rs 10000");
                return;
            }
            if(Double.parseDouble(txtRoi.getText())>=50){
                JOptionPane.showMessageDialog(null,"CAGR cannot be greater than 50");
                return;
            }
            if(startDate.equalsIgnoreCase("wrong") || endDate.equalsIgnoreCase("wrong")){
                JOptionPane.showMessageDialog(null,"Date is Invalid");
                return;
            }
            if(isMature()){
                JOptionPane.showMessageDialog(null,"Your borrowed loan have been Matured.");
                return;
            } else {
                double amt=Double.parseDouble(txtPrincipal.getText());
                double roi=Double.parseDouble(txtRoi.getText());
                double emi=emiCalculation(amt,roi,monthsBetween);
                JOptionPane.showMessageDialog(null,"Monthly EMI Payment is: ₹"+emi);
            }
        }
        if(e.getSource()==cancelBtn){
            resetBtn.doClick();
            CalculatorPage.cardLayout.show(CalculatorPage.cardPanel,"calculatorPage");
        }
        if(e.getSource()==resetBtn){
            txtPrincipal.setText("");
            txtRoi.setText("");
            yearCombo.setSelectedIndex(0);
            monthCombo.setSelectedIndex(0);
            dayCombo.setSelectedIndex(0);

            yearCombo1.setSelectedIndex(0);
            monthCombo1.setSelectedIndex(0);
            dayCombo1.setSelectedIndex(0);

        }
    }

    private double emiCalculation(double amount,double rate,long timeInMonths){
        double r=rate/1200;
        double part1=amount*r*(Math.pow((1+r),timeInMonths));
        double part2=Math.pow((1+r),timeInMonths)-1;
        double emi=part1/part2;
        return Math.round(emi * 100.0) / 100.0;
    }

    static int decimalCount=0;
    static int i=2,j=8;
    static boolean hasDecimal=false;
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource()==txtPrincipal){
            CalculatorPage.amountLogic(txtPrincipal,e);
        }
        if(e.getSource()==txtRoi){
            CalculatorPage.rateLogic(txtRoi,e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
