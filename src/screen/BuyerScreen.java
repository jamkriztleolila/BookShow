package screen;

import entity.Show;
import entity.Ticket;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import service.InputService;
import service.ShowService;
import service.TicketService;

public class BuyerScreen implements IScreen {

  Common c = new Common();
  Scanner sc = new Scanner(System.in);
  ShowService showService = new ShowService();
  MainScreen main = new MainScreen();
  InputService input = new InputService();
  TicketService ticketService = new TicketService();

  private static final String[] buyerScreenMenu = {
    "Book a show",
    "Cancel a booking",
    "Back",
  };

  public void showMenu() {
    c.clearScreen();
    c.buildHeader("WELCOME BUYER!");
    c.buildMenu(
      "Select action from the menu \nand enter the corresponding number:",
      buyerScreenMenu
    );

    c.buildInput();
    int userInput = input.read(sc, 0, buyerScreenMenu.length);

    c.clearScreen();

    switch (userInput) {
      case 1:
        selectAShow();
        break;
      case 2:
        cancelBooking();
        break;
      case 3:
        main.showMenu();
        break;
      default:
        break;
    }
  }

  private void selectAShow() {
    c.clearScreen();
    c.buildHeader("SELECT A SHOW TO BOOK");
    System.out.println("Available shows:\n");

    if (!showService.displayShows()) {
      c.actionEnter();
      showMenu();
    }

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

    System.out.println("\t[o] - Seat Available");
    System.out.println("\t[x] - Seat Taken\n");

    c.buildSeats(show);

    System.out.println("  Or press 0 to go back to menu...\n");

    c.buildInput("Seat # (Separated by comma w/o space): ");

    sc.useDelimiter("\\n");
    String seats = sc.next();

    if (seats.equals("0")) {
      showMenu();
      return;
    }

    try {
      while (!ticketService.seatsAvailable(seats, show)) {
        System.err.println(
          "[!] Seat/s not found." +
          "\n\t* Please choose available seats appeared on the screen." +
          "\n\t* Input shall be separated by comma w/o space"
        );
        c.buildInput("Seat # (Separated by comma w/o space): ");
        seats = sc.next();
        if (seats.equals("0")) {
          showMenu();
          return;
        }
      }

      Map<String, Integer> ticketReservations = ticketService.reserveSeats(seats, show);
      reserve(show, ticketReservations);

    } catch (Exception e) {
      System.out.println(
        "[!] Seat/s not found or already taken. Please choose seats that are available."
      );
    }
  }

  private void reserve(Show show, Map<String, Integer> reservations) {
    c.clearScreen();
    c.buildHeader("RESERVE TICKET FOR Show #: " + show.getShowNumber());

    System.out.println(
      "Each ticket must have a corresponding phone number. \nPlease provide the details.\n"
    );

    Set<Map.Entry<String, Integer>> reservedSeats = reservations.entrySet();

    for (Map.Entry<String, Integer> seats : reservedSeats) {
      System.out.printf("%-15s %s%n", "Seat #:", seats.getKey());
      System.out.printf("%-15s %s%n", "Ticket #:", seats.getValue());

      c.buildInput("Phone number: ");
      long phoneNumber = input.readPhoneNumber(sc, "Phone number: ", 0);


      Ticket newTicket = ticketService.issueTicket(
        new Ticket(seats.getValue(), show.getShowNumber(), seats.getKey()),
        show,
        phoneNumber
      );

      if (newTicket == null) {
        System.out.println("Error occurred while issuing Ticket.");
      }
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
