package Dictionary;
import java.util.ArrayList;
import java.util.Scanner;

//Class quản lý giao diện dòng lệnh của APP tiếng anh
public class DictionaryCommandLine extends DictionaryManagement{

    //show toàn bộ thư viện chính. Do thư viện chính rất lớn vì vậy không nên show ra.
    public static void showMainDictionary() {
        showDictionary(wordArray);
    }

    //Show các thư viện con
    public static void showDictionary(ArrayList<Word> arrayList) {
        System.out.printf("%-5s%c %-18s%c %-23s%n", "No", '|', "English", '|', "Vietnamese");
        int index = 1;
        for (Word w : arrayList) {
            System.out.printf("%-5d%c %-18s%c %-23s%n", index, '|', w.getWord_target(), '|', w.getWord_explain());
            index++;
        }
    }

    //Quản lý các lựa chọn trong APP tiếng anh
    public void dictionaryAdvanced() {
        int action = -23;
        while (action != 0) {
            System.out.println("Welcome to My Application!\n"
                    + "[0] Exit \n[1] Add\n"
                    + "[2] Remove\n[3] Modify\n"
                    + "[4] Display\n[5] Lookup\n"
                    + "[6] Search\n[7] Game\n"
                    + "[8] Import from file\n[9] Export to file\n"
                    + "[10] Pronunciation\n"
                    + "Your action: ");

            try {
                Scanner scanner = new Scanner(System.in);
                action = Integer.parseInt(scanner.nextLine());
                switch (action) {
                    case 0:
                        // Exit
                        Main.option = -1;
                        System.out.println("Exiting the application.");
                        break;
                    case 1:
                        // Add functionality
                        System.out.println("addWord Funcion!");
                        insertFromCommandLine();
                        break;
                    case 2:
                        // Remove functionality
                        System.out.println("Remove Fuction!");
                        deleteWord("");
                        break;
                    case 3:
                        // Update functionality
                        System.out.println("Modify Fuction!");
                        modifyWord("", "");
                        break;
                    case 4:
                        // Display functionality
                        int option;
                        System.out.println("What dictionary do you want to show?\n"
                                + "1. Added Words\n"
                                + "2. Modified Words\n"
                                + "3. Deleted Words\n"
                                + "4. Recent Words\n"
                                + "5. User Words\n"
                                + "Your option: ");
                        option = scanner.nextInt();
                        switch (option) {
                            case 1:
                                showDictionary(addedArray);
                                break;
                            case 2:
                                showDictionary(modifiedArray);
                                break;
                            case 3:
                                showDictionary(deletedArray);
                                break;
                            case 4:
                                showDictionary(recentWordArray);
                                break;
                            case 5:
                                showDictionary(userArray);
                                break;
                        }
                        break;
                    case 5:
                        // Lookup functionality
                        System.out.println("Lookup Fuction!");
                        Word w = dictionaryLookup("");
                        if (w == null) {
                            System.out.println("Not found this word.");
                        } else {
                            System.out.println(w.getWord_target() + "\t" + w.getWord_explain());
                        }
                        break;
                    case 6:
                        // Search functionality
                        System.out.println("Search Fuction!");
                        ArrayList<String> searchWord = dictionarySearch("");
                        if (!searchWord.isEmpty()) {
                            System.out.println("Searched Word List: ");
                            for (String stringWord : searchWord) {
                                System.out.println(stringWord);
                            }
                        }
                        break;
                    case 7:
                        // Game functionality
                        Game game = new Game();
                        do {
                            game.getQuestion();
                            game.answerQ("");
                            boolean mark = game.playAgain("");
                            if (!mark) {
                                break;
                            }
                        } while (true);
                        break;
                    case 8:
                        // Import from file functionality
                        System.out.println("Insert All Files to Dictionary Lists");
                        insertAllFile();
                        break;
                    case 9:
                        // Export to file functionality
                        updateAllFile();
                        break;
                    case 10:
                        //Pronunciation
                        textToSpeech("");
                    default:
                        System.out.println("Action not supported.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Action not supported");
            }
        }
    }
}
