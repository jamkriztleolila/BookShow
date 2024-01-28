package test.main.service;

import static org.junit.Assert.assertTrue;

import java.util.Scanner;
import org.junit.Test;
import service.InputService;

public class InputServiceTest {

  Scanner sc = new Scanner(System.in);
  InputService inputService = new InputService();

  String testLabel = "Test Label";
  int testInputInt = 1;
  long testInputLong = 1234566790;

  @Test
  public void testRead_labelInput() {
    assertTrue(inputService.read(sc, testLabel, testInputInt) == 1);
  }

  @Test
  public void testRead_labelInput_Long() {
    assertTrue(inputService.read(sc, testInputLong, testLabel) == 1234566790);
  }

  @Test
  public void testRead_maxAllowed() {
    assertTrue(inputService.read(sc, testInputInt, 3) == 1);
  }

  @Test
  public void testRead_maxAllowed_maxLimit() {
    assertTrue(inputService.read(sc, testLabel, testInputInt, 3, true) == 1);
  }

  @Test
  public void testReadPhoneNumber() {
    assertTrue(
      inputService.readPhoneNumber(sc, testLabel, testInputLong) == 1234566790
    );
  }
}
