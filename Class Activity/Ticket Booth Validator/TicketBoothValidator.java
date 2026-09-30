import java.util.Scanner;

public class TicketBoothValidator {
          private static final String[] seats = { "A1", "A2", "A3", "A4", "A5" };

          public static int checkAge(String input) throws InvalidAgeException {
                    int age = Integer.parseInt(input.trim());
                    if (age < 0 || age > 120) {
                              throw new InvalidAgeException("age must be between 0 and 120.");
                    }
                    System.out.println("Age " + age + " is valid.");
                    return age;
          }

          public static String bookSeat(int seatNumber) {
                    String label = seats[seatNumber];
                    System.out.println("Seat label: " + label);
                    return label;
          }

          public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    String again;

                    do {
                              try {
                                        System.out.print("Enter customer age: ");
                                        int age = checkAge(sc.nextLine());
                                        System.out.print("Enter seat number (0-4): ");
                                        int seatNumber = Integer.parseInt(sc.nextLine().trim());
                                        String seat = bookSeat(seatNumber);
                                        System.out.println("Ticket approved for age " + age + ", seat " + seat + ".");
                              } catch (NumberFormatException e) {
                                        System.out.println("Error: that is not a valid number.");
                              } catch (InvalidAgeException e) {
                                        System.out.println("Error: " + e.getMessage());
                              } catch (ArrayIndexOutOfBoundsException e) {
                                        System.out.println("Error: that seat number does not exist.");
                              } finally {
                                        System.out.println("Finished checking this customer.");
                              }

                              System.out.print("Check another customer? (y/n): ");
                              again = sc.nextLine().trim();
                              System.out.println();
                    } while (again.equalsIgnoreCase("y"));

                    sc.close();
          }
}