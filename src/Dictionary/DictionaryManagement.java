package Dictionary;
import java.io.*;
import java.util.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

//Class quản lý các chức năng của APP tiếng anh như thêm, sửa, xóa,...
public class DictionaryManagement extends Dictionary{

    /** Comment Chung
      Do giao diện đồ họa và  giao diện dòng lệnh có một số sự khác biệt khi truyền tham số vào các hàm chức năng và
      Do code thực hiện quản lý cả giao diện dòng lệnh và giao diện đồ họa nên có một số chức năng cần kiểm tra xem tham
     số truyền vào hàm đã có hay chưa.
     */

    //Sử dụng để chứa thông báo
    private static String noti;

    //Tạo scanner để đọc dữ liệu
    static Scanner scanner = new Scanner(System.in);

    //Lấy thông báo
    public static String getNoti() {
        return noti;
    }

    //Set thông báo
    public static void setNoti(String noti) {
        DictionaryManagement.noti = noti;
    }

    //Lấy từ vựng người dùng nhập từ giao diện dòng lệnh qua bàn phím
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

    /** Chức năng thêm từ
        - Kiểm tra xem từ đã có trong danh sách từ đã thêm hay chưa. Chưa có thì thêm vào và cập nhật file
        - Kiểm tra xem từ đã có trong danh sách từ đã xóa hay chưa. Chưa có thì add vào danh sách người dùng.
            Nếu có rồi thì xóa khỏi danh sách đã xóa sau đó add vào danh sách người dùng.
     */
    public static void addWord(String word_target, String word_explain) {
        setNoti("");
        Word w = new Word(word_target, word_explain);
        if (!isWordExists(addedArray, word_target)) {
            insertWord(w, addedArray);
            updateFile(addedArray, addedList);
        }
        if (!isWordExists(deletedArray, word_target)) {
            if (!isWordExists(userArray, word_target)) {
                insertWord(w, userArray);
                updateFile(userArray, userList);
                System.out.println("Added!");
                setNoti("Added!");
            } else {
                System.out.println("That word has been added previously!");
                setNoti("That word has been added previously!");
            }
        } else {
            insertWord(w, userArray);
            updateFile(userArray, userList);
            Word temp = binarySearchWord(word_target, deletedArray);
            deletedArray.remove(temp);
            updateFile(deletedArray, deletedList);
            System.out.println("Added!");
            setNoti("Added!");
        }
    }


    /** Chức năng xóa từ
        - Nếu từ chưa có trong danh sách từ đã xóa:
            Đầu tiên, tìm từ đó trong danh sách của người dùng nếu có thì xóa nếu không có thì
            tìm trong thư viện chính nhưng do không cho phép xóa từ trong thư viện chính nên nếu tìm được
            trong thư viện chính thì chỉ add nó vào danh sách từ đã xóa để khi look up hoặc search
            có thể qua danh sách từ đã xóa để không hiện thị các từ đã xóa đó.
     */
    public static void deleteWord(String word_target) {
        setNoti("");
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
                    updateFile(userArray, userList);
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
            } else if (isWordExists(deletedArray, word_target)) {
                System.out.println("This word has been removed previously!!");
                setNoti("This word has been removed previously!!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            setNoti("Error!");
        }
    }

    /** Chức năng chỉnh sử từ
        Tương tự chức năng xóa từ, thì ở chức năng này chỉ cho phép thay đổi định nghĩa
        của từ trong danh sách từ vựng của người dùng chứ không được thay đổi trong thư viện chính.
        Mà chỉ add vào danh sách các từ đã thay đổi để khi look up hoặc search có thể qua đó hiện thị
        định nghĩa mà người dùng đã thay đổi.
     */
    public static void modifyWord(String word_target, String explain) {
        setNoti("");
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
            if (!isWordExists(deletedArray, word_target)) {
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
                    System.out.println("Modified!");
                    setNoti("Modified!");
                } else if ((binarySearchWord(word_target, wordArray)) != null){
                    Word tempWord = new Word();
                    tempWord.setWord_target(word_target);
                    tempWord.setWord_explain(explain);
                    insertWord(tempWord, userArray);
                    updateFile(userArray, userList);
                    if ((temp = binarySearchWord(word_target, modifiedArray)) != null) {
                        temp.setWord_explain(explain);
                        updateFile(modifiedArray, modifiedList);
                    } else {
                        insertWord(tempWord, modifiedArray);
                        updateFile(modifiedArray, modifiedList);
                    }
                    System.out.println("Modified!");
                    setNoti("Modified!");
                } else {
                    System.out.println("Not found word!");
                    setNoti("Not found word!");
                }
            } else {
                System.out.println("This word has been removed previously!");
                setNoti("This word has been removed previously!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            setNoti("Error!");;
        }
    }

