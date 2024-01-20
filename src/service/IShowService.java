package service;

import entity.Show;

public interface IShowService {

  /**
   * 
   * @param showNumber
   * @return Show
   */
  Show findShow(int showNumber);

  /**
   * 
   * @param showNumber
   * @param numOfRows
   * @param numOfSeatsPerRow
   * @param cancellationPeriod
   * @return
   */
  boolean createShow(int showNumber, int numOfRows, int numOfSeatsPerRow, int cancellationPeriod);

  /**
   * displays shows
   */
  boolean displayShows();

  void displayShowDetails(Show show);

}
