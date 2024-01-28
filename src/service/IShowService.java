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
   * @param show
   */
  void createShow(Show show);

  /**
   * 
   * @return boolean
   */
  boolean displayShows();

  /**
   * 
   * @param show
   */
  void displayShowDetails(Show show);

}
