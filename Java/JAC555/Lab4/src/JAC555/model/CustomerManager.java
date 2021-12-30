package JAC555.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

public class CustomerManager {
	
	public static Customer createCustomer(EntityManager em, int id, String email, String name,  int age) {
		
		// Create customer object
	    Customer customer = new Customer(); 
	    
	    // Set the values specified
		customer.setId(id);
		customer.setName(name);
		customer.setEmail(email);
		customer.setAge(age);
		
		// Persist to the DB
		em.getTransaction().begin();
		em.persist(customer);
		em.getTransaction().commit();

		return customer;
	}
	
	public static void deleteCustomer(EntityManager em, int id) {
		
		// Find a customer object with the specified ID
		Customer customer = em.find(Customer.class, id);
		
		// Delete from DB
		em.getTransaction().begin();
		em.remove(customer);
		em.getTransaction().commit();
	}
	
	public static void updateCustomer(EntityManager em, Customer customer) {
		
		em.getTransaction().begin();
		
		// Find customer object based on id of provided object
		Customer c = em.find(Customer.class, customer.getId());
		
		// Update all the values
		c.setName(customer.getName());
		c.setEmail(customer.getEmail());
		c.setAge(customer.getAge());
		c.setSalesRep(customer.getSalesRep());
		
		// Commit the update
		em.getTransaction().commit();
		
	}
	
	public static Customer getCustomer(EntityManager em, int id) {
		
		// Find object based on specified ID and return it
		return em.find(Customer.class, id);
	}
	
	
	// ------ CustomerManager Tests ------
	
	// ------ Create Test ------
	@Test
	public void testCreate() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JAC555");
		EntityManager em = emf.createEntityManager();
		
		Customer cust = createCustomer(em, 1, "test@email.com", "Test Tester", 19);
		
		em.close();
		emf.close();
	}
	
	// ------ Update / Get Test ------
	@Test
	public void testUpdateGet() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JAC555");
		EntityManager em = emf.createEntityManager();
		
		Customer cust = getCustomer(em, 1);
		
		System.out.println("Before update: " + cust);
		System.out.println(); // Formatting print
		
		cust.setName("Tested Updated");
		updateCustomer(em, cust);
		
		cust = getCustomer(em, 1);
		System.out.println("After update: " + cust);
		System.out.println(); // Formatting print
		
		em.close();
		emf.close();
	}
	
	// ------ Delete Test ------
	@Test
	public void testDelete() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JAC555");
		EntityManager em = emf.createEntityManager();
		
		deleteCustomer(em, 1);
		
		em.close();
		emf.close();
	}
	// ------ ------ ------ ------
	
}
