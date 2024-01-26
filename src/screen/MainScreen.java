package screen;
import java.util.Scanner;

import service.InputService;

public class MainScreen {

  private static final String[] mainScreenMenu = { "Admin", "Buyer", "Exit" };

  public void homeScreen() {

    Common c = new Common();
    AdminScreen admin = new AdminScreen();
    BuyerScreen buyer = new BuyerScreen();
    Scanner sc = new Scanner(System.in);
    InputService input = new InputService();

    c.clearScreen();
    c.buildHeader("Welcome! Book a show");
    c.buildMenu("Select type of user from the options \nand type in the corresponding number:",
    mainScreenMenu);

    c.buildInput();

    int userInput = input.read(sc, 0, mainScreenMenu.length);

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
