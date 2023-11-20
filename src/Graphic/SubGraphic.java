package Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Dictionary.Dictionary;
import Dictionary.DictionaryManagement;
import Dictionary.Word;

public class SubGraphic implements ActionListener {
    JDialog subWindow = new JDialog();
    JButton acceptBtn = new JButton();
    JButton cancelBtn = new JButton("Cancel");
    JButton searchBtn = new JButton("Search");
    JLabel functionLabel = new JLabel();
    JTextField enterWordBox = new JTextField();
    JLabel definitionLabel = new JLabel();
    JTextArea definitionWordBox = new JTextArea();
    JTextField notificationLabel = new JTextField();
    private String noti = "";
    public String wordTarget;

    public String getNoti() {
        return noti;
    }

    public void setNoti(String noti) {
        this.noti = noti;
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
        acceptBtn.setBounds(35, 260, 80, 40);
        acceptBtn.setBorderPainted(true);
        acceptBtn.addActionListener(this);

        cancelBtn.setBounds(215,260,80,40);
        cancelBtn.setBorderPainted(true);
        cancelBtn.addActionListener(this);

        searchBtn.setBounds(215, 85, 80, 40);
        searchBtn.setBorderPainted(true);
        searchBtn.addActionListener(searching);
        searchBtn.setVisible(false);

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

        notificationLabel.setBounds(0, 165, 360, 40);
        notificationLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        notificationLabel.setBackground(Color.cyan);
        notificationLabel.setVisible(false);

        subWindow.add(acceptBtn);
        subWindow.add(cancelBtn);
        subWindow.add(searchBtn);
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
        else if (option.equals("modifyWord")) {
            subWindow.setVisible(true);
            setModifyFunction();
        } else if (option.equals("removeWord")) {
            subWindow.setVisible(true);
            searchBtn.setVisible(true);
            definitionWordBox.setEditable(false);
            acceptBtn.setVisible(false);
            setRemoveFuction();
        } else {
            setNoti("Error");
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

    public void setRemoveFuction() {
        subWindow.setTitle("Remove Function");
        acceptBtn.setText("Remove");
        acceptBtn.addActionListener(removeWordButton);
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

    ActionListener removeWordButton = eventRemove -> {
      setNoti("");
      String enterWordBoxString = enterWordBox.getText().replaceAll("\\s+", " ").trim();
      DictionaryManagement.deleteWord(enterWordBoxString);
      setNoti(DictionaryManagement.getNoti());
      acceptBtn.setVisible(false);
    };

    ActionListener searching = search -> {
        setNoti("");
        String text = enterWordBox.getText().replaceAll("\\s+", " ").trim();
        Word word;
        if (!text.isEmpty()) {
            word = DictionaryManagement.dictionaryLookup(text);
            setNoti(DictionaryManagement.getNoti());
            if (word != null) {
                definitionWordBox.setText(word.getWord_explain());
                acceptBtn.setVisible(true);
            } else {
                notificationLabel.setVisible(true);
                showNotification(DictionaryManagement.getNoti());
            }
        } else {
            notificationLabel.setVisible(true);
            showNotification("Enter word, please!");
        }
    };

    public void actionPerformed(ActionEvent event) {
         if (event.getSource() == acceptBtn) {
            if (getNoti() != null) {
                if (getNoti().startsWith("A") || getNoti().startsWith("T")
                        || getNoti().startsWith("M") || getNoti().startsWith("D")) {
                    if (acceptBtn.getText().equals("Add")) {
                        MainGraphic.showAddAndModify(Dictionary.addedArray);
                    } else if (acceptBtn.getText().equals("Modify")){
                        MainGraphic.showAddAndModify(Dictionary.modifiedArray);
                    } else if (acceptBtn.getText().equals("Remove")) {
                        MainGraphic.showAddAndModify(Dictionary.deletedArray);
                    }
                    enterWordBox.setText("");
                    definitionWordBox.setText("");
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
        Timer timer = new Timer(3500, event -> notificationLabel.setVisible(false));
        timer.setRepeats(false);
        timer.start();
    }
}
