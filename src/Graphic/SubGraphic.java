package Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Dictionary.Dictionary;
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
        noti = noti;
    }


    public void setLocation(Frame windowApp) {
        Point mainFrameLocation = windowApp.getLocation();
        Dimension mainFrameSize = windowApp.getSize();
        int subWindowX = mainFrameLocation.x + mainFrameSize.width - subWindow.getSize().width - 20;
        int subWindowY = mainFrameLocation.y + 200;
        subWindow.setLocation(subWindowX, subWindowY);
    }
    public SubGraphic(String option, Frame windowApp) {
        Timer timer = new Timer(100, e -> setLocation(windowApp));
        timer.start();
        acceptBtn.setBounds(35, 260, 100, 40);
        acceptBtn.setBorderPainted(true);
        acceptBtn.addActionListener(this);

        cancelBtn.setBounds(215,260,100,40);
        cancelBtn.setBorderPainted(true);
        cancelBtn.addActionListener(this);

        functionLabel.setFont(new Font("Times New Roman", Font.ITALIC,20));
        functionLabel.setBounds(5,10,150,40);

        enterWordBox.setBounds(5, 42, 340,40);
        enterWordBox.setFont(new Font("Times New Roman", Font.ITALIC,20));

        definitionLabel.setFont(new Font("Times New Roman", Font.ITALIC,20));
        definitionLabel.setBounds(5,85,300,40);

        definitionWordBox.setBounds(5, 130, 340,120);
        definitionWordBox.setFont(new Font("Times New Roman", Font.ITALIC,20));
        definitionWordBox.setWrapStyleWord(true);
        definitionWordBox.setLineWrap(true);

        notificationLabel.setBounds(0, 125, 360, 40);
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
        subWindow.setSize(370,355);
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
        acceptBtn.setText("Modify");
        acceptBtn.addActionListener(modifyWordButton);
        functionLabel.setText("Enter Word");
        definitionLabel.setText("Definition");
    }

    public void setAddFunction() {
        subWindow.setTitle("Addition Function");
        acceptBtn.setText("Add");
        acceptBtn.addActionListener(addWordButton);
        functionLabel.setText("Enter Word");
        definitionLabel.setText("Definition");
    }

    ActionListener addWordButton = eventAdd -> {
        setNoti("");
        if (enterWordBox.getText().isEmpty() || definitionWordBox.getText().isEmpty()) {
            setNoti("Enter completely infor, pls!");
        } else {
            DictionaryManagement.addWord(enterWordBox.getText().replaceAll("\\s+", " "), definitionWordBox.getText().replaceAll("\\s+", " "));
            setNoti(DictionaryManagement.getNoti());
        }
    };

    ActionListener modifyWordButton = eventModify -> {
        setNoti("");
        if (enterWordBox.getText().isEmpty() || definitionWordBox.getText().isEmpty()) {
            setNoti("Enter completely infor, pls!");
        } else {
            DictionaryManagement.modifyWord(enterWordBox.getText().replaceAll("\\s+", " "), definitionWordBox.getText().replaceAll("\\s+", " "));
            setNoti(DictionaryManagement.getNoti());
        }
    };

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == acceptBtn) {
            if (getNoti() != null) {
                if (getNoti().startsWith("A") || getNoti().startsWith("T") ) {
                    if (acceptBtn.getText().equals("Add")) {
                        MainGraphic.generalBtnSetUp(Dictionary.addedArray);
                    } else {
                        MainGraphic.generalBtnSetUp(Dictionary.modifiedArray);
                    }
                }
                notificationLabel.setVisible(true);
                showNotification(getNoti());
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
