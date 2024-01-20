package screen;

import entity.Seat;
import entity.Show;
import service.SeatService;

public class Common {
  public void buildHeader(String header) {
    System.out.println("------------------------------------------");
    System.out.printf("\t\t%s\n", header);
    System.out.println("------------------------------------------\n");
  }

  public void buildMenu(String message, String[] options) {
    System.out.printf("%s \n\n", message);
    int count = 0;
    for (String option : options) {
      count++;
      System.out.printf("%s. %s\n", count, option);
    }
  }

  public void buildInput() {
    System.out.print("\n> Input: ");
  }

  public void buildInput(String message) {
    System.out.printf("> %s", message);
  }

  public void buildSeats(Show show) {

    System.out.println("\t-------------- SCREEN ---------------\n");
    int row = show.getNumOfRows() + 1;
    int col = show.getNumSeatsPerRow() + 1;
    char alpha = 'A';

    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        if (r > 0 && c > 0) {
          SeatService ss = new SeatService();
          Seat seat = ss.findSeatByRowCol(r, c, show);
          String taken = seat.isAvailable() ? " o " : " x ";
          System.out.print(taken);
        } else if (r == 0 && c > 0) {
          System.out.print(String.format(" %s ", c));
        } else if (r > 0 && c == 0) {
          System.out.printf("\t   %s ", String.valueOf(alpha));
          alpha++;
        } else if (r == 0 && c == 0) {
          System.out.print("\t     ");
        }
      }
      System.out.println();
    }

    System.out.println();
    System.out.println("\t--------------------------------------\n");

  }

  public void clearScreen() {
    System.out.print("\033\143");
  }

  public void actionEnter() {
    try {
      System.out.println("Press Enter key to go back to menu...");
      System.in.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
