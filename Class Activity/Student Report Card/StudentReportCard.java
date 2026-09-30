import java.util.Scanner;

public class StudentReportCard {
          public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);

                    Student s1 = readStudent(sc, 1);
                    Student s2 = readStudent(sc, 2);

                    System.out.println();
                    System.out.println(s1);
                    System.out.println(s2);
                    System.out.println();

                    if (s1.hasHigherAverage(s2)) {
                              System.out.println(s1.getName() + " has a higher average than " + s2.getName() + ".");
                    } else if (s2.hasHigherAverage(s1)) {
                              System.out.println(s2.getName() + " has a higher average than " + s1.getName() + ".");
                    } else {
                              System.out.println(s1.getName() + " and " + s2.getName() + " have the same average.");
                    }

                    System.out.println();
                    s1.addBonus(5);
                    s2.addBonus(3, "Class participation");

                    sc.close();
          }

          private static Student readStudent(Scanner sc, int number) {
                    System.out.print("Enter name for student " + number + ": ");
                    String name = sc.nextLine();
                    System.out.print("Enter mark 1: ");
                    int m1 = sc.nextInt();
                    System.out.print("Enter mark 2: ");
                    int m2 = sc.nextInt();
                    sc.nextLine();
                    return new Student(name, m1, m2);
          }
}