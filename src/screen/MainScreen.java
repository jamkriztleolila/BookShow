package screen;
import java.util.Scanner;

import constants.Constants;
import service.InputService;

public class MainScreen {

  public void HomeScreen() {

    Common c = new Common();
    AdminScreen admin = new AdminScreen();
    BuyerScreen buyer = new BuyerScreen();
    Scanner sc = new Scanner(System.in);
    InputService input = new InputService();

    c.clearScreen();
    c.buildHeader("Welcome! Book a show");
    c.buildMenu("Select type of user from the options \nand type in the corresponding number:",
        Constants.MAIN_SCREEN_MENU);

    c.buildInput();

    int userInput = input.read(sc, 0, Constants.MAIN_SCREEN_MENU.length);

    switch (userInput) {
      case 1:
        admin.showMenu();
        break;
      case 2:
        buyer.showMenu();
        break;
      case 3:
        System.exit(0);
      default:
        break;
    }
    sc.close();
  }
}
