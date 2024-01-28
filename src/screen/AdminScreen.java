package screen;

import entity.Show;
import java.util.Scanner;
import service.InputService;
import service.ShowService;

public class AdminScreen implements IScreen {

  Common c = new Common();
  Scanner sc = new Scanner(System.in);
  ShowService showService = new ShowService();
  MainScreen main = new MainScreen();
  InputService input = new InputService();

  private static final String[] adminScreenMenu = { "Setup Show", "View a Show", "Back" };

  public void showMenu() {
    c.clearScreen();
    c.buildHeader("WELCOME ADMIN!");
    c.buildMenu("Select action from the menu \nand enter the corresponding number:", adminScreenMenu);

    c.buildInput();
    int userInput = input.read(sc, 0, adminScreenMenu.length);

    c.clearScreen();

    switch (userInput) {
      case 1:
        setupShow();
        break;
      case 2:
        viewShow();
        break;
      case 3:
        main.showMenu();
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
    int noOfRows = input.read(sc, "No. of rows: ", 0, MAX_ROWS, true);

    c.buildInput("No. of seats per rows: ");
    int noOfSeatsPerRow = input.read(sc, "No. of seats per rows: ", 0, MAX_NUM_SEAT_ROWS, true);

    c.buildInput("Cancellation Period (in minutes): ");
    int cancellationPeriod = input.read(sc, "Cancellation Period (in minutes): ", 0);

    Show show = new Show(showNumber, noOfRows, noOfSeatsPerRow, cancellationPeriod);
    showService.createShow(show);

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
    int showNumber = input.read(sc, "Enter show Number: ", 0);

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
