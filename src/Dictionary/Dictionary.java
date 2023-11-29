package Dictionary;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Dictionary extends Word{

    //File các từ đã thay đổi
    public static final String modifiedList = getPath() + "/modifiedList.txt";

    //File các từ đã xóa
    public static final String deletedList = getPath() + "/deletedList.txt";

    //File các từ đã thêm
    public static final String addedList = getPath() + "/addedList.txt";

    //File các từ tìm gần đây
    public static final String recentWordList = getPath() + "/recentWordList.txt";

    //File từ vựng của người dùng
    public static final String userList = getPath() + "/userList.txt";

    //File Thư viện chính
    public static final String mainDictionary = getPath() + "/dictionaries.txt";

    //Mảng chứa thư viện chính
    public static ArrayList<Word> wordArray = new ArrayList<>();

    //Mảng chứa các từ đã thay đổi
    public static ArrayList<Word> modifiedArray = new ArrayList<>();

    //Mảng chứa các từ đã xóa
    public static ArrayList<Word> deletedArray = new ArrayList<>();

    //Mảng chứa các từ đã thêm
    public static ArrayList<Word> addedArray = new ArrayList<>();

    //Mảng chứa các từ tìm gần đây
    public static  ArrayList<Word> recentWordArray = new ArrayList<>();

    //Mảng chứa từ vựng của người dùng
    public static ArrayList<Word> userArray = new ArrayList<>();


    /* Lấy ra đường gần tuyệt đối đến nơi chứa các file từ vựng. */
    public static String getPath () {
        return Paths.get("").toAbsolutePath() + "/src/Dictionaries/";
    }

}