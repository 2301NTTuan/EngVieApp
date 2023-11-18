package Dictionary;

import Graphic.MainGraphic;
import java.util.Scanner;

public class Main {
    public static int option = -1;
    public static void main(String[] args) throws Exception {
       do {
          System.out.println("Select version you want to use: "
                  + "\n0. Exit."
                  + "\n1. Command Line Version."
                  + "\n2. Graphic Version.");
          System.out.print("Your option: ");
          Scanner scanner = new Scanner(System.in);
          option = scanner.nextInt();
          switch (option) {
              case 0:
                  break;
              case 1:
                  DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
                  DictionaryManagement.insertAllFile();
                  dictionaryCommandLine.dictionaryAdvanced();
                  break;
              case 2:
                  try {
                      DictionaryManagement.insertAllFile();
                      new MainGraphic();
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  break;
              default:
                  System.out.println("Invalid Selection!");
                  break;
          }
      }while (option != 0);

    }
     // DictionaryManagement.insertAllFile();
    //  DictionaryCommandLine.showMainDictionary();
}