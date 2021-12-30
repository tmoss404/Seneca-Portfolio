package JAC555.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SALES_REPS")
public class SalesRepresentative implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SALES_REP_ID", unique = true, nullable = false, updatable = false)
	private int id;
	
	private String name;
	private double salary;
	
	@OneToMany(mappedBy = "salesRep")
	@JoinColumn(name = "CUSTOMER_ID")
	private List<Customer> customerSet = new ArrayList<Customer>();
	
	// ------ Constructors ------
	public SalesRepresentative() {
		super();
	}
	
	public SalesRepresentative(int id, String name, double salary, List<Customer> customerSet) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.customerSet = customerSet;
	}

	// ------ ------ ------ ------
	
	
	// ------ Getters / Setters ------
	
	// ------ Id ------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// ------ Name ------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// ------ Salary ------
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	// ------ CustomerSet ------
	@OneToMany(mappedBy = "salesRep")
	public List<Customer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(List<Customer> customerSet) {
		this.customerSet = customerSet;
	}
	// ------ ------ ------ ------
	
	
	// ------ toString Method ------
	@Override
	public String toString() {
		return "SalesRepresentative [id=" + id + ", name=" + name + ", salary=" + salary + ", CustomerSet="
				+ customerSet + "]";
	}
	// ------ ------ ------ ------
	
}
