import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public JTextField textField1;
    public JButton getResultButton;
    private JPanel panel;
    private JLabel label1;

    public void getGetResultButton() {
        Font font = new Font("Courier", Font.ITALIC, 14);
        getResultButton.setFont(font);
    }

    public void getLabel1() {
        Font font = new Font("Courier", Font.ITALIC, 14);
        label1.setFont(font);
    }

    public void getPanel() {
        panel.setBackground(Color.PINK);
    }

    public void getTextField1() {
        Font font = new Font("Courier", Font.ITALIC, 14);
        textField1.setFont(font);
        textField1.setHorizontalAlignment(JTextField.CENTER);
    }

    public MainWindow() {
        getPanel();
        getGetResultButton();
        getLabel1();
        getTextField1();
        this.getContentPane().add(panel);
    }

}