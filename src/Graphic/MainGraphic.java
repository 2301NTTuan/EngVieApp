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


    JButton addBtn = new JButton("Add");
    JButton removeBtn = new JButton("Remove");
    JButton modifyBtn = new JButton("Modify");
    JButton speakerBtn = new JButton("Speak");
    JButton recentBtn = new JButton("Recent");
    static JList<Object> suggestionsList = new JList<>();
    JScrollPane listSuggestionContainer = new JScrollPane(suggestionsList);
    JTextField searchWordBox = new JTextField();
    JTextArea generalTextPanel = new JTextArea();
    JLabel dictionaryLabel = new JLabel();
    static ArrayList<String> foundSuggestionList = new ArrayList<>();

    private void setupPanel() {
        this.panel.setBounds(recPanel);
        this.panel.setLayout(null);
        this.panel.setBackground(Color.white);

        this.panel.add(listSuggestionContainer);
        this.panel.add(borderSeachBox);
        this.panel.add(generalTextPanel);
        this.panel.add(headerPanel);
        this.panel.add(dictionaryLabel);

        this.dictionaryLabel.setForeground(Color.white);
        this.dictionaryLabel.setText("Dictionary");
        this.dictionaryLabel.setFont(new Font("Times New Roman",Font.ITALIC,23));
        this.dictionaryLabel.setBounds(380,10,200,35);

        Color bkgColor = new Color(41, 162, 162);
        this.headerPanel.setBounds(0,0, 900, 140);
        this.headerPanel.setBackground(bkgColor);
        this.headerPanel.add(addBtn);
        this.headerPanel.add(removeBtn);
        this.headerPanel.add(modifyBtn);
        this.headerPanel.add(recentBtn);
        this.headerPanel.add(dictionaryLabel);
        this.headerPanel.setLayout(null);


        this.windowApp.add(panel, BorderLayout.CENTER);
        this.windowApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.windowApp.setTitle("ENG-VIE Dictionary");
        this.windowApp.pack();
        this.windowApp.setVisible(true);
        this.windowApp.setSize(900,960);
    }

    private void setupGeneralButton(JButton button, ActionListener actionListener) {
        button.setForeground(Color.darkGray);
        button.setBackground(Color.cyan);
        button.addActionListener(actionListener);
    }

    private void setupButton() {

        this.removeBtn.setBounds(190, 50, 100, 35);
        this.removeBtn.setBorderPainted(false);
        this.removeBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(removeBtn, removeBtnListener);

        this.modifyBtn.setBounds(330, 50, 100, 35);
        this.modifyBtn.setBorderPainted(false);
        this.modifyBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(modifyBtn, modifyBtnListener);


        this.recentBtn.setBounds(470, 50, 100, 35);
        this.recentBtn.setBorderPainted(false);
        this.recentBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(recentBtn, recentBtnListener);



        this.addBtn.setBounds(610, 50, 100, 35);
        this.addBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        this.addBtn.setBorderPainted(false);
        setupGeneralButton(addBtn, addBtnListener);


        Icon speakIcon = new ImageIcon("Resources/Icon/speaker.png");
        this.speakerBtn.setIcon(speakIcon);
        this.speakerBtn.setBounds(710, 20, 50, 50);
        this.speakerBtn.setBorderPainted(false);
        this.speakerBtn.addActionListener(this.speakBtnListener);
        this.speakerBtn.setVisible(false);
    }

    private void setupOther() {

        Color bkgColor = new Color(190, 231, 231);
        this.searchWordBox.setBounds(2,2,335,46);
        this.searchWordBox.setFont(new Font("Times New Roman", Font.PLAIN,22));
        this.searchWordBox.setBackground(bkgColor);
        this.borderSeachBox.setBackground(Color.cyan);
        this.borderSeachBox.setLayout(null);
        this.borderSeachBox.setBounds(280,90,340,50);
        this.borderSeachBox.add(searchWordBox);

        this.listSuggestionContainer.setBounds(280, 140, 340, 400);
        this.listSuggestionContainer.setOpaque(false);
        this.listSuggestionContainer.setBorder(BorderFactory.createEmptyBorder());
        this.listSuggestionContainer.setVisible(false);

        Color bkgColor1 = new Color(138, 231, 231);
        suggestionsList.setBackground(bkgColor1);
        suggestionsList.setBounds(280,144,340,400);
        suggestionsList.setFixedCellWidth(250);
        suggestionsList.setFixedCellHeight(40);
        suggestionsList.setFont(new Font("Times New Roman", Font.BOLD,22));
        suggestionsList.addListSelectionListener(this.selectedWordListener);

        Color bkgColor2 = new Color(41, 162, 162);
        this.generalTextPanel.setLayout(null);
        this.generalTextPanel.setEditable(false);
        this.generalTextPanel.setBackground(bkgColor2);
        this.generalTextPanel.setBounds(0,140,900,820);
        this.generalTextPanel.setFont(new Font("Times New Roman",Font.ITALIC,23));
        generalTextPanel.setWrapStyleWord(true);
        generalTextPanel.setLineWrap(true);
        generalTextPanel.add(speakerBtn);
        speakerBtn.setVisible(false);
    }

    public MainGraphic() {
        setupOther();
        setupButton();
        setupPanel();
        setupSearchWordBox();
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

    public String wordString(ArrayList<Word> wordArrayList) {
        String string = "";
        for (Word w : wordArrayList) {
            string += "  - " + w.getWord_target() + " : " + w.getWord_explain() + "\n";
        }
        return string;
    }

    ActionListener recentBtnListener = showRecentWord -> {
        listSuggestionContainer.setVisible(false);
        suggestionsList.clearSelection();
        String recentWordString = "recent Word: \n" + wordString(Dictionary.recentWordArray);
        generalTextPanel.setText(recentWordString);
    };

    ActionListener addBtnListener = add -> {
        speakerBtn.setVisible(false);
        suggestionsList.clearSelection();
        listSuggestionContainer.setVisible(false);
        searchWordBox.setText("");
        String addedList = "Added Word list: \n" + wordString(Dictionary.addedArray);
        generalTextPanel.setText(addedList);
        SubGraphic addGraphic = new SubGraphic("addWord", windowApp);
    };


    ActionListener modifyBtnListener = modify -> {
        speakerBtn.setVisible(false);
        suggestionsList.clearSelection();
        listSuggestionContainer.setVisible(false);
        searchWordBox.setText("");
        String modifiedList = "Modified Word list: \n" + wordString(Dictionary.modifiedArray);
        generalTextPanel.setText(modifiedList);
        SubGraphic modifyGraphic = new SubGraphic("modifyWord", windowApp);
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
                if (!DictionaryManagement.isWordExists(Dictionary.recentWordArray, word.getWord_target())) {
                    DictionaryManagement.insertWord(word, Dictionary.recentWordArray);
                    DictionaryManagement.updateFile(Dictionary.recentWordArray, Dictionary.recentWordList);
                }
            } else {
                generalTextPanel.setText(DictionaryManagement.getNoti());
            }
        }
    };

}