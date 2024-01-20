package entity;

public class Show {

  private final int showNumber;
  private final int numOfRows;
  private final int numOfSeatsPerRow;
  private final int cancellationPeriod;

  public Show(int showNumber, int numOfRows, int numOfSeatsPerRow, int cancellationPeriod) {
    this.showNumber = showNumber;
    this.numOfRows = numOfRows;
    this.numOfSeatsPerRow = numOfSeatsPerRow;
    this.cancellationPeriod = cancellationPeriod;
  }

  public int getShowNumber() {
    return showNumber;
  }

  public int getNumOfRows() {
    return numOfRows;
  }

  public int getNumSeatsPerRow() {
    return numOfSeatsPerRow;
  }

  public int getCancellationPeriod() {
    return cancellationPeriod;
  }

  public void display() {
    System.out.printf("\t Show no.: %7s \n", showNumber);
  }
}
