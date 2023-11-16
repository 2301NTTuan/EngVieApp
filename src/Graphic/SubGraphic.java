package Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Dictionary.DictionaryManagement;


public class SubGraphic implements ActionListener {
    JDialog subWindow = new JDialog();
    JButton acceptBtn = new JButton();
    JButton cancelBtn = new JButton("Cancel");
    JLabel functionLabel = new JLabel();
    JTextField enterWordBox = new JTextField();
    JLabel definitionLabel = new JLabel();
    JTextArea definitionWordBox = new JTextArea();
    JTextField notificationLabel = new JTextField();
    private String noti = "";
    public String getNoti() {
        return noti;
    }

    public void setNoti(String noti) {
        this.noti = noti;
    }


    public void setLocation(Frame windowApp) {
        Point mainFrameLocation = windowApp.getLocation();
        Dimension mainFrameSize = windowApp.getSize();
        int subWindowX = mainFrameLocation.x + mainFrameSize.width - subWindow.getSize().width - 10;
        int subWindowY = mainFrameLocation.y + 200;
        subWindow.setLocation(subWindowX, subWindowY);
    }
    public SubGraphic(String option, Frame windowApp) {
        Timer timer = new Timer(100, e -> setLocation(windowApp));
        timer.start();
        acceptBtn.setBounds(45, 400, 100, 40);
        acceptBtn.setBorderPainted(true);
        acceptBtn.addActionListener(this);

        cancelBtn.setBounds(225,400,100,40);
        cancelBtn.setBorderPainted(true);
        cancelBtn.addActionListener(this);

        functionLabel.setFont(new Font("Times New Roman", Font.ITALIC,20));
        functionLabel.setBounds(15,10,150,40);

        enterWordBox.setBounds(15, 42, 340,40);
        enterWordBox.setFont(new Font("Times New Roman", Font.ITALIC,20));

        definitionLabel.setFont(new Font("Times New Roman", Font.ITALIC,20));
        definitionLabel.setBounds(15,85,300,40);

        definitionWordBox.setBounds(15, 130, 340,265);
        definitionWordBox.setFont(new Font("Times New Roman", Font.ITALIC,20));

        notificationLabel.setBounds(50, 250, 265, 40);
        notificationLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        notificationLabel.setBackground(Color.cyan);
        notificationLabel.setVisible(false);

        subWindow.add(acceptBtn);
        subWindow.add(cancelBtn);
        subWindow.add(functionLabel);
        subWindow.add(definitionLabel);
        subWindow.add(enterWordBox);
        subWindow.add(definitionWordBox);
        subWindow.add(notificationLabel);
        subWindow.setLayout(null);
        subWindow.setSize(390,500);
        subWindow.setVisible(false);

        if (option.equals("addWord")) {
            subWindow.setVisible(true);
            setAddFunction();
        }
        else if (option.equals("modifyWord")){
            subWindow.setVisible(true);
            setModifyFunction();
        } else {
            noti = "Error";
        }
    }

    public void setModifyFunction() {
        subWindow.setTitle("Modify Function");
        acceptBtn.setText("OK");
        acceptBtn.addActionListener(this.modifyWordButton);
        functionLabel.setText("Enter Word");
        definitionLabel.setText("Definition");
    }

    public void setAddFunction() {
        subWindow.setTitle("Addition Function");
        acceptBtn.setText("OK");
        acceptBtn.addActionListener(this.addWordButton);
        functionLabel.setText("Enter Word");
        definitionLabel.setText("Definition");
    }

    ActionListener addWordButton = eventAdd -> {
        setNoti("");
        if (enterWordBox.getText().isEmpty() || definitionWordBox.getText().isEmpty()) {
            setNoti("Enter completely infor, pls!");
        } else {
            DictionaryManagement.addWord(enterWordBox.getText(), definitionWordBox.getText());
            setNoti(DictionaryManagement.getNoti());
        }
    };

    ActionListener modifyWordButton = eventModify -> {
        setNoti("");
        if (enterWordBox.getText().isEmpty() || definitionWordBox.getText().isEmpty()) {
            setNoti("Enter completely infor, pls!");
        } else {
            DictionaryManagement.modifyWord(enterWordBox.getText(), definitionWordBox.getText());
            setNoti(DictionaryManagement.getNoti());
        }
    };

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == acceptBtn) {
            if (getNoti().startsWith("N") || getNoti().startsWith("E")) {
                notificationLabel.setVisible(true);
                showNotification(getNoti());
            } else {
                subWindow.dispose();
            }
        } else {
            subWindow.dispose();
        }
    }

    private void showNotification(String message) {
        notificationLabel.setText(message);
        Timer timer = new Timer(2000, event -> notificationLabel.setVisible(false));
        timer.setRepeats(false);
        timer.start();
    }
}
