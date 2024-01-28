package service;

import java.util.InputMismatchException;
import java.util.Scanner;

import screen.Common;

public class InputService {

  Common c = new Common();

  public int read(Scanner sc, String inputLabel, int userInput) {
    while (userInput <= 0) {
      try {
        userInput = sc.nextInt();
        while (userInput == 0) {
          System.out.println("Invalid input. Please enter a value other than 0");
          c.buildInput(inputLabel);
          userInput = sc.nextInt();
        }
      } catch (InputMismatchException er) {
        invalidInputError();
        c.buildInput(inputLabel);
        sc.next();
      }
    }
    return userInput;
  }

  public long read(Scanner sc, long userInput, String inputLabel) {
    while (userInput <= 0) {
      try {
        userInput = sc.nextLong();
        while (userInput == 0) {
          System.out.println("Invalid input. Please enter a value other than 0");
          c.buildInput(inputLabel);
          userInput = sc.nextLong();
        }
      } catch (InputMismatchException er) {
        invalidInputError();
        c.buildInput(inputLabel);
        sc.next();
      }
    }
    return userInput;
  }

  public int read(Scanner sc, int userInput, int maxAllowedInput) {
    while (userInput <= 0) {
      try {
        userInput = sc.nextInt();
        while (userInput < 1 || userInput > maxAllowedInput) {
          invalidInputError();
          c.buildInput();
          userInput = sc.nextInt();
        }
      } catch (InputMismatchException er) {
        invalidInputError();
        c.buildInput();
        sc.next();
      }
    }
    return userInput;
  }

  public int read(Scanner sc, String inputLabel, int userInput, int maxAllowedInput, boolean maxLimit) {
    while (userInput <= 0) {
      try {
        userInput = sc.nextInt();
        while (userInput < 1 || userInput > maxAllowedInput) {
          maxLimitInputError(maxAllowedInput);
          c.buildInput(inputLabel);
          userInput = sc.nextInt();
        }
      } catch (InputMismatchException er) {
        invalidInputError();
        c.buildInput(inputLabel);
        sc.next();
      }
    }
    return userInput;
  }

  public long readPhoneNumber(Scanner sc, String inputLabel, long userInput) {
    while (userInput <= 0) {
      try {
        userInput = sc.nextLong();
        TicketService ticketService = new TicketService();
        while (ticketService.isPhoneNumberTaken(userInput)) {
          invalidInputError();
          c.buildInput(inputLabel);
          userInput = sc.nextLong();
        }
      } catch (InputMismatchException er) {
        invalidInputError();
        c.buildInput(inputLabel);
        sc.next();
      }
    }
    return userInput;
  }

  private void invalidInputError() {
    System.err.println("Invalid input. Please try again with a valid entry.");
  }

  private void maxLimitInputError(int maxValue) {
    System.err.printf("Invalid input, beyond max limit of %s. Please try again.%n", maxValue);
  }

}
