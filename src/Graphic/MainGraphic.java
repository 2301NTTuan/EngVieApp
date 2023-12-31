package Graphic;

import Dictionary.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import Graphic.SubGraphic;

public class MainGraphic {

    //3 subgraphic objects
    SubGraphic removeWordGraphic;
    SubGraphic modifyWordGraphic;
    SubGraphic addWordGraphic;

    //Main window
    JFrame windowApp = new JFrame();

    //Main Panel
    JPanel panel = new JPanel();

    //Header Panel
    JPanel headerPanel = new JPanel();

    //Shape of Panel
    Rectangle recPanel = new Rectangle();

    //Viền searching box
    JPanel borderSeachBox = new JPanel();


    //Buttons
    JButton addBtn = new JButton("Added Words");
    JButton removeBtn = new JButton("Removed Words");
    JButton modifyBtn = new JButton("Modified Words");
    JButton speakerBtn = new JButton("Speak");
    JButton recentBtn = new JButton("Recent Words");
    JButton userBtn = new JButton("User Words");
    static JButton searchBtn = new JButton("Search");
    JButton gameBtn = new JButton("Game");
    JButton okAnswer = new JButton("OK");
    JButton selectedButton = null;

    //List of suggested word
    static JList<Object> suggestionsList = new JList<>();

    //Area contains suggestion list
    static JScrollPane listSuggestionContainer = new JScrollPane(suggestionsList);

    //Box to search
    static JTextField searchWordBox = new JTextField();

    //Box to answer question
    JTextField answerBox = new JTextField();

    //Area where to show dictionary, game, ...
    static JTextArea generalTextPanel = new JTextArea();

    //Scroll bar
    JScrollPane textScrollPane = new JScrollPane(generalTextPanel);

    //Name of App
    JLabel dictionaryLabel = new JLabel();

    //Array to store suggestion list
    static ArrayList<String> foundSuggestionList = new ArrayList<>();

    //Game Object
    Game game1 = new Game();


    private void setupPanel() {
        panel.setBounds(recPanel);
        panel.setLayout(null);
        panel.setBackground(Color.white);
        panel.add(listSuggestionContainer);
        panel.add(borderSeachBox);
        panel.add(textScrollPane);
        panel.add(headerPanel);
        panel.add(dictionaryLabel);

        dictionaryLabel.setForeground(Color.white);
        dictionaryLabel.setText("Dictionary");
        dictionaryLabel.setFont(new Font("Times New Roman",Font.ITALIC,23));
        dictionaryLabel.setBounds(380,10,200,35);

        Color bkgColor = new Color(41, 162, 162);
        headerPanel.setBounds(0,0, 910, 140);
        headerPanel.setBackground(bkgColor);
        headerPanel.add(addBtn);
        headerPanel.add(removeBtn);
        headerPanel.add(modifyBtn);
        headerPanel.add(recentBtn);
        headerPanel.add(dictionaryLabel);
        headerPanel.add(userBtn);
        headerPanel.add(speakerBtn);
        headerPanel.add(searchBtn);
        headerPanel.add(gameBtn);
        headerPanel.setLayout(null);


        windowApp.add(panel, BorderLayout.CENTER);
        windowApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowApp.setTitle("ENG-VIE Dictionary");
        windowApp.pack();
        windowApp.setVisible(true);
        windowApp.setSize(910,960);
    }

    private void setupGeneralButton(JButton button, ActionListener actionListener) {
        button.setForeground(Color.darkGray);
        button.setBackground(Color.cyan);
        button.addActionListener(actionListener);
        button.setFocusPainted(false);
    }




    private void setupButton() {

        removeBtn.setBounds(5, 50, 165, 35);
        removeBtn.setBorderPainted(false);
        removeBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(removeBtn, removeBtnListener);

        modifyBtn.setBounds(185, 50, 165, 35);
        modifyBtn.setBorderPainted(false);
        modifyBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(modifyBtn, modifyBtnListener);


        recentBtn.setBounds(365, 50, 165, 35);
        recentBtn.setBorderPainted(false);
        recentBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(recentBtn, recentBtnListener);

        addBtn.setBounds(545, 50, 165, 35);
        addBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        addBtn.setBorderPainted(false);
        setupGeneralButton(addBtn, addBtnListener);

        userBtn.setBounds(725, 50, 165, 35);
        userBtn.setBorderPainted(false);
        userBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(userBtn, usesrBtnListener);

        searchBtn.setBounds(630, 95, 100, 35);
        searchBtn.setBorderPainted(false);
        searchBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(searchBtn, searchBtnListener);

        speakerBtn.setBounds(160, 95, 100, 35);
        speakerBtn.setBorderPainted(false);
        speakerBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(speakerBtn, speakBtnListener);
        speakerBtn.setVisible(false);

        gameBtn.setBounds(745, 95, 100, 35);
        gameBtn.setBorderPainted(false);
        gameBtn.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(gameBtn, gameBtnListener);

        okAnswer.setBounds(300, 135, 100, 35);
        okAnswer.setBorderPainted(false);
        okAnswer.setFont(new Font("Times New Roman", Font.ITALIC,20));
        setupGeneralButton(okAnswer, okBtnListener);
        okAnswer.setVisible(false);
    }

