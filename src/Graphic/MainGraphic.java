package Graphic;

import Dictionary.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;

public class MainGraphic {
    JFrame windowApp = new JFrame();
    JPanel panel = new JPanel();
    JPanel headerPanel = new JPanel();
    Rectangle recPanel = new Rectangle();
    JPanel borderSeachBox = new JPanel();


    JButton addBtn = new JButton("Added Words");
    JButton removeBtn = new JButton("Removed Words");
    JButton modifyBtn = new JButton("Modified Words");
    JButton speakerBtn = new JButton("Speak");
    JButton recentBtn = new JButton("Recent Words");
    JButton userBtn = new JButton("User Words");

    private JButton selectedButton = null;

    static JList<Object> suggestionsList = new JList<>();
    static JScrollPane listSuggestionContainer = new JScrollPane(suggestionsList);
    static JTextField searchWordBox = new JTextField();
    static JTextArea generalTextPanel = new JTextArea();
    JScrollPane textScrollPane = new JScrollPane(generalTextPanel);
    JLabel dictionaryLabel = new JLabel();
    static ArrayList<String> foundSuggestionList = new ArrayList<>();

    private void setupPanel() {
        this.panel.setBounds(recPanel);
        this.panel.setLayout(null);
        this.panel.setBackground(Color.white);
        this.panel.add(listSuggestionContainer);
        this.panel.add(borderSeachBox);
        this.panel.add(textScrollPane);
        this.panel.add(headerPanel);
        this.panel.add(dictionaryLabel);

        this.dictionaryLabel.setForeground(Color.white);
        this.dictionaryLabel.setText("Dictionary");
        this.dictionaryLabel.setFont(new Font("Times New Roman",Font.ITALIC,23));
        this.dictionaryLabel.setBounds(380,10,200,35);

        Color bkgColor = new Color(41, 162, 162);
        this.headerPanel.setBounds(0,0, 910, 140);
        this.headerPanel.setBackground(bkgColor);
        this.headerPanel.add(addBtn);
        this.headerPanel.add(removeBtn);
        this.headerPanel.add(modifyBtn);
        this.headerPanel.add(recentBtn);
        this.headerPanel.add(dictionaryLabel);
        this.headerPanel.add(userBtn);
        this.headerPanel.add(speakerBtn);
        this.headerPanel.setLayout(null);


        this.windowApp.add(panel, BorderLayout.CENTER);
        this.windowApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.windowApp.setTitle("ENG-VIE Dictionary");
        this.windowApp.pack();
        this.windowApp.setVisible(true);
        this.windowApp.setSize(910,960);
    }

    private void setupGeneralButton(JButton button, ActionListener actionListener) {
        button.setForeground(Color.darkGray);
        button.setBackground(Color.cyan);
        button.addActionListener(actionListener);
        button.setFocusPainted(false);
    }




    private void setupButton() {

        this.removeBtn.setBounds(5, 50, 165, 35);
        this.removeBtn.setBorderPainted(false);
        this.removeBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(removeBtn, removeBtnListener);

        this.modifyBtn.setBounds(185, 50, 165, 35);
        this.modifyBtn.setBorderPainted(false);
        this.modifyBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(modifyBtn, modifyBtnListener);


        this.recentBtn.setBounds(365, 50, 165, 35);
        this.recentBtn.setBorderPainted(false);
        this.recentBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(recentBtn, recentBtnListener);



        this.addBtn.setBounds(545, 50, 165, 35);
        this.addBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        this.addBtn.setBorderPainted(false);
        setupGeneralButton(addBtn, addBtnListener);

        this.userBtn.setBounds(725, 50, 165, 35);
        this.userBtn.setBorderPainted(false);
        this.userBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(userBtn, usesrBtnListener);

        this.speakerBtn.setBounds(160, 95, 100, 35);
        this.speakerBtn.setBorderPainted(false);
        this.speakerBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(speakerBtn, speakBtnListener);
        speakerBtn.setVisible(false);
    }

    private void setupOther() {

        Color bkgColor = new Color(190, 231, 231);
        searchWordBox.setBounds(2,2,335,46);
        searchWordBox.setFont(new Font("Times New Roman", Font.PLAIN,22));
        searchWordBox.setBackground(bkgColor);
        borderSeachBox.setBackground(Color.cyan);
        borderSeachBox.setLayout(null);
        borderSeachBox.setBounds(280,90,340,50);
        borderSeachBox.add(searchWordBox);

        listSuggestionContainer.setBounds(280, 140, 340, 400);
        listSuggestionContainer.setOpaque(false);
        listSuggestionContainer.setBorder(BorderFactory.createEmptyBorder());
        listSuggestionContainer.setVisible(false);

        Color bkgColor1 = new Color(138, 231, 231);
        suggestionsList.setBackground(bkgColor1);
        suggestionsList.setBounds(280,144,340,400);
        suggestionsList.setFixedCellWidth(250);
        suggestionsList.setFixedCellHeight(40);
        suggestionsList.setFont(new Font("Times New Roman", Font.BOLD,22));
        suggestionsList.addListSelectionListener(this.selectedWordListener);



        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textScrollPane.setBounds(0, 145, 893, 780);

        Color bkgColor2 = new Color(41, 162, 162);
        //generalTextPanel.setLayout(null);
        generalTextPanel.setEditable(false);
        generalTextPanel.setBackground(bkgColor2);
        generalTextPanel.setBounds(0,145,910,780);
        generalTextPanel.setFont(new Font("Times New Roman",Font.ITALIC,23));
        generalTextPanel.setWrapStyleWord(true);
        generalTextPanel.setLineWrap(true);
    }

