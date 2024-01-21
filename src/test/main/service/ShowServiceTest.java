package test.main.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import entity.Show;
import service.ShowService;
import service.TicketService;

public class ShowServiceTest {

  ShowService showService = new ShowService();
  TicketService ticketService = new TicketService();

  @Test
  public void testFindShow() {
    showService.createShow(101, 12, 4, 1);
    assertTrue(showService.findShow(101) instanceof Show);
  }

  @Test
  public void testCreateShow() {
    assertTrue(showService.createShow(123, 12, 4, 1));
  }

  @Test
  public void testCreateShow_alreadyExists() {
    showService.createShow(101, 12, 4, 1);
    assertFalse(showService.createShow(101, 12, 4, 1));
  }

  @Test
  public void testDisplayShows() {
    showService.createShow(101, 12, 4, 1);
    showService.createShow(102, 12, 4, 1);

    assertTrue(showService.displayShows());
  }

  @Test
  public void testGenerateSeats() {
    Show testShow = new Show(101, 26, 10, 10);
    assertTrue(showService.generateSeats(testShow).size() == 260);
  }
}