    private void setupOther() {

        //set up search box
        Color bkgColor = new Color(190, 231, 231);
        searchWordBox.setBounds(2,2,335,46);
        searchWordBox.setFont(new Font("Times New Roman", Font.PLAIN,22));
        searchWordBox.setBackground(bkgColor);
        borderSeachBox.setBackground(Color.cyan);
        borderSeachBox.setLayout(null);
        borderSeachBox.setBounds(280,90,340,50);
        borderSeachBox.add(searchWordBox);

        //set up container containing list of suggestion word
        listSuggestionContainer.setBounds(280, 140, 340, 400);
        listSuggestionContainer.setOpaque(false);
        listSuggestionContainer.setBorder(BorderFactory.createEmptyBorder());
        listSuggestionContainer.setVisible(false);

        //set up suggestion list
        Color bkgColor1 = new Color(138, 231, 231);
        suggestionsList.setBackground(bkgColor1);
        suggestionsList.setBounds(280,144,340,400);
        suggestionsList.setFixedCellWidth(250);
        suggestionsList.setFixedCellHeight(40);
        suggestionsList.setFont(new Font("Times New Roman", Font.BOLD,22));
        suggestionsList.addListSelectionListener(selectedWordListener);

        // set up scroll bar
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textScrollPane.setBounds(0, 145, 893, 780);

        //set up area where to show ...
        Color bkgColor2 = new Color(41, 162, 162);
        //generalTextPanel.setLayout(null);
        generalTextPanel.setEditable(false);
        generalTextPanel.setBackground(bkgColor2);
        generalTextPanel.setBounds(0,145,910,780);
        generalTextPanel.setFont(new Font("Times New Roman",Font.ITALIC,23));
        generalTextPanel.setWrapStyleWord(true);
        generalTextPanel.setLineWrap(true);
        generalTextPanel.add(answerBox);
        generalTextPanel.add(okAnswer);

        //set up answer box
        answerBox.setEnabled(true);
        answerBox.setBounds(190, 135, 100, 35);
        answerBox.setFont(new Font("Times New Roman", Font.PLAIN,22));
        answerBox.setVisible(false);
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
                        button.setBackground(new Color(30, 115, 115));
                        selectedButton.setBackground(Color.cyan);
                        selectedButton = button;
                    }
                } else {
                    button.setBackground(new Color(30, 115, 115));
                    selectedButton = button;
                }

            }
        });
    }

    //set up search box
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

    public MainGraphic() {
        setupOther();
        setupButton();
        setupPanel();
        setupSearchWordBox();
        colorAllChangeButton();
    }

    //to String
    public static String wordString(ArrayList<Word> wordArrayList) {
        String string = "";
        for (Word w : wordArrayList) {
            if (w.getWord_explain().isEmpty() || wordArrayList == Dictionary.deletedArray) {
                string += " - " + w.getWord_target() + "\n";
            } else {
                string += " - " + w.getWord_target() + " : " + w.getWord_explain() + "\n";
            }
        }
        return string;
    }

    // listen recentBtn to act
    ActionListener recentBtnListener = showRecentWord -> {
        setFalseGame();
        closeAllSubGraphic();
        listSuggestionContainer.setVisible(false);
        suggestionsList.clearSelection();
        String recentWordString = "recent Word: \n" + wordString(Dictionary.recentWordArray);
        generalTextPanel.setText(recentWordString);
    };

    // listen searchBtn to act
    ActionListener searchBtnListener = search -> {
        listSuggestionContainer.setVisible(false);
        suggestionsList.clearSelection();
        if (!searchWordBox.getText().isEmpty()) {
            Word searchWord = DictionaryManagement.dictionaryLookup(searchWordBox.getText().replaceAll("\\s+", " ").trim());
            if (searchWord == null) {
                generalTextPanel.setText("Not found! Due to " + DictionaryManagement.getNoti());
            } else {
                speakerBtn.setVisible(true);
                String searchedWord = "Searched Word: \n" + searchWord.getWord_target() + " : " + searchWord.getWord_explain();
                generalTextPanel.setText(searchedWord);
            }
        }
    };

    //to show some dictionaries
    public static void showSomeDictionaries(ArrayList<Word> arrayList) {
        suggestionsList.clearSelection();
        listSuggestionContainer.setVisible(false);
        searchWordBox.setText("");
        String List;
        if (arrayList == Dictionary.addedArray) {
            List = "Added Word List: \n" + wordString(arrayList);
        } else if (arrayList == Dictionary.modifiedArray){
            List = "Modified Word List: \n" + wordString(arrayList);
        } else {
            List = "Deleted Word List: \n" + wordString(arrayList);
        }
        generalTextPanel.setText(List);
    }

    // listen addBtn to act
    ActionListener addBtnListener = add -> {
        setFalseGame();
        closeAllSubGraphic();
        speakerBtn.setVisible(false);
        showSomeDictionaries(Dictionary.addedArray);
        addWordGraphic = new SubGraphic("addWord", windowApp);
    };

    // listen modifyBtn to act
    ActionListener modifyBtnListener = modify -> {
        setFalseGame();
        closeAllSubGraphic();
        speakerBtn.setVisible(false);
        showSomeDictionaries(Dictionary.modifiedArray);
        modifyWordGraphic = new SubGraphic("modifyWord", windowApp);
    };

    // listen removeBtn to act
    ActionListener removeBtnListener = remove -> {
        setFalseGame();
        closeAllSubGraphic();
        speakerBtn.setVisible(false);
        showSomeDictionaries(Dictionary.deletedArray);
        removeWordGraphic = new SubGraphic("removeWord", windowApp);
    };

    //close subgraphic
    public void closeAllSubGraphic() {
        if (addWordGraphic != null) {
            addWordGraphic = null;
        }
        if (removeWordGraphic != null) {
            removeWordGraphic = null;
        }
        if (modifyWordGraphic != null) {
            modifyWordGraphic = null;
        }
    }

    // listen speakBtn to act
    ActionListener speakBtnListener = speak -> {
        if (!searchWordBox.getText().isEmpty()) {
            DictionaryManagement.textToSpeech(searchWordBox.getText());
        }
    };

    // listen selection of user
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

    // listen userBtn to act
    ActionListener usesrBtnListener = showUserWord -> {
        setFalseGame();
        closeAllSubGraphic();
        speakerBtn.setVisible(false);
        suggestionsList.clearSelection();
        listSuggestionContainer.setVisible(false);
        searchWordBox.setText("");
        String userList = "User Words list: \n" + wordString(Dictionary.userArray);
        generalTextPanel.setText(userList);
    };

    //game
    public void playGame() {
        answerBox.setVisible(true);
        okAnswer.setVisible(true);
        game1.getQuestion();
        generalTextPanel.setText(game1.getQuestionForGraphic);
    }

    // listen gameBtn to act
    ActionListener gameBtnListener = game -> {
        speakerBtn.setVisible(false);
        suggestionsList.clearSelection();
        listSuggestionContainer.setVisible(false);
        searchWordBox.setText("");
        playGame();
    };

    //listen okBtn to act
    ActionListener okBtnListener = OK -> {
        String answerQuestion = answerBox.getText().replaceAll("\\s+", " ").trim();
        if (game1.markFuction == 1) {
            if (Integer.parseInt(answerQuestion) >= 1 && Integer.parseInt(answerQuestion) <= 4) {
                game1.answerQ(answerQuestion);
                generalTextPanel.setText(game1.getQuestionForGraphic + game1.getNoti());
                answerBox.setText("");
            }
        } else if (game1.markFuction == 2){
            if (answerQuestion.equalsIgnoreCase("y")){
                answerBox.setText("");
                playGame();
            } else if (answerQuestion.equalsIgnoreCase("n")){
                generalTextPanel.setText("You Stopped Game!");
                answerBox.setVisible(false);
                okAnswer.setVisible(false);
                answerBox.setText("");
            }
        }
    };
    public void setFalseGame() {
        answerBox.setVisible(false);
        okAnswer.setVisible(false);
        answerBox.setText("");
    }
}