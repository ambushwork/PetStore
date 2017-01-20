package domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BookDAO {
	private EntityManager em;
	private EntityTransaction tx;
	
	public BookDAO() {
        // Gets an entity manager and a transaction
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("petstorePU");
        em = emf.createEntityManager();
        tx = em.getTransaction();		
	}

	public void persist(Book book) {
        tx.begin();
        em.persist(book);
        tx.commit();
	}
	
	List<Book> findAll() {
		List<Book> books = em.createNamedQuery("findAllBooks").getResultList();
		return books;
	}
	
	Book findByISBN(String isbn) {
		String queryString = "select b from Book b where b.isbn = :isbn";
		Query query = em.createQuery( queryString );
		query.setParameter( "isbn", isbn );
		Book b = null;
		b = (Book)query.getSingleResult();
		return b;
	}

}
