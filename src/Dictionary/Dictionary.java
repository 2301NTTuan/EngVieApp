package Dictionary;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Dictionary {
    public static final String modifiedList = getPath() + "/modifiedList.txt";
    public static final String deletedList = getPath() + "/deletedList.txt";
    public static final String addedList = getPath() + "/addedList.txt";
    public static final String recentWordList = getPath() + "/recentWordList.txt";
    public static final String userList = getPath() + "/userList.txt";

    public static final String mainDictionary = getPath() + "/dictionaries.txt";
    public static ArrayList<Word> wordArray = new ArrayList<>();

    public static ArrayList<Word> modifiedArray = new ArrayList<>();

    public static ArrayList<Word> deletedArray = new ArrayList<>();
    public static ArrayList<Word> addedArray = new ArrayList<>();

    public static  ArrayList<Word> recentWordArray = new ArrayList<>();
    public static ArrayList<Word> userArray = new ArrayList<>();

    public static String getPath () {
        return Paths.get("").toAbsolutePath() + "/src/Dictionaries/";
    }

}
