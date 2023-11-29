package Dictionary;

import java.util.*;

public class Game extends Dictionary {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    String[] options = new String[4];
    String questionWord;
    String meaning;
    public int markFuction = 0;
    public String noti;
    public String getQuestionForGraphic;

    public String getNoti() {
        return noti;
    }

    public void getQuestion() {
        getQuestionForGraphic = "";
        int sizeWordArray = wordArray.size();
        if (sizeWordArray > 0) {
            int randomIndex = random.nextInt(sizeWordArray);
            questionWord = wordArray.get(randomIndex).getWord_target();
            meaning = wordArray.get(randomIndex).getWord_explain();

            System.out.println("Question: What is the meaning of \"" + meaning + "\" ?");
            getQuestionForGraphic += "Question: What is the meaning of \"" + meaning + "\" ? \n";
            if (questionWord != null) {
                options[0] = questionWord;
            }
            for (int i = 1; i < 4; i++) {
                int randomOptionIndex = random.nextInt(wordArray.size());
                String option = wordArray.get(randomOptionIndex).getWord_target();
                options[i] = option;
            }
            shuffleArray(options);
            for (int i = 0; i < 4; i++) {
                String option = options[i];
                System.out.println((i + 1) + ". " + option);
                getQuestionForGraphic += (i + 1) + ". " + option + "\n";
            }
            System.out.print("Your choice (1-4): ");
            getQuestionForGraphic += "Your choice (1-4): \n";
        }
        markFuction = 1;
    }

    public void answerQ(String answer) {
        noti = "";
        if (answer == null || answer.isEmpty()) {
            while (true) {
                answer = scanner.nextLine();
                if (Integer.parseInt(answer) >= 1 && Integer.parseInt(answer) <= 4) {
                    break;
                }
            }
        }
        if (options[Integer.parseInt(answer) - 1].equals(questionWord)) {
            System.out.println("Correct!");
            noti += "Correct \n";
        } else {
            System.out.println("Incorrect. The correct answer is: " + questionWord);
            noti += "Incorrect. The correct answer is: " + questionWord + "\n";
        }
        System.out.print("Do you want to play again? (y/n): ");
        noti += "Do you want to play again? (y/n): \n";
        markFuction = 2;
    }

    //Trộn mảng
    private static void shuffleArray(String[] array) {
        int n = array.length;
        Random random = new Random();

        for (int i = n - 1; i > 0; i--) {
            int randomIndex = random.nextInt(i + 1);
            String temp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = temp;
        }
    }

    public boolean playAgain(String playAgain) {
        if (playAgain == null || playAgain.isEmpty()) {
            playAgain = scanner.nextLine();
        }
        if (!playAgain.equalsIgnoreCase("y")) {
            System.out.println("Out Game!");
            return false;
        }
        return true;
    }
}
