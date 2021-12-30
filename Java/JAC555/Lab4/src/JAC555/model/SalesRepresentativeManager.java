package JAC555.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

public class SalesRepresentativeManager {
	
	public static SalesRepresentative createSalesRep(EntityManager em, int id, String name, double salary) {
		
		// Create salesRep object
		SalesRepresentative salesRep = new SalesRepresentative(); 
		
		// Set the values specified
		salesRep.setId(id);
		salesRep.setName(name);
		salesRep.setSalary(salary);
		
		// Persist to the DB
		em.getTransaction().begin();
		em.persist(salesRep);
		em.getTransaction().commit();

		return salesRep;
	}
	
	public static void deleteSalesRep(EntityManager em, int id) {
		
		// Find a salesRep object with the specified ID
		SalesRepresentative salesRep = em.find(SalesRepresentative.class, id);
		
		// Delete from DB
		em.getTransaction().begin();
		em.remove(salesRep);
		em.getTransaction().commit();
	}
	
	public static void updateSalesRep(EntityManager em, SalesRepresentative salesRep) {
		
		em.getTransaction().begin();
		
		// Find salesRep object based on id of provided object
		SalesRepresentative sr = em.find(SalesRepresentative.class, salesRep.getId());
		
		// Update all the values
		sr.setName(salesRep.getName());
		sr.setSalary(salesRep.getSalary());
		sr.setCustomerSet(salesRep.getCustomerSet());
		
		// Commit the update
		em.getTransaction().commit();
		
	}
	
	public static SalesRepresentative getSalesRep(EntityManager em, int id) {
		
		// Find object based on specified ID and return it
		return em.find(SalesRepresentative.class, id);
	}
	
	
	// ------ SalesRepresentativeManager Tests ------
	
	// ------ Create Test ------
	@Test
	public void testCreate() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JAC555");
		EntityManager em = emf.createEntityManager();
		
		SalesRepresentative rep = createSalesRep(em, 1, "Test Tester", 20000);
		
		em.close();
		emf.close();
	}
	
	// ------ Update / Get Test ------
	@Test
	public void testUpdateGet() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JAC555");
		EntityManager em = emf.createEntityManager();
		
		SalesRepresentative rep = getSalesRep(em, 1);
		
		System.out.println("Before update: " + rep);
		System.out.println(); // Formatting print
		
		rep.setName("Tested Updated");
		updateSalesRep(em, rep);
		
		rep = getSalesRep(em, 1);
		System.out.println("After update: " + rep);
		System.out.println(); // Formatting print
		
		em.close();
		emf.close();
	}
	
	// ------ Delete Test ------
	@Test
	public void testDelete() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JAC555");
		EntityManager em = emf.createEntityManager();
		
		deleteSalesRep(em, 1);
		
		em.close();
		emf.close();
	}
	// ------ ------ ------ ------
}
