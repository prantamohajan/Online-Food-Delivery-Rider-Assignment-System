import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class LibraryFrontDesk {
          private static ArrayList<String> shelf = new ArrayList<>();
          private static LinkedList<String> reservationQueue = new LinkedList<>();
          private static HashMap<String, String> catalog = new HashMap<>();

          public static void returnBook(String title) {
                    shelf.add(title);
                    System.out.println("Returned: " + title);
          }

          public static void borrowBook(String title) {
                    if (shelf.remove(title)) {
                              System.out.println("Borrowed: " + title);
                    } else {
                              System.out.println("'" + title + "' is not on the shelf.");
                    }
          }

          public static String serveNextPatron() {
                    if (reservationQueue.isEmpty()) {
                              System.out.println("No patrons are waiting.");
                              return null;
                    }
                    String patron = reservationQueue.remove();
                    System.out.println("Next in line: " + patron);
                    return patron;
          }

          public static void lookupBook(String id) {
                    if (catalog.containsKey(id)) {
                              System.out.println("Look up book ID " + id + "... " + catalog.get(id));
                    } else {
                              System.out.println("Look up book ID " + id + "... No book found with that ID");
                    }
          }

          private static void printShelf() {
                    System.out.print("Books on shelf: [");
                    for (int i = 0; i < shelf.size(); i++) {
                              System.out.print(shelf.get(i));
                              if (i < shelf.size() - 1) {
                                        System.out.print(", ");
                              }
                    }
                    System.out.println("]");
          }

          public static void main(String[] args) {
                    shelf.add("Dune");
                    shelf.add("1984");
                    shelf.add("The Hobbit");

                    catalog.put("B10", "Dune");
                    catalog.put("B11", "1984");
                    catalog.put("B12", "The Hobbit");
                    catalog.put("B13", "Brave New World");

                    printShelf();
                    System.out.println();

                    borrowBook("Dune");
                    printShelf();
                    System.out.println();

                    reservationQueue.add("Alice");
                    System.out.println("Reserve 'Dune' for: Alice");
                    reservationQueue.add("Ben");
                    System.out.println("Reserve 'Dune' for: Ben");
                    serveNextPatron();
                    serveNextPatron();
                    serveNextPatron();
                    System.out.println();

                    returnBook("Dune");
                    printShelf();
                    System.out.println();

                    lookupBook("B12");
                    lookupBook("Z99");
          }
}