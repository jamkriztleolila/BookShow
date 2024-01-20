package screen;

import java.util.Scanner;

import constants.Constants;
import entity.Show;
import service.InputService;
import service.ShowService;

public class AdminScreen {

  Common c = new Common();
  Scanner sc = new Scanner(System.in);
  ShowService showService = new ShowService();
  MainScreen main = new MainScreen();
  InputService input = new InputService();

  public void showMenu() {
    c.clearScreen();
    c.buildHeader("WELCOME ADMIN!");
    c.buildMenu("Select action from the menu \nand enter the corresponding number:", Constants.ADMIN_SCREEN_MENU);

    c.buildInput();
    int userInput = input.read(sc, 0, Constants.ADMIN_SCREEN_MENU.length);

    c.clearScreen();

    switch (userInput) {
      case 1:
        setupShow();
        break;
      case 2:
        viewShow();
        break;
      case 3:
        main.HomeScreen();
        break;
      default:
        break;
    }
  }

  private void setupShow() {
    c.buildHeader("SETUP A NEW SHOW");

    c.buildInput("Show Number: ");
    int showNumber = input.read(sc, "Show Number: ", 0);

    c.buildInput("No. of rows: ");
    int noOfRows = input.read(sc, "No. of rows: ", 0, Constants.MAX_ROWS, true);

    c.buildInput("No. of seats per rows: ");
    int noOfSeatsPerRow = input.read(sc, "No. of seats per rows: ", 0, Constants.MAX_NUM_SEAT_ROWS, true);

    c.buildInput("Cancellation Period (in minutes): ");
    int cancellationPeriod = input.read(sc, "Cancellation Period (in minutes): ", 0);

    showService.createShow(showNumber, noOfRows, noOfSeatsPerRow, cancellationPeriod);

    c.actionEnter();
    showMenu();
  }

  private void viewShow() {
    c.buildHeader("VIEW A SHOW");
    System.out.println("Enter the SHOW NUMBER from the list below:\n");

    if (!showService.displayShows()) {
      c.actionEnter();
      showMenu();
    }

    System.out.println();
    c.buildInput("Enter show number: ");
    int showNumber = input.read(sc, "Show Number: ", 0);

    try {
      Show show = showService.findShow(showNumber);
      viewShowDetails(show);
    } catch (Exception e) {
      System.out.println("Show number not found.");
      showMenu();
    }
  }

  private void viewShowDetails(Show show) {
    c.clearScreen();
    c.buildHeader("Show #: " + show.getShowNumber());

    showService.displayShowDetails(show);

    c.actionEnter();
    showMenu();
  }

}
