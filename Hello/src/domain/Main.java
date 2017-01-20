package domain;

import java.util.List;

/**
 * @author Antonio Goncalves
 *         APress Book - Beginning Java EE 6 with Glassfish
 *         http://www.apress.com/
 *         http://www.antoniogoncalves.org
 *         --
 */
public class Main {

    public static void main(String[] args) {

        // Creates an instance of book
        Book book = new Book();
        String isbn = "1-84023-742-2";
        book.setTitle("The Hitchhiker's Guide to the Galaxy");
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy series created by Douglas Adams.");
        book.setIsbn(isbn);
        book.setNbOfPage(354);
        book.setIllustrations(false);

        // Persists the book to the database
        BookDAO dao = new BookDAO();
        dao.persist(book);

        // Retrieve all books from the database
        List<Book> books = dao.findAll();
        System.out.println("All books :");
        System.out.println(books);

        // Retrieve one book from the database
        book = dao.findByISBN(isbn);
        System.out.println("Book with isbn = " + isbn);
        System.out.println(book);
    }
}