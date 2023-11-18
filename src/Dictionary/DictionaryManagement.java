package Dictionary;
import java.io.*;
import java.util.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class DictionaryManagement extends Dictionary{
    private static String noti;
    static Scanner scanner = new Scanner(System.in);
    public static String getNoti() {
        return noti;
    }

    public static void setNoti(String noti) {
        DictionaryManagement.noti = noti;
    }

    public static void insertFromCommandLine() {
        int numberOfWords;
        do {
            System.out.println("Enter the number of words:");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer value.");
                scanner.next();
            }
            numberOfWords = scanner.nextInt();
        } while (numberOfWords <= 0);
        scanner.nextLine();
        for (int i=0; i < numberOfWords; i ++) {
            System.out.println("Enter Word " + (i + 1));
            System.out.println("\t + Enter the English word: ");
            System.out.print("\t");
            String word_target  = scanner.nextLine().toLowerCase();
            System.out.println("\t + Enter the Vietnamese meaning: ");
            System.out.print("\t");
            String word_explain = scanner.nextLine().toLowerCase();
            addWord(word_target, word_explain);
        }
        System.out.println("Action Completed");
    }

    public static void addWord(String word_target, String word_explain) {
        Word w = new Word(word_target, word_explain);
        if (!isWordExists(addedArray, word_target)) {
            insertWord(w, addedArray);
            updateFile(addedArray, addedList);
            insertWord(w, userArray);
            updateFile(userArray, userList);
            System.out.println("Added!");
            setNoti("Added!");
        } else {
            System.out.println("That word has been added previously!");
            setNoti("That word has been added previously!");
        }
    }

    public static void deleteWord(String word_target) {
        try {
            if (word_target == null || word_target.isEmpty()) {
                System.out.println("Enter word:");
                word_target = scanner.nextLine();
            }
            word_target = word_target.toLowerCase();
            if ((!isWordExists(deletedArray, word_target))) {
                if (isWordExists(userArray, word_target)) {
                    Word word;
                    word = binarySearchWord(word_target, userArray);
                    userArray.remove(word);
                    insertWord(word, deletedArray);
                    updateFile(deletedArray, deletedList);
                    System.out.println("Deleted");
                    setNoti("Deleted");
                } else {
                    Word word = binarySearchWord(word_target, wordArray);
                    if (word != null) {
                        insertWord(word, deletedArray);
                        updateFile(deletedArray, deletedList);
                        System.out.println("Deleted");
                        setNoti("Deleted");
                    } else {
                        System.out.println("Not found word!");
                        setNoti("Not found word!");
                    }
                }
            } else {
                System.out.println("This word has been removed previously!");
                setNoti("This word has been removed previously!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            setNoti("Error!");
        }
    }

    public static void modifyWord(String word_target, String explain) {
        try {
            if (word_target == null || word_target.isEmpty()) {
                System.out.println("Enter word:");
                word_target = scanner.nextLine().replaceAll("\\s+", " ").trim().toLowerCase();
            }
            if (explain == null || explain.isEmpty()) {
                System.out.println("Enter your definition:");
                explain = scanner.nextLine().replaceAll("\\s+", " ").trim();
            }
            Word word;
            Word temp;
            if ((word = binarySearchWord(word_target, userArray)) != null) {
                word.setWord_target(word_target);
                word.setWord_explain(explain);
                updateFile(userArray, userList);
                if ((temp = binarySearchWord(word_target, modifiedArray)) != null) {
                    temp.setWord_explain(explain);
                    updateFile(modifiedArray, modifiedList);
                } else {
                    insertWord(word, modifiedArray);
                    updateFile(modifiedArray, modifiedList);
                }
                System.out.println("This word's explain has been modified!");
                setNoti("This word's explain has been modified!");
            } else if ((binarySearchWord(word_target, wordArray)) != null){
                Word tempWord = new Word();
                tempWord.setWord_target(word_target);
                tempWord.setWord_explain(explain);
                insertWord(tempWord, userArray);
                insertWord(tempWord, modifiedArray);
                updateFile(modifiedArray, modifiedList);
                updateFile(userArray, userList);
                System.out.println("This word's explain has been modified!");
                setNoti("This word's explain has been modified!");
            } else {
                System.out.println("Not found word!");
                setNoti("Not found word!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            setNoti("Error!");;
        }
    }


    public static Word dictionaryLookup(String word_target) {
        Word w;
        if (word_target == null || word_target.isEmpty()) {
            System.out.println("Enter the English word you want to look up:");
            word_target = scanner.nextLine().toLowerCase();
        }
        if (isWordExists(deletedArray, word_target)) {
            System.out.println("This word was deleted!");
            setNoti("This word was deleted!");
        } else if (isWordExists(userArray, word_target)) {
            w = binarySearchWord(word_target, userArray);
            if (!isWordExists(recentWordArray, w.getWord_target())) {
                Word tempWord = new Word(w.getWord_target(), "");
                insertWord(tempWord, recentWordArray);
                updateFile(recentWordArray, recentWordList);
            }
            return w;
        } else if ((w = binarySearchWord(word_target, wordArray)) != null) {
            if (!isWordExists(recentWordArray, w.getWord_target())) {
                Word tempWord = new Word(w.getWord_target(), "");
                insertWord(tempWord, recentWordArray);
                updateFile(recentWordArray, recentWordList);
            }
            return w;
        }
        return null;
    }


    public static ArrayList<String> dictionarySearch(String word_target) {
        ArrayList<String> suggestionArray = new ArrayList<>();
        if (word_target == null || word_target.isEmpty()) {
            System.out.println("Searching word: ");
            word_target = scanner.nextLine();
        }
        word_target = word_target.toLowerCase();
        int index = 0;
        int position = binarySearchPosition(word_target, wordArray);
        int sizeOfWordArray = wordArray.size();
        if (position < 0) {
            System.out.println("Not found!");
            setNoti("Not found!");
        } else {
            do {
                String temp = wordArray.get(position + index).getWord_target().toLowerCase();
                if (temp.startsWith(word_target) && !isWordExists(deletedArray, temp)) {
                    suggestionArray.add(temp);
                    index++;
                } else {
                    break;
                }
            } while ((index + position) < sizeOfWordArray);
        }
        return suggestionArray;
    }

    public static void insertFromFileToList(ArrayList<Word> list, String filename) {
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
            String word_target;
            String word_explain = "";
            Word currentWord = null;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                int lengthLine = line.length();
                if (line.startsWith("*")) {
                    if (currentWord != null) {
                        if (!isWordExists(list, currentWord.getWord_target())) {
                            insertWord(currentWord, list);
                        }
                    }
                    int index = 0;
                    while (index < lengthLine && line.charAt(index) != ' ') {
                        index++;
                    }
                    currentWord = new Word();
                    try {
                        word_target = line.substring(1, index).trim().toLowerCase();
                        currentWord.setWord_target(word_target);
                        word_explain = line.substring(index, lengthLine).replaceAll("\\s+", " ").trim();
                        currentWord.setWord_explain(word_explain);
                    } catch (Exception e) {
                        System.out.println("Error" + e);
                        setNoti("Error" + e);
                    }
                } else {
                    word_explain += line.replaceAll("\\s+", " ");
                    currentWord.setWord_explain(word_explain);
                }
            }
            if (currentWord != null) {
                if (!isWordExists(list, currentWord.getWord_target())) {
                    insertWord(currentWord, list);
                }
            }
            fileScanner.close();
            System.out.println("Data in " + filename + " inserted successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public static void insertAllFile() {
        insertFromFileToList(wordArray, mainDictionary);
        insertFromFileToList(addedArray, addedList);
        insertFromFileToList(deletedArray, deletedList);
        insertFromFileToList(modifiedArray, modifiedList);
        insertFromFileToList(recentWordArray, recentWordList);
        insertFromFileToList(userArray, userList);
    }

    public static void updateFile(ArrayList<Word> arrayList, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Word w : arrayList) {
                String line = "*" + w.getWord_target() + " \t" + w.getWord_explain();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Update file " + fileName + " Complete!");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void updateAllFile() {
        updateFile(addedArray, addedList);
        updateFile(deletedArray, deletedList);
        updateFile(modifiedArray, modifiedList);
        updateFile(recentWordArray, recentWordList);
        updateFile(userArray, userList);
        updateFile(wordArray, mainDictionary);
    }

   public static boolean isWordExists(ArrayList<Word> arrayList, String word_target) {
        Word w = binarySearchWord(word_target, arrayList);
        if (w == null) {
            return false;
        }
        return true;
    }

    public static Word binarySearchWord(String word_target, ArrayList<Word> Array) {
        if (Array == Dictionary.wordArray) {
            int left = 0;
            int right = Array.size() - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                int comparisonResult = Array.get(mid).getWord_target().compareTo(word_target);

                if (comparisonResult == 0) {
                    return Array.get(mid);
                } else if (comparisonResult > 0) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return null;
        } else {
            for (Word word : Array) {
                if (word.getWord_target().equals(word_target.trim())) {
                    return word;
                }
            }
        }
        return null;
    }

    public static int binarySearchPosition(String target, ArrayList<Word> list) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (list.get(mid).getWord_target().startsWith(target)) {
                while (mid > 0 && list.get(mid - 1).getWord_target().startsWith(target)) {
                    mid--;
                }
                return mid;
            } else if (list.get(mid).getWord_target().compareTo(target) > 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public static void insertWord(Word newWord, ArrayList<Word> wordList) {
        int left = 0;
        int right = wordList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparisonResult = newWord.getWord_target().compareTo(wordList.get(mid).getWord_target());

            if (comparisonResult == 0) {
                break;
            } else if (comparisonResult < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        wordList.add(left, newWord);
    }

    public static void textToSpeech(String word) {
        if (word == null || word.isEmpty()) {
            System.out.println("Enter word:");
            word = scanner.nextLine().toLowerCase();
        }
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice syntheticVoice = voiceManager.getVoice("kevin16");
        syntheticVoice.allocate();
        syntheticVoice.speak(word);
        syntheticVoice.deallocate();
    }

}
