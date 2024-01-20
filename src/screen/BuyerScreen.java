package screen;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import constants.Constants;
import entity.Show;
import service.InputService;
import service.ShowService;
import service.TicketService;

public class BuyerScreen {

  Common c = new Common();
  Scanner sc = new Scanner(System.in);
  ShowService showService = new ShowService();
  MainScreen main = new MainScreen();
  InputService input = new InputService();
  TicketService ticketService = new TicketService();

  public void showMenu() {
    c.clearScreen();
    c.buildHeader("WELCOME BUYER!");
    c.buildMenu("Select action from the menu \nand enter the corresponding number:", Constants.BUYER_SCREEN_MENU);

    c.buildInput();
    int userInput = input.read(sc, 0, Constants.BUYER_SCREEN_MENU.length);

    c.clearScreen();

    switch (userInput) {
      case 1:
        selectAShow();
        break;
      case 2:
        cancelBooking();
        break;
      case 3:
        main.HomeScreen();
        break;
      default:
        break;
    }
  }

  private void selectAShow() {
    c.clearScreen();
    c.buildHeader("SELECT A SHOW TO BOOK");
    System.out.println("Available shows:\n");

    showService.displayShows();

    System.out.println();
    c.buildInput("Show number: ");
    int showNumber = input.read(sc, "Show number: ", 0);

    try {
      Show show = showService.findShow(showNumber);
      while (show == null) {
        System.out.println("Show number not found.");
        c.buildInput("Show number: ");
        showNumber = input.read(sc, "Show number: ", 0);
        show = showService.findShow(showNumber);
      }
      showSeats(show);
    } catch (Exception e) {
      System.out.println("Show number not found.");
    }
  }

  private void showSeats(Show show) {
    c.clearScreen();
    c.buildHeader("Show #: " + show.getShowNumber());

    System.out.println("\t\t[o] - Seat Available");
    System.out.println("\t\t[x] - Seat Taken\n");

    c.buildSeats(show);

    c.buildInput("Seat # (Separated by comma w/o space): ");

    sc.useDelimiter("\\n");
    String seats = sc.next();

    try {
      while (!ticketService.seatsAvailable(seats, show)) {
        System.err.println("[!] Seat/s not found."
            + "\n\t* Please choose available seats appeared on the screen."
            + "\n\t* Input shall be separated by comma w/o space");
        c.buildInput("Seat # (Separated by comma w/o space): ");
        seats = sc.next();
      }
      reserve(show, ticketService.reserveSeats(seats, show));
    } catch (Exception e) {
      System.out.println("[!] Seat/s not found or already taken. Please choose seats that are available.");
    }
  }

  private void reserve(Show show, Map<String, Integer> reservations) {
    c.clearScreen();
    c.buildHeader("RESERVE TICKET FOR Show #: " + show.getShowNumber());

    System.out.println("Each ticket must have a corresponding phone number. \nPlease provide the details.\n");

    Set<Map.Entry<String, Integer>> reservedTickets = reservations.entrySet();

    for (Map.Entry<String, Integer> seat : reservedTickets) {

      System.out.printf("%-15s %s\n", "Seat #:", seat.getKey());
      System.out.printf("%-15s %s\n", "Ticket #:", seat.getValue());

      c.buildInput("Phone number: ");
      long phoneNumber = input.readPhoneNumber(sc, "Phone number: ", 0);
      ticketService.issueTicket(show, seat.getValue(), seat.getKey(), phoneNumber);
      System.out.println("\n");
    }

    c.actionEnter();
    showMenu();
  }

  private void cancelBooking() {
    c.clearScreen();
    c.buildHeader("CANCEL BOOKING");
    System.out.println("Enter the following details to cancel\nyour booking\n");

    c.buildInput("Ticket number: ");
    int ticket = input.read(sc, "Ticket number: ", 0);

    c.buildInput("Phone number: ");
    long phoneNumber = input.read(sc, 0, "Phone number: ");

    System.out.println();
    ticketService.cancelBooking(ticket, phoneNumber);

    c.actionEnter();
    showMenu();
  }

}