    /** Chức năng tra từ
        Tìm trong danh sách từ đã xóa, nếu tồn tại thì từ đã bị xóa, nếu không tồn tại thì tìm trong
        danh sách của người dùng trước, nếu không có thì mới tìm trong thư viện chính.
     */
    public static Word dictionaryLookup(String word_target) {
        setNoti("");
        Word w;
        if (word_target == null || word_target.isEmpty()) {
            System.out.println("Enter the English word you want to look up:");
            word_target = scanner.nextLine().toLowerCase();
        }
        if (isWordExists(deletedArray, word_target)) {
            System.out.println("This word has been deleted previously!");
            setNoti("This word has been deleted previously!");
        } else if (isWordExists(userArray, word_target)) {
            w = binarySearchWord(word_target, userArray);
            if (!isWordExists(recentWordArray, word_target)) {
                Word tempWord = new Word(word_target, "");
                insertWord(tempWord, recentWordArray);
                updateFile(recentWordArray, recentWordList);
            }
            return w;
        } else if ((w = binarySearchWord(word_target, wordArray)) != null) {
            if (!isWordExists(recentWordArray, word_target)) {
                Word tempWord = new Word(word_target, "");
                insertWord(tempWord, recentWordArray);
                updateFile(recentWordArray, recentWordList);
            }
            return w;
        } else {
            setNoti("Not found");
        }
        return null;
    }


    /** Chức năng tìm kiếm từ theo ký tự
        Tìm kiếm từ trong thư viện chính theo ký tự bắt đầu của từ và kiểm tra xem từ đó tồn tại trong danh sách từ bị xóa chưa.
        Nếu chưa thì thêm nó vào danh sách gợi ý. Để phục vụ cho chức năng gợi ý từ trong giao diện đồ họa.
     */
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

    /**
        Đọc các file từ vựng và file thư viện chính rồi lưu vào các danh sách từ tương ứng
        Các từ vựng trong file bắt đầu bằng ký tự * và ngăn cách từ tiếng anh với định nghĩa của nó
        qua 1 space và 1 tab.

        Khi gặp * thì nếu currentWord != null tức là từ trước đó chưa được lưu vào danh sách nên cần lưu nó lại, còn khi
        currentWord == null thì bắt đầu ghi word_target bắt đầu từ ký tự có index = 1(do index = 0 là dấu *) đến vị trí trước
        khi có ký tự space(ký tự khoảng trắng). word_explain thì lưu từ vị trí index đến khi hết dòng nếu sang dòng mới mà
        chưa gặp dấu * thì tiếp tục lưu.
     */
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

    /**
        Lấy toàn dữ liệu trong toàn bộ các file lưu vào các mảng để xử lý
     */
    public static void insertAllFile() {
        insertFromFileToList(wordArray, mainDictionary);
        insertFromFileToList(addedArray, addedList);
        insertFromFileToList(deletedArray, deletedList);
        insertFromFileToList(modifiedArray, modifiedList);
        insertFromFileToList(recentWordArray, recentWordList);
        insertFromFileToList(userArray, userList);
    }

    /**
        Cập nhật dữ liệu từ các mảng ra lại file
     */
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

    /**
     Update toàn bộ
     */
    public static void updateAllFile() {
        updateFile(addedArray, addedList);
        updateFile(deletedArray, deletedList);
        updateFile(modifiedArray, modifiedList);
        updateFile(recentWordArray, recentWordList);
        updateFile(userArray, userList);
        updateFile(wordArray, mainDictionary);
    }

    /**
        Kiểm tra sự tồn tại của một từ trong một danh sách
        @return false nếu chưa tồn tại
     */
   public static boolean isWordExists(ArrayList<Word> arrayList, String word_target) {
        Word w = binarySearchWord(word_target, arrayList);
        if (w == null) {
            return false;
        }
        return true;
    }

    /**
     Tìm kiếm nhị phân trên danh sách các từ đã được sắp xếp để tối ưu hóa.
     */
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

    /**
        Lấy vị trí của từ trong danh sách để sử dụng cho danh sách gợi ý
     */
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

    /**
        Chèn từ vựng vào danh sách theo thứ tự bảng chữ cái
     */
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

    /**
        Chuyển text thành giọng nói.
     */
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