    public MainGraphic() {
        setupOther();
        setupButton();
        setupPanel();
        setupSearchWordBox();
        colorAllChangeButton();
    }

    private void colorAllChangeButton() {
        createColorChangeButton(removeBtn);
        createColorChangeButton(recentBtn);
        createColorChangeButton(addBtn);
        createColorChangeButton(modifyBtn);
        createColorChangeButton(userBtn);
    }

    private void createColorChangeButton(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedButton != null) {
                    if (!button.equals(selectedButton)) {
                        button.setBackground(new Color(19, 58, 58));
                        selectedButton.setBackground(Color.cyan);
                        selectedButton = button;
                    }
                } else {
                    button.setBackground(new Color(19, 58, 58));
                    selectedButton = button;
                }

            }
        });
    }

    private void setupSearchWordBox() {
        searchWordBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                listSuggestionContainer.setVisible(false);
                String prefixWord = searchWordBox.getText();
                if (prefixWord.isEmpty() || prefixWord == null) {
                    listSuggestionContainer.setVisible(false);
                    return;
                }

                if (!prefixWord.isEmpty() || prefixWord != null) {
                    foundSuggestionList = DictionaryManagement.dictionarySearch(prefixWord);
                }

                if (foundSuggestionList.size() > 0) {
                    listSuggestionContainer.setVisible(true);
                    suggestionsList.setListData(foundSuggestionList.toArray());
                } else {
                    listSuggestionContainer.setVisible(false);
                    suggestionsList.clearSelection();
                }
            }
        });
    }

    public static String wordString(ArrayList<Word> wordArrayList) {
        String string = "";
        for (Word w : wordArrayList) {

            if (w.getWord_explain().isEmpty()) {
                string += " - " + w.getWord_target() + "\n";
            } else {
                string += "  - " + w.getWord_target() + " : " + w.getWord_explain() + "\n";
            }
        }
        return string;
    }

    ActionListener recentBtnListener = showRecentWord -> {
        listSuggestionContainer.setVisible(false);
        suggestionsList.clearSelection();
        String recentWordString = "recent Word: \n" + wordString(Dictionary.recentWordArray);
        generalTextPanel.setText(recentWordString);
    };

    public static void generalBtnSetUp(ArrayList<Word> arrayList) {
        suggestionsList.clearSelection();
        listSuggestionContainer.setVisible(false);
        searchWordBox.setText("");
        String List;
        if (arrayList == Dictionary.addedArray) {
            List = "Added Word List: \n" + wordString(arrayList);
        } else {
            List = "Modified Word List: \n" + wordString(arrayList);
        }
        generalTextPanel.setText(List);
    }

    ActionListener addBtnListener = add -> {
        speakerBtn.setVisible(false);
        generalBtnSetUp(Dictionary.addedArray);
        new SubGraphic("addWord", windowApp);
    };


    ActionListener modifyBtnListener = modify -> {
        speakerBtn.setVisible(false);
        generalBtnSetUp(Dictionary.modifiedArray);
        new SubGraphic("modifyWord", windowApp);
    };

    ActionListener removeBtnListener = remove -> {
        speakerBtn.setVisible(false);
        int selectedWord = suggestionsList.getSelectedIndex();
        if (selectedWord != -1 && !foundSuggestionList.isEmpty()) {
            Object[] options = {"Yes", "No"};
            String wordToDelete = foundSuggestionList.get(selectedWord);

            String message = String.format("Are you sure you want to delete \"%s\"?", wordToDelete);
            int n = JOptionPane.showOptionDialog(windowApp,
                    message,
                    "Yes No Question!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]);

            if (n == JOptionPane.OK_OPTION) {
                System.out.println("OKE");
                DictionaryManagement.deleteWord(wordToDelete);
                foundSuggestionList.remove(selectedWord);
                suggestionsList.setListData(foundSuggestionList.toArray());
            }
        } else {
            listSuggestionContainer.setVisible(false);
            searchWordBox.setText("");
            suggestionsList.clearSelection();
            String removedList = "Removed Word list: \n" + wordString(Dictionary.deletedArray);
            generalTextPanel.setText(removedList);
        }
    };


    ActionListener speakBtnListener = speak -> {
        int selectedWord = suggestionsList.getSelectedIndex();
        if (selectedWord != -1) {
            String word = (foundSuggestionList.get(selectedWord));
            if (!word.isEmpty()) {
                DictionaryManagement.textToSpeech(word);
            }
        } else if (!searchWordBox.getText().isEmpty()) {
            DictionaryManagement.textToSpeech(searchWordBox.getText());
        }
    };

    ListSelectionListener selectedWordListener = selectWord -> {
        speakerBtn.setVisible(true);
        listSuggestionContainer.setVisible(false);
        int selectedWord = suggestionsList.getSelectedIndex();
        if (selectedWord > -1) {
            Word word = DictionaryManagement.dictionaryLookup(foundSuggestionList.get(selectedWord));
            if (word != null) {
                searchWordBox.setText(word.getWord_target());
                String stringWord = "  - " + word.getWord_target() + " : " + word.getWord_explain();
                generalTextPanel.setText(stringWord);
            } else {
                generalTextPanel.setText(DictionaryManagement.getNoti());
            }
        }
    };

    ActionListener usesrBtnListener = showUserWord -> {
        speakerBtn.setVisible(false);
        suggestionsList.clearSelection();
        listSuggestionContainer.setVisible(false);
        searchWordBox.setText("");
        String userList = "User Words list: \n" + wordString(Dictionary.userArray);
        generalTextPanel.setText(userList);
    };

}