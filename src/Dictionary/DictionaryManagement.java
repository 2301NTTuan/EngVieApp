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
            String word_explain = scanner.nextLine();
            addWord(word_target, word_explain);
        }
        System.out.println("Action Completed");
    }

    public static void addWord(String word_target, String word_explain) {
        if (!isWordExists(wordArray, word_target)) {
            Word w = new Word(word_target, word_explain);
            insertWord(w, wordArray);
            if (!isWordExists(addedArray, word_target)) {
                insertWord(w, addedArray);
            }
        }
    }

    public static void deleteWord(String word_target) {
        try {
            if (word_target == null || word_target.isEmpty()) {
                System.out.println("Enter word:");
                word_target = scanner.nextLine().toLowerCase();
            }
            Word word;
            if ((word = binarySearchWord(word_target, wordArray)) != null) {
                if (!isWordExists(deletedArray, word_target)) {
                    insertWord(word, deletedArray);
                    updateFile(deletedArray, deletedList);
                }
                wordArray.remove(word);
                updateFile(wordArray, mainDictionary);
                noti = "This word has been removed.\n";
            } else {
                noti = "Not found word!\n";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            noti = "Error!";
        }
    }

    public static void modifyWord(String word_target, String explain) {
        try {
            if (word_target == null || word_target.isEmpty()) {
                System.out.println("Enter word:");
                word_target = scanner.nextLine();
            }
            Word word;
            word = binarySearchWord(word_target.toLowerCase(), wordArray);
            if (word != null) {
                if (explain == null || explain.isEmpty()) {
                    System.out.println("Enter your definition:");
                    explain = scanner.nextLine();
                }
                word.setWord_target(word_target);
                word.setWord_explain(explain);
                insertWord(word, modifiedArray);
                updateFile(modifiedArray, modifiedList);
                updateFile(wordArray, mainDictionary);
                noti = "This word's explain has been modified!\n";
            } else {
                noti = "Not found this word!\n";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            noti = "Error!";
        }
    }

    public static Word dictionaryLookup(String word_target) {
        Word w;
        if (word_target == null || word_target.isEmpty()) {
            System.out.println("Enter the English word you want to look up:");
            word_target = scanner.nextLine().toLowerCase();
        }
        if ((w = binarySearchWord(word_target, wordArray)) != null) {
            if (!isWordExists(recentWordArray, w.getWord_target())) {
                insertWord(w, recentWordArray);
            }
            return w;
        }
        return null;
    }


    public static ArrayList<String> dictionarySearch(String word_target) {
        ArrayList<String> suggestionArray = new ArrayList<>();
        if (word_target == null || word_target.isEmpty()) {
            System.out.println("Searching word: ");
            word_target = scanner.nextLine().toLowerCase();
        }
        int index = 0;
        int position = binarySearchPosition(word_target, wordArray);
        int sizeOfWordArray = wordArray.size();
        if (position < 0) {
            noti = "Not found!\n";
        } else {
            do {
                String temp = wordArray.get(position + index).getWord_target();
                if (temp.startsWith(word_target)) {
                    suggestionArray.add(temp);
                    index++;
                } else {
                    break;
                }
            } while ((index + position) < sizeOfWordArray && index < 10);
        }
        return suggestionArray;
    }

    public static void insertFromFileToList(ArrayList<Word> list, String filename) {
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split("\t");
                if (splitData.length >= 2) {
                    Word w = new Word();
                    w.setWord_target(splitData[0]);
                    w.setWord_explain(splitData[1]);
                    if (!isWordExists(list, w.getWord_target())) {
                        insertWord(w, list);
                    }
                }
            }
            fileScanner.close();
            System.out.println("Data inserted successfully.");
            noti = "Data inserted successfully.";
        } catch (FileNotFoundException e) {
            System.out.println("Find not found");
            noti = "Find not found";
            e.printStackTrace();
        }
    }

    public static void insertAllFile() {
        insertFromFileToList(wordArray, mainDictionary);
        insertFromFileToList(addedArray, addedList );
        insertFromFileToList(deletedArray, deletedList);
        insertFromFileToList(modifiedArray, modifiedList);
        insertFromFileToList(recentWordArray, recentWordList);
    }

    public static void updateFile(ArrayList<Word> arrayList, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Word w : arrayList) {
                String line = w.getWord_target() + "\t" + w.getWord_explain();
                writer.write(line);
                writer.newLine();
            }
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

    public static void textToSpeech(String text) {
        if (text == null || text.isEmpty()) {
            System.out.println("Enter word:");
            text = scanner.nextLine();
        }
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice syntheticVoice = voiceManager.getVoice("kevin16");
        syntheticVoice.allocate();
        syntheticVoice.speak(text);
        syntheticVoice.deallocate();
    }

}
