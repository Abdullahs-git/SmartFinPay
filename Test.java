import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    int number = -1;
    while (number < 0 || number > 100) {
      System.out.print("Enter a number between 0 and 100: ");
      number = input.nextInt();
      if (number < 0 || number > 100) {
        System.out.println("Invalid input. Please enter a number between 0 and 100.");
      }
    }
    System.out.println("Valid input. Number entered: " + number);
  }
}
